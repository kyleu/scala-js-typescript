package org.scalajs.tools.tsimporter.sc

import org.scalajs.tools.tsimporter.Utils

object Name {
  val scala = Name("scala")
  val scalajs = Name("scalajs")
  val js = Name("js")
  val java = Name("java")
  val lang = Name("lang")

  val EMPTY = Name("")
  val CONSTRUCTOR = Name("<init>")
  val REPEATED = Name("*")
  val SINGLETON = Name("<typeof>")
}

case class Name(name: String) {
  override def toString = Utils.scalaEscape(name)
}
