package org.scalajs.tools.tsimporter.sc.tree

class TypeParamSymbol(nme: Name, val upperBound: Option[TypeRef]) extends Symbol(nme) {
  override def toString = {
    nme.toString + upperBound.fold("")(bound => s" <: $bound")
  }

  override def equals(that: Any): Boolean = that match {
    case that: TypeParamSymbol => this.name == that.name && this.upperBound == that.upperBound
    case _ => false
  }
}
