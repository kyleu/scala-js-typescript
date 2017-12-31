package services.sbt

import services.file.FileService
import utils.DateUtils

object SbtHistoryService {
  private[this] val root = FileService.getDir("logs") / "history"
  if (!root.exists) {
    root.createDirectories()
  }
  private[this] val logDir = FileService.getDir("logs") / "sbt"
  if (!logDir.exists) {
    logDir.createDirectories()
  }

  def list() = root.children.map(_.name.stripSuffix(".csv")).toSeq.sorted

  def statuses() = {
    val projects = logDir.list.filter(_.isRegularFile).toSeq
    projects.map { p =>
      val content = p.contentAsString
      (p.name.stripPrefix("scala-js-").stripSuffix(".log"), !content.contains("[error]"))
    }
  }

  def status(key: String) = {
    val f = logDir / s"scala-js-$key.log"
    if (f.exists) {
      f
    } else {
      throw new IllegalStateException(s"No build history for [$key] (${f.path}).")
    }
  }

  def write() = {
    val file = root / (DateUtils.now.toString("yyyy-MM-dd HH:mm:ss") + ".csv")
    val data = statuses().sortBy(_._1)
    val content = data.map(x => x._1.stripPrefix("scala-js-").stripSuffix(".log") + "," + x._2).mkString("\n")
    file.write(content)
  }

  def read(key: String) = {
    val file = root / (key + ".csv")
    if (!file.exists) {
      throw new IllegalStateException(s"Missing file [${file.path}].")
    }
    file.lines.toSeq.filterNot(_.isEmpty).map { line =>
      val split = line.split(',').toList
      split match {
        case one :: two :: Nil => one -> two.toBoolean
        case _ => throw new IllegalStateException(s"Invalid line [$line].")
      }
    }
  }

  def delete(key: String) = {
    val file = root / (key + ".csv")
    if (!file.exists) {
      throw new IllegalStateException(s"Missing file to delete [${file.path}].")
    }
    file.delete()
  }
}
