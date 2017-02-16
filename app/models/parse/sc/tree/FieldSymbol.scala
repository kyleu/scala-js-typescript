package org.scalajs.tools.tsimporter.sc.tree

class FieldSymbol(nme: Name) extends Symbol(nme) with JSNameable {
  var tpe: TypeRef = TypeRef.Any

  override def toString = s"${jsNameStr}var $name: $tpe"
}
