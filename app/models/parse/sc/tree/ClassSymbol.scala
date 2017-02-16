package org.scalajs.tools.tsimporter.sc.tree

import scala.collection.mutable

class ClassSymbol(nme: Name) extends ContainerSymbol(nme) {
  val tparams = new mutable.ListBuffer[TypeParamSymbol]
  val parents = new mutable.ListBuffer[TypeRef]
  var companionModule: ModuleSymbol = _
  var isTrait: Boolean = true
  var isSealed: Boolean = false

  override def toString = {
    val sl = if (isSealed) { "sealed " } else { "" }
    val t = if (isTrait) { s"trait $name" } else { s"class $name" }
    val p = if (tparams.isEmpty) { "" } else { tparams.mkString("<", ", ", ">") }
    sl + t + p
  }
}
