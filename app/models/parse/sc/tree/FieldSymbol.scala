package org.scalajs.tools.tsimporter.sc.tree

class FieldSymbol(nme: Name, readonly: Boolean) extends Symbol(nme) with JSNameable {
  var tpe: TypeRef = TypeRef.Any
  val decl = if(readonly) { "val" } else { "var" }
  override def toString = s"$jsNameStr$decl $name: $tpe"
}
