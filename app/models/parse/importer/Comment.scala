package models.parse.importer

sealed trait Comment {
  def text: String
}

object Comment {
  case class SingleLine(text: String) extends Comment
  case class MultiLine(text: String) extends Comment
}
