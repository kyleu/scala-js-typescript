package models.parse.parser

object CommentRegistry {
  case class Comment(line: Int, col: Int, isMulti: Boolean, text: String) {
    override def toString = s"($line:$col): $text"
  }

  def parseFile(key: String, lines: Traversable[String]) = {
    val reg = CommentRegistry(key)

    var lastMultiStart = -1 -> -1
    var multilineBuffer = ""

    lines.toSeq.zipWithIndex.foreach { line =>
      if (multilineBuffer.isEmpty) {
        line._1.indexOf("/*") match {
          case -1 => // noop
          case x =>
            lastMultiStart = line._2 -> x
            multilineBuffer += line._1.substring(x)
        }
      }

      if (multilineBuffer.isEmpty) {
        line._1.indexOf("//") match {
          case -1 => None
          case x => reg.registerSingle(line._2, x, Nil, line._1.substring(x), multiline = false)
        }
      } else {
        line._1.indexOf("*/") match {
          case -1 => multilineBuffer += line
          case x =>
            multilineBuffer += ("\n" + line._1.substring(0, x + 2))
            reg.registerSingle(lastMultiStart._1, lastMultiStart._2, Nil, multilineBuffer, multiline = true)
            multilineBuffer = ""
        }
      }
    }
    reg
  }
}

case class CommentRegistry(key: String) {
  val comments = collection.mutable.HashMap.empty[Seq[String], Seq[CommentRegistry.Comment]]

  def registerSingle(line: Int, col: Int, path: Seq[String], comment: String, multiline: Boolean) = {
    val existing = comments.getOrElseUpdate(path, Nil)
    comments(path) = existing :+ CommentRegistry.Comment(line, col, multiline, comment)
  }

  def dump() = {
    println(s"Comment Registry for [$key].")
    comments.foreach { c =>
      println(c._1.mkString("/") + ":")
      c._2.foreach { x =>
        println(s"  - $x")
      }
    }
  }
}
