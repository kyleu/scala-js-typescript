package models.parse.sc

import models.parse.sc.tree._

class Printer(val files: PrinterFiles, outputPackage: String) {
  import PrinterHelper._

  private implicit val self = this

  protected var currentJSNamespace = ""

  private[this] def printComment(text: String) = pln"/* $text */"

  private[this] def canBeTopLevel(sym: tree.Symbol): Boolean = sym.isInstanceOf[ContainerSymbol]

  private[this] def printPackage(sym: PackageSymbol, outputPackage: String) = {
    val isRootPackage = sym.name == Name.EMPTY

    val parentPackage :+ thisPackage =
      if (isRootPackage) outputPackage.split("\\.").toList.map(Name(_))
      else List(sym.name)

    if (parentPackage.nonEmpty) {
      pln"package ${parentPackage.mkString(".")}"
    }

    if (isRootPackage) {
      pln""
      pln"import scala.scalajs.js"
      pln"import js.annotation._"
      pln"import js.|"
    }

    val oldJSNamespace = currentJSNamespace
    if (!isRootPackage) {
      currentJSNamespace += sym.name.name + "."
    }

    if (sym.members.nonEmpty) {
      val (topLevels, packageObjectMembers) = sym.members.partition(canBeTopLevel)

      pln""
      pln"package $thisPackage {"

      for (sym <- topLevels) {
        printSymbol(sym)
      }

      if (packageObjectMembers.nonEmpty) {
        val packageObjectName =
          Name(thisPackage.name.head.toUpper + thisPackage.name.tail)

        pln""
        if (currentJSNamespace == "") {
          pln"@js.native"
          pln"object $packageObjectName extends js.GlobalScope {"
        } else {
          val jsName = currentJSNamespace.init
          pln"@js.native"
          pln"""@js.annotation.JSName("$jsName")"""
          pln"object $packageObjectName extends js.Object {"
        }
        for (sym <- packageObjectMembers) {
          printSymbol(sym)
        }
        pln"}"
      }

      pln""
      pln"}"
    }

    currentJSNamespace = oldJSNamespace
  }

  private[this] def printClass(sym: ClassSymbol) = {
    val sealedKw = if (sym.isSealed) "sealed " else ""
    val abstractKw = if (sym.isAbstract) "abstract " else ""
    val kw = if (sym.isTrait) "trait" else "class"
    val constructorStr =
      if (sym.isTrait) ""
      else if (sym.members.exists(isParameterlessConstructor)) ""
      else " protected ()"
    val parents =
      if (sym.parents.isEmpty) List(TypeRef.Object)
      else sym.parents.toList

    pln""
    pln"@js.native"
    if (currentJSNamespace != "" && !sym.isTrait) {
      pln"""@js.annotation.JSName("$currentJSNamespace${sym.name}")"""
    }
    p"$abstractKw$sealedKw$kw ${sym.name}"
    if (sym.tparams.nonEmpty) {
      p"[${sym.tparams}]"
    }

    {
      implicit val withSep = ListElemSeparator.WithKeyword
      pln"$constructorStr extends $parents {"
    }

    printMemberDecls(sym)
    pln"}"
  }

  private[this] def printModule(sym: ModuleSymbol) = {
    pln""
    pln"@js.native"
    if (currentJSNamespace != "")
      pln"""@js.annotation.JSName("$currentJSNamespace${sym.name}")"""
    pln"object ${sym.name} extends js.Object {"
    printMemberDecls(sym)
    pln"}"
  }

  private[this] def printTypeAlias(sym: TypeAliasSymbol) = {
    p"  type ${sym.name}"
    if (sym.tparams.nonEmpty) {
      p"[${sym.tparams}]"
    }
    pln" = ${sym.alias}"
  }

  private[this] def printField(sym: FieldSymbol) = {
    sym.jsName foreach { jsName =>
      pln"""  @js.annotation.JSName("$jsName")"""
    }
    pln"  ${sym.decl} ${sym.name}: ${sym.tpe} = js.native"
  }

  private[this] def printMethod(sym: MethodSymbol) = {
    val params = sym.params

    if (sym.name == Name.CONSTRUCTOR) {
      if (params.nonEmpty) {
        pln"  def this($params) = this()"
      }
    } else {
      sym.jsName foreach { jsName =>
        pln"""  @js.annotation.JSName("$jsName")"""
      }
      if (sym.isBracketAccess) {
        pln"""  @JSBracketAccess"""
      }
      p"  def ${sym.name}"
      if (sym.tparams.nonEmpty) {
        p"[${sym.tparams}]"
      }
      pln"($params): ${sym.resultType} = js.native"
    }
  }

  private[this] def printParam(sym: ParamSymbol) = p"${sym.name}: ${sym.tpe}${if (sym.optional) " = js.native" else ""}"

  private[this] def printTypeParam(sym: TypeParamSymbol) = {
    p"${sym.name}"
    sym.upperBound.foreach(bound => p" <: $bound")
  }

  def printSymbol(sym: tree.Symbol) {
    sym match {
      case s: CommentSymbol => printComment(s.text)
      case s: PackageSymbol => printPackage(s, outputPackage)
      case s: ClassSymbol => printClass(s)
      case s: ModuleSymbol => printModule(s)
      case s: TypeAliasSymbol => printTypeAlias(s)
      case s: FieldSymbol => printField(s)
      case s: MethodSymbol => printMethod(s)
      case s: ParamSymbol => printParam(s)
      case s: TypeParamSymbol => printTypeParam(s)
    }
  }

  private def printMemberDecls(owner: ContainerSymbol) {
    val (constructors, others) = owner.members.toList.partition(_.name == Name.CONSTRUCTOR)
    for (sym <- constructors ++ others) {
      printSymbol(sym)
    }
  }

  private def isParameterlessConstructor(sym: tree.Symbol): Boolean = sym match {
    case sym: MethodSymbol => sym.name == Name.CONSTRUCTOR && sym.params.isEmpty
    case _ => false
  }

  def printTypeRef(tpe: TypeRef) = tpe match {
    case TypeRef(typeName, Nil) => p"$typeName"
    case TypeRef.Union(left, right) => p"$left | $right"
    case TypeRef.Singleton(termRef) => p"$termRef.type"
    case TypeRef.Repeated(underlying) => p"$underlying*"
    case TypeRef(typeName, targs) => p"$typeName[$targs]"
  }

  def print(x: Any) = x match {
    case x: tree.Symbol => printSymbol(x)
    case x: TypeRef => printTypeRef(x)
    case QualifiedName(Name.scala, Name.scalajs, Name.js, name) =>
      files.output.print("js.")
      files.output.print(name)
    case QualifiedName(Name.scala, name) => files.output.print(name)
    case QualifiedName(Name.java, Name.lang, name) => files.output.print(name)
    case _ => files.output.print(x)
  }
}
