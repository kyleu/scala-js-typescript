package models.parse.sc.printer

import models.parse.sc.tree
import models.parse.sc.tree._

class Printer(val files: PrinterFiles, outputPackage: String, ignoredPackages: Set[String]) {
  import PrinterHelper._

  private implicit val self = this

  private[this] val pendingComments = collection.mutable.ArrayBuffer.empty[CommentSymbol]
  private[this] def printPending(id: Int) = {
    //printComment(s" Print Pending [$id]", multiline = false)
    pendingComments.foreach(s => printComment(s.cleanedText, s.multiline))
    pendingComments.clear()
  }

  protected var currentJSNamespace = ""

  private[this] def printComment(text: String, multiline: Boolean) = if (multiline) {
    pln"  /*$text*/"
  } else {
    pln"  //$text"
  }

  private[this] def canBeTopLevel(sym: tree.Symbol): Boolean = sym.isInstanceOf[ContainerSymbol]

  private[this] def printPackage(sym: PackageSymbol, outputPackage: String) = {
    val isRootPackage = sym.name == Name.EMPTY

    val parentPackage :+ thisPackage = if (isRootPackage) {
      outputPackage.split("\\.").toList.map(Name(_))
    } else {
      List(sym.name)
    }

    if (parentPackage.nonEmpty) {
      pln"package ${parentPackage.mkString(".")}"
    }

    val oldJSNamespace = currentJSNamespace
    if (!isRootPackage) {
      currentJSNamespace += sym.name.name + "."
    }

    if (sym.members.nonEmpty) {
      val (topLevels, packageObjectMembers) = sym.members.partition(canBeTopLevel)

      if (!isRootPackage && !ignoredPackages.contains(thisPackage.name)) {
        files.pushPackage(thisPackage)
      }

      printPending(1)

      for (sym <- topLevels) {
        printSymbol(sym)
      }

      if (!packageObjectMembers.forall(_.isInstanceOf[CommentSymbol])) {
        val tempName = thisPackage.name.replaceAllLiterally("-", "")
        val packageObjectName = Name(tempName.head.toUpper + tempName.tail)

        pln""
        files.setActiveObject(packageObjectName)
        if (currentJSNamespace == "") {
          pln"@js.native"
          pln"@js.annotation.JSGlobalScope"
          pln"object $packageObjectName extends js.Object {"
        } else {
          pln"@js.native"
          pln"""@js.annotation.JSGlobal("${currentJSNamespace.init}")"""
          pln"object $packageObjectName extends js.Object {"
        }
        for (sym <- packageObjectMembers) {
          printSymbol(sym)
        }
        pln"}"
        files.clearActiveObject(packageObjectName)
      }

      if (!isRootPackage && !ignoredPackages.contains(thisPackage.name)) {
        files.popPackage(thisPackage)
      }
    }

    currentJSNamespace = oldJSNamespace
  }

  private[this] def printClass(sym: ClassSymbol) = {
    val sealedKw = if (sym.isSealed) { "sealed " } else { "" }
    val abstractKw = if (sym.isAbstract) { "abstract " } else { "" }
    val kw = if (sym.isTrait) { "trait" } else { "class" }
    val constructorStr = if (sym.isTrait) {
      ""
    } else if (sym.members.exists(isParameterlessConstructor)) {
      ""
    } else {
      " protected ()"
    }
    val parents = if (sym.parents.isEmpty) {
      List(TypeRef.Object)
    } else {
      sym.parents.toList
    }

    pln""
    printPending(2)
    pln"@js.native"
    if (!sym.isTrait) {
      pln"""@js.annotation.JSGlobal("$currentJSNamespace${sym.name}")"""
    }
    p"$abstractKw$sealedKw$kw ${sym.name}"
    if (sym.tparams.nonEmpty) {
      p"[${sym.tparams}]"
    }

    plnw"$constructorStr extends $parents {"

    printMemberDecls(sym)
    pln"}"
  }

  private[this] def printModule(sym: ModuleSymbol) = if (sym.members.nonEmpty) {
    files.setActiveObject(sym.name)
    printPending(3)
    pln""
    pln"@js.native"
    pln"""@js.annotation.JSGlobal("$currentJSNamespace${sym.name}")"""
    pln"object ${sym.name} extends js.Object {"
    printMemberDecls(sym)
    pln"}"
    files.clearActiveObject(sym.name)
  }

  private[this] def printTypeAlias(sym: TypeAliasSymbol) = {
    printPending(4)
    p"  type ${sym.name}"
    if (sym.tparams.nonEmpty) {
      p"[${sym.tparams}]"
    }
    pln" = ${sym.alias}"
  }

  private[this] def printField(sym: FieldSymbol) = {
    printPending(5)
    sym.jsName.foreach { jsName =>
      pln"""  @js.annotation.JSName("$jsName")"""
    }
    pln"  ${sym.p}${sym.decl} ${sym.name}: ${sym.tpeTranslated} = js.native"
  }

  private[this] def printMethod(sym: MethodSymbol) = {
    printPending(6)
    val params = sym.params

    if (sym.name == Name.CONSTRUCTOR) {
      if (params.nonEmpty) {
        pln"  ${sym.p}def this($params) = this()"
      }
    } else {
      sym.jsName.foreach { jsName =>
        pln"""  @js.annotation.JSName("$jsName")"""
      }
      if (sym.isBracketAccess) {
        pln"""  @js.annotation.JSBracketAccess"""
      }
      p"  ${sym.p}def ${sym.name}"
      if (sym.tparams.nonEmpty) {
        p"[${sym.tparams}]"
      }
      pln"($params): ${sym.resultType} = js.native"
    }
  }

  private[this] def printParam(sym: ParamSymbol) = p"${sym.name}: ${sym.tpe}${if (sym.optional && sym.allowDefaults) " = js.native" else ""}"

  private[this] def printTypeParam(sym: TypeParamSymbol) = {
    p"${sym.name}"
    sym.upperBound.foreach(bound => p" <: $bound")
  }

  def printSymbol(sym: tree.Symbol) {
    sym match {
      case s: CommentSymbol => pendingComments += s
      //case s: CommentSymbol => printComment(s.text, s.multiline)
      case s: PackageSymbol => printPackage(s, outputPackage)
      case s: ClassSymbol =>
        files.setActiveObject(s.name)
        printClass(s)
        files.clearActiveObject(s.name)
      case s: ModuleSymbol => printModule(s)
      case s: TypeAliasSymbol => printTypeAlias(s)
      case s: FieldSymbol => printField(s)
      case s: MethodSymbol => printMethod(s)
      case s: ParamSymbol => printParam(s)
      case s: TypeParamSymbol => printTypeParam(s)
      case x => throw new IllegalStateException(s"Unhandled symbol [$x].")
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
      files.print("js.")
      files.print(name.toString)
    case QualifiedName(Name.scala, name) => files.print(name.toString)
    case QualifiedName(Name.java, Name.lang, name) => files.print(name.toString)
    case _ => files.print(x.toString)
  }
}
