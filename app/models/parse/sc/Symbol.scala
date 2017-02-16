package org.scalajs.tools.tsimporter.sc

class Symbol(val name: Name) {
  override def toString = s"${this.getClass.getSimpleName}($name)}"
}
