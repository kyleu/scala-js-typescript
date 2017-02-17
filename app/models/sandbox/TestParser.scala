package models.sandbox

import better.files._
import models.parse.parser.CommentRegistry

object TestParser {
  def test() = {
    val f = "data" / "DefinitelyTyped" / "debug" / "index.d.ts"
    val lines = f.lines
    val reg = CommentRegistry.parseFile("debug", lines)
    "OK: " + reg.dump()
  }
}
