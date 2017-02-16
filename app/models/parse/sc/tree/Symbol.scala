package org.scalajs.tools.tsimporter.sc.tree

class Symbol(val name: Name) {
  override def toString = s"${this.getClass.getSimpleName}($name)}"
}
