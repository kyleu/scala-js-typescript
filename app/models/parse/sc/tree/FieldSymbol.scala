package models.parse.sc.tree

import models.parse.sc.transform.Transformations

class FieldSymbol(prot: Boolean, nme: Name, readonly: Boolean) extends Symbol(nme) with JSNameable {
  var tpe: TypeRef = TypeRef.Any

  def tpeTranslated = tpe.copy(typeName = Transformations.forName(tpe.typeName))

  val p = if (prot) {
    "protected "
  } else {
    ""
  }
  val decl = if (readonly) {
    "val"
  } else if (name.name == name.name.toUpperCase) {
    "val"
  } else {
    "var"
  }

  override def toString = s"$jsNameStr$p$decl $name: $tpeTranslated"
}
