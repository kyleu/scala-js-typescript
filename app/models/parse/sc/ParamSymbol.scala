package org.scalajs.tools.tsimporter.sc

class ParamSymbol(nme: Name) extends Symbol(nme) {
  def this(nme: Name, tpe: TypeRef) = {
    this(nme)
    this.tpe = tpe
  }

  var optional: Boolean = false
  var tpe: TypeRef = TypeRef.Any

  override def toString =
    s"$name: $tpe" + (if (optional) " = _" else "")

  override def equals(that: Any): Boolean = that match {
    case that: ParamSymbol => this.name == that.name && this.tpe == that.tpe
    case _ => false
  }
}
