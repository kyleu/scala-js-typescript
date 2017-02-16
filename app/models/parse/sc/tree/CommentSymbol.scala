package models.parse.sc.tree

class CommentSymbol(val text: String) extends Symbol(Name("<comment>")) {
  override def toString = s"/* $text */"
}
