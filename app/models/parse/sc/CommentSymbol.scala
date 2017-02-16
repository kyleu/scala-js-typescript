package org.scalajs.tools.tsimporter.sc

class CommentSymbol(val text: String) extends Symbol(Name("<comment>")) {
  override def toString = s"/* $text */"
}
