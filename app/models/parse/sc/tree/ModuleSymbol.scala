package org.scalajs.tools.tsimporter.sc.tree

class ModuleSymbol(nme: Name) extends ContainerSymbol(nme) {
  var companionClass: ClassSymbol = _

  override def toString = s"object $name"
}
