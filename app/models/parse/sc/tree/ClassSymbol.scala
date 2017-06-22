package models.parse.sc.tree

import scala.collection.mutable

class ClassSymbol(nme: Name) extends ContainerSymbol(nme) {
  val tparams = new mutable.ListBuffer[TypeParamSymbol]
  val parents = new mutable.ListBuffer[TypeRef]
  var companionModule: ModuleSymbol = _
  var isTrait: Boolean = true
  val isFancy: Boolean = members.exists {
    case x: MethodSymbol if x.isBracketAccess || x.name.name == "apply" => true
    case _ => false
  }
  var isSealed: Boolean = false
  var isAbstract: Boolean = false

  override def toString = {
    val sl = if (isSealed) { "sealed " } else { "" }
    val a = if (isAbstract) { "abstract " } else { "" }
    val t = if (isTrait) { s"trait $name" } else { s"class $name" }
    val p = if (tparams.isEmpty) { "" } else { tparams.mkString("<", ", ", ">") }
    sl + a + t + p
  }
}
