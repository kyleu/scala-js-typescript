package services.sbt

import better.files._

object SbtResultParser {
  case class Error(content: String)

  case class Result(key: String, infoLines: Seq[(String, Int)], errorLines: Seq[(String, Int)], errors: Seq[Error]) {
    val success = errors.isEmpty
  }

  private[this] val infoTag = "[info] "
  private[this] val errorTag = "[error] "

  def parse(f: File) = {
    val key = f.name.stripPrefix("scala-js-").stripSuffix(".log")

    val lines = f.lines.toArray.zipWithIndex

    val infoLines = lines.filter(_._1.startsWith(infoTag)).map(x => x._1.stripPrefix(infoTag) -> x._2)
    val errorLines = lines.filter(_._1.startsWith(errorTag)).map(x => x._1.stripPrefix(errorTag) -> x._2)

    val errors = Nil
    Result(key, infoLines, errorLines, errors)
  }
}
