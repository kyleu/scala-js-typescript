package services.parse

import java.nio.charset.MalformedInputException

import better.files._
import models.parse.ProjectDefinition

object TypeScriptFiles {
  private[this] val source = "data" / "typescript"
  if (!source.exists) {
    source.createDirectory()
  }

  private[this] val originalFiles = "typescript" / "DefinitelyTyped" / "types"
  private[this] val overrideFiles = "typescript" / "DefinitelyScala"

  def copy() = {
    val original = originalFiles.list.filter(_.isDirectory).toSeq.map(_.name)
    val over = overrideFiles.list.filter(_.isRegularFile).toSeq.map(_.name.stripSuffix(".ts")).filterNot(_.startsWith("."))
    val keys = (original ++ over).distinct.sorted

    source.delete(swallowIOExceptions = true)
    source.createDirectories()

    var skipped = 0

    keys.foreach { key =>
      val normalized = ProjectDefinition.normalize(key)

      val src = if ((overrideFiles / s"$normalized.ts").exists) {
        overrideFiles / s"$normalized.ts"
      } else {
        originalFiles / key / "index.d.ts"
      }

      if (!src.exists) {
        throw new IllegalStateException(s"Cannot read [${src.path}].")
      }

      val out = source / s"$normalized.ts"
      if (out.exists) {
        skipped += 1
        out.delete()
      }

      val lines = try {
        src.lines
      } catch {
        case x: MalformedInputException => Nil // throw new IllegalStateException(s"Failed to read [${src.pathAsString}].", x) }
      }
      out.write(lines.mkString("\n"))
    }

    keys.size -> skipped
  }

  def list(q: Option[String]) = {
    source.list.filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name.stripSuffix(".ts")).sorted
  }

  def getContent(key: String) = {
    (source / (key + ".ts")).contentAsString
  }
}
