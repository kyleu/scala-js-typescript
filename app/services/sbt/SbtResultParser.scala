package services.sbt

import better.files._

object SbtResultParser {
  case class Error(content: String)

  case class Result(key: String, infoLines: Seq[(String, Int)], errorLines: Seq[(String, Int)], errors: Seq[Error]) {
    val success = errors.isEmpty
  }

  private[this] def linesFor(lines: Array[(String, Int)], tag: String) = {
    lines.filter(_._1.startsWith(tag)).map(x => x._1.stripPrefix(tag).replaceAllLiterally("^", "").trim -> x._2).filter(_._1.nonEmpty)
  }

  private[this] def parseErrors(lines: Array[(String, Int)]) = if (lines.isEmpty) {
    Array.empty[Error]
  } else {
    lines.zipWithIndex.flatMap {
      case ((line, lineNum), arrayIdx) => line match {
        case _ if line.contains(".scala") =>
          val trimmed = line.substring(line.indexOf("com/"))
          val others = lines.drop(arrayIdx + 1).takeWhile(x => (!x._1.contains(".scala")) && (!x._1.contains("errors found")))
          Some(Error(trimmed + "\n" + others.map("  " + _._1).mkString("\n")))
        case _ => None
      }
    }
  }

  def parse(f: File) = {
    val key = f.name.stripPrefix("scala-js-").stripSuffix(".log")

    val lines = f.lines.toArray.zipWithIndex

    val infoLines = linesFor(lines, "[info] ")
    val errorLines = linesFor(lines, "[error] ")

    val errors = parseErrors(errorLines)
    Result(key, infoLines, errorLines, errors)
  }
}
