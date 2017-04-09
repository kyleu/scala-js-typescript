package models.parse

import models.parse.sc.tree._
import models.parse.parser.tree._
import models.parse.sc.transform.{ExcludedMembers, Transformations}

object Importer {
  private val AnyType = TypeRefTree(CoreType("any"))
  private val DynamicType = TypeRefTree(CoreType("dynamic"))

  private implicit class OptType(val optType: Option[TypeTree]) extends AnyVal {
    @inline def orAny: TypeTree = optType.getOrElse(AnyType)
    @inline def orDynamic: TypeTree = optType.getOrElse(DynamicType)
  }

  private object TypeOrAny {
    @inline def unapply(optType: Option[TypeTree]) = Some(optType.orAny)
  }

  private object IdentName {
    @inline def unapply(ident: Ident) = Some(Name(ident.name))
  }

  private object TypeNameName {
    @inline def apply(typeName: Name) = TypeName(typeName.name)
    @inline def unapply(typeName: TypeName) = Some(Name(typeName.name))
  }

  private object PropertyNameName {
    @inline def unapply(propName: PropertyName) = Some(Name(propName.name))
  }
}

/**
 * The meat and potatoes: the importer
 *  It reads the TypeScript AST and produces (hopefully) equivalent Scala
 *  code.
 */
case class Importer(key: String) {
  import Importer._

  /** Entry point */
  def apply(declarations: List[DeclTree]) = {
    val rootPackage = new PackageSymbol(Name.EMPTY)
    for (declaration <- declarations) {
      processDecl(rootPackage, declaration)
    }
    rootPackage
  }

  private def processDecl(owner: ContainerSymbol, declaration: DeclTree) {
    declaration match {
      case ModuleDecl(PropertyNameName(name), innerDecls) =>
        assert(owner.isInstanceOf[PackageSymbol], s"Found package $name in non-package $owner")
        val sym = owner.asInstanceOf[PackageSymbol].getPackageOrCreate(name)

        for (innerDecl <- innerDecls) {
          processDecl(sym, innerDecl)
        }

      case VarDecl(IdentName(name), Some(tpe @ ObjectType(members))) =>
        val sym = owner.getModuleOrCreate(name)
        processMembersDecls(name.name, owner, sym, members)

      case ValDecl(IdentName(name), Some(tpe @ ObjectType(members))) =>
        val sym = owner.getModuleOrCreate(name)
        processMembersDecls(name.name, owner, sym, members)

      case TypeDecl(TypeNameName(name), tpe @ ObjectType(members)) =>
        val sym = owner.getClassOrCreate(name)
        processMembersDecls(name.name, owner, sym, members)

      case EnumDecl(TypeNameName(name), members) =>
        // Type
        val tsym = owner.getClassOrCreate(name)
        tsym.isSealed = true

        // Module
        val sym = owner.getModuleOrCreate(name)
        for (IdentName(name) <- members) {
          val m = sym.newField(prot = false, name)
          m.protectName()
          m.tpe = TypeRef(tsym.name)
        }
        val applySym = sym.newMethod(prot = false, Name("apply"))
        applySym.params += new ParamSymbol(Name("value"), TypeRef(tsym.name))
        applySym.resultType = TypeRef.String
        applySym.isBracketAccess = true

      case ClassDecl(abst, TypeNameName(name), tparams, parent, implements, members) =>
        val sym = owner.getClassOrCreate(name)
        sym.isTrait = false
        sym.isAbstract = abst
        parent.foreach(sym.parents += typeToScala(_))
        for { parent <- implements.map(typeToScala) if !sym.parents.contains(parent) } {
          sym.parents += parent
        }
        sym.tparams ++= typeParamsToScala(tparams)
        processMembersDecls(name.name, owner, sym, members)
        if (!sym.members.exists(_.name == Name.CONSTRUCTOR)) {
          processDefDecl(sym, Name.CONSTRUCTOR, FunSignature(Nil, Nil, Some(TypeRefTree(CoreType("void")))), protectName = true, prot = false, allowDefaults = true, readonly = false)
        }

      case InterfaceDecl(TypeNameName(name), tparams, inheritance, members) =>
        val sym = owner.getClassOrCreate(name)
        for { parent <- inheritance.map(typeToScala) if !sym.parents.contains(parent) } {
          sym.parents += parent
        }
        sym.tparams ++= typeParamsToScala(tparams)
        processMembersDecls(name.name, owner, sym, members)

      case TypeAliasDecl(TypeNameName(name), tparams, alias) =>
        val sym = owner.newTypeAlias(name)
        sym.tparams ++= typeParamsToScala(tparams)
        sym.alias = typeToScala(alias)

      case VarDecl(IdentName(name), TypeOrAny(tpe)) =>
        val sym = owner.newField(prot = false, name)
        sym.tpe = typeToScala(tpe)

      case ValDecl(IdentName(name), TypeOrAny(tpe)) =>
        val sym = owner.newField(prot = false, name, readonly = true)
        sym.tpe = typeToScala(tpe)

      case FunctionDecl(prot, IdentName(name), signature) => processDefDecl(owner, name, signature, protectName = true, prot = prot, allowDefaults = true, readonly = false)

      case ExportDecl(v) => owner.members += new CommentSymbol(v, false)

      case ImportCommentDecl(text) => owner.members += new CommentSymbol(text, multiline = false)
      case LineCommentDecl(text) => owner.members += new CommentSymbol(text, multiline = false)
      case MultilineCommentDecl(text) => owner.members += new CommentSymbol(text, multiline = true)

      case _ => //owner.members += new CommentSymbol("??? " + declaration, false)
    }
  }

  private def processMembersDecls(nm: String, enclosing: ContainerSymbol, owner: ContainerSymbol, members: List[MemberTree]) {
    val OwnerName = owner.name

    lazy val companionClassRef = {
      val tparams = enclosing.findClass(OwnerName) match {
        case Some(clazz) => clazz.tparams.toList.map(tp => TypeRefTree(TypeNameName(tp.name), Nil))
        case _ => Nil
      }
      TypeRefTree(TypeNameName(OwnerName), tparams)
    }

    val nameCounts = members.flatMap {
      case x: FunctionMember => Some(x.name.name)
      case x: PropertyMember => Some(x.name.name)
      case _ => None
    }.groupBy(x => x).mapValues(_.size)

    val filteredMembers = members.filter {
      case f: FunctionMember => ExcludedMembers.check(key, nm, f.name.name)
      case p: PropertyMember => ExcludedMembers.check(key, nm, p.name.name)
      case _ => true
    }

    for (member <- filteredMembers) member match {
      case CallMember(signature) => processDefDecl(owner, Name("apply"), signature, protectName = false, prot = false, allowDefaults = false, readonly = false)

      case ConstructorMember(sig @ FunSignature(tparamsIgnored, params, Some(resultType))) if owner.isInstanceOf[ModuleSymbol] && resultType == companionClassRef =>
        val classSym = enclosing.getClassOrCreate(owner.name)
        classSym.isTrait = false
        processDefDecl(classSym, Name.CONSTRUCTOR, FunSignature(Nil, params, Some(TypeRefTree(CoreType("void")))), protectName = true, prot = false, allowDefaults = true, readonly = false)

      case PropertyMember(prot, PropertyNameName(name), opt, tpe, true, readonly) =>
        assert(owner.isInstanceOf[ClassSymbol], s"Cannot process static member $name in module definition")
        val module = enclosing.getModuleOrCreate(owner.name)
        processPropertyDecl(prot, module, name, tpe, readonly = readonly)

      case PropertyMember(prot, PropertyNameName(name), opt, tpe, _, readonly) => processPropertyDecl(prot, owner, name, tpe, readonly = readonly)

      case FunctionMember(prot, PropertyName("constructor"), _, signature, false, readonly) if owner.isInstanceOf[ClassSymbol] =>
        owner.asInstanceOf[ClassSymbol].isTrait = false
        processDefDecl(owner, Name.CONSTRUCTOR, FunSignature(Nil, signature.params, Some(TypeRefTree(CoreType("void")))), protectName = true, prot = prot, allowDefaults = true, readonly = readonly)

      case FunctionMember(prot, PropertyNameName(name), opt, signature, true, readonly) =>
        assert(owner.isInstanceOf[ClassSymbol], s"Cannot process static member $name in module definition")
        val count = nameCounts.getOrElse(name.name, 0)
        val module = enclosing.getModuleOrCreate(owner.name)
        processDefDecl(module, name, signature, protectName = true, prot = prot, allowDefaults = count <= 1, readonly = readonly)

      case FunctionMember(prot, PropertyNameName(name), opt, signature, _, readonly) =>
        val count = nameCounts.getOrElse(name.name, 0)
        processDefDecl(owner, name, signature, protectName = true, prot = prot, allowDefaults = count <= 1, readonly = readonly)

      case IndexMember(IdentName(indexName), indexType, valueType) =>
        val indexTpe = typeToScala(indexType)
        val valueTpe = typeToScala(valueType)

        val getterSym = owner.newMethod(prot = false, Name("apply"))
        getterSym.params += new ParamSymbol(indexName, indexTpe)
        getterSym.resultType = valueTpe
        getterSym.isBracketAccess = true

        val setterSym = owner.newMethod(prot = false, Name("update"))
        setterSym.params += new ParamSymbol(indexName, indexTpe)
        setterSym.params += new ParamSymbol(Name("v"), valueTpe)
        setterSym.resultType = TypeRef.Unit
        setterSym.isBracketAccess = true

      case ImportCommentMember(text) => owner.members += new CommentSymbol(text, multiline = false)
      case LineCommentMember(text) => owner.members += new CommentSymbol(text, multiline = false)
      case MultilineCommentMember(text) => owner.members += new CommentSymbol(text, multiline = true)

      case _ => //owner.members += new CommentSymbol("??? " + member, multiline = false)
    }
  }

  private def processPropertyDecl(prot: Boolean, owner: ContainerSymbol, name: Name, tpe: TypeTree, protectName: Boolean = true, readonly: Boolean) = if (name.name != "prototype") {
    tpe match {
      case ObjectType(members) if members.forall(_.isInstanceOf[CallMember]) =>
        for (CallMember(signature) <- members) {
          processDefDecl(owner, name, signature, protectName, prot = prot, allowDefaults = true, readonly = readonly)
        }
      case _ =>
        val sym = owner.newField(prot, name)
        if (protectName) {
          sym.protectName()
        }
        sym.tpe = typeToScala(tpe)
    }
  }

  private def processDefDecl(owner: ContainerSymbol, name: Name, signature: FunSignature, protectName: Boolean, prot: Boolean, allowDefaults: Boolean, readonly: Boolean) {
    // Discard specialized signatures
    if (signature.params.exists(_.tpe.exists(_.isInstanceOf[ConstantType]))) {
      return
    }

    val sym = owner.newMethod(prot, name)
    if (protectName) {
      sym.protectName()
    }

    sym.tparams ++= typeParamsToScala(signature.tparams)

    val allowDef = allowDefaults && !signature.params.exists(x => x.tpe.exists(_.isInstanceOf[RepeatedType]))

    for (FunParam(IdentName(paramName), opt, TypeOrAny(tpe)) <- signature.params) {
      val paramSym = new ParamSymbol(paramName)
      paramSym.allowDefaults = allowDef
      paramSym.optional = opt
      paramSym.readonly = readonly
      tpe match {
        case RepeatedType(tpe0) => paramSym.tpe = TypeRef.Repeated(typeToScala(tpe0))
        case _ => paramSym.tpe = typeToScala(tpe)
      }
      sym.params += paramSym
    }

    sym.resultType = typeToScala(signature.resultType.orDynamic, anyAsDynamic = true, prot = prot)

    owner.removeIfDuplicate(sym)
  }

  private def typeParamsToScala(tparams: List[TypeParam]): List[TypeParamSymbol] = for (TypeParam(TypeNameName(tparam), upperBound) <- tparams) yield {
    new TypeParamSymbol(tparam, upperBound.map(x => typeToScala(x)))
  }

  private def typeToScala(tpe: TypeTree): TypeRef = typeToScala(tpe, anyAsDynamic = false, prot = false)

  private def typeToScala(tpe: TypeTree, anyAsDynamic: Boolean, prot: Boolean): TypeRef = tpe match {
    case TypeRefTree(tpe: CoreType, Nil) => coreTypeToScala(tpe, anyAsDynamic)

    case TypeRefTree(base, targs) =>
      val baseTypeRef = base match {
        case TypeName("Array") => QualifiedName.Array
        case TypeName("Function") => QualifiedName.FunctionBase
        case TypeNameName(name) => Transformations.forName(QualifiedName(name))
        case QualifiedTypeName(qualifier, TypeNameName(name)) => Transformations.forName(QualifiedName(qualifier.map(x => Name(x.name)) :+ name: _*))
        case _: CoreType => throw new MatchError(base)
      }
      TypeRef(baseTypeRef, targs map typeToScala)

    case ObjectType(List(IndexMember(_, TypeRefTree(CoreType("string"), _), valueType))) =>
      val valueTpe = typeToScala(valueType)
      TypeRef(QualifiedName.Dictionary, List(valueTpe))

    case ObjectType(members) => TypeRef.Any

    case FunctionType(FunSignature(tparams, params, Some(resultType))) => if (tparams.nonEmpty) {
      // Type parameters in function types are not supported
      TypeRef.Function
    } else if (params.exists(_.tpe.exists(_.isInstanceOf[RepeatedType]))) {
      // Repeated params in function types are not supported
      TypeRef.Function
    } else {
      val paramTypes = for (FunParam(_, _, TypeOrAny(tpe)) <- params) yield typeToScala(tpe)
      val resType = resultType match {
        case TypeRefTree(CoreType("any"), Nil) => TypeRef.ScalaAny
        case _ => typeToScala(resultType)
      }
      val targs = paramTypes :+ resType

      TypeRef(QualifiedName.Function(params.size), targs)
    }
    case UnionType(left, right) => TypeRef.Union(typeToScala(left), typeToScala(right))
    case TypeQuery(expr) => TypeRef.Singleton(QualifiedName((expr.qualifier :+ expr.name).map(ident => Name(ident.name)): _*))
    case TupleType(targs) => TypeRef(QualifiedName.Tuple(targs.length), targs map typeToScala)
    case RepeatedType(underlying) => TypeRef(Name.REPEATED, List(typeToScala(underlying)))

    //case LineCommentType(text) => owner.members += new CommentSymbol(text, multiline = false)
    //case MultilineCommentType(text) => owner.members += new CommentSymbol(text, multiline = true)

    case _ => TypeRef.Any
  }

  private def coreTypeToScala(tpe: CoreType, anyAsDynamic: Boolean = false): TypeRef = tpe.name match {
    case "any" => if (anyAsDynamic) TypeRef.Dynamic else TypeRef.Any
    case "dynamic" => TypeRef.Dynamic
    case "void" => TypeRef.Unit
    case "number" => TypeRef.Double
    case "bool" => TypeRef.Boolean
    case "boolean" => TypeRef.Boolean
    case "string" => TypeRef.String
    case "null" => TypeRef.Null
    case "undefined" => TypeRef.Unit
  }
}
