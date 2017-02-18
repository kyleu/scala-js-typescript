package models.parse.sc.tree

class CommentSymbol(val text: String, val multiline: Boolean) extends Symbol(Name("<comment>")) {
  val cleanedText = text.replaceAllLiterally("@link", "")

  override def toString = if (multiline) {
    s"/*$cleanedText*/"
  } else {
    s"//$cleanedText"
  }
}
