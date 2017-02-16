package models.parse.sc.tree

class FieldSymbol(prot: Boolean, nme: Name, readonly: Boolean) extends Symbol(nme) with JSNameable {
  var tpe: TypeRef = TypeRef.Any
  val p = if (prot) { "protected " } else { "" }
  val decl = if (readonly) { "val" } else { "var" }
  override def toString = s"$jsNameStr$p$decl $name: $tpe"
}
