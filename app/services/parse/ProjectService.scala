package services.parse

import better.files.File
import services.file.FileService

case class ProjectService(key: String) {
  val keyNormalized = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "")

  def create() = {
    val dir = copyFiles()
    replaceStrings(dir)

    val srcRoot = dir / "src" / "main" / "scala" / "org" / "scalajs"
    if (!srcRoot.isDirectory) {
      throw new IllegalStateException(s"Missing [$srcRoot].")
    }

    val srcDir = srcRoot / keyNormalized
    srcDir.createDirectories()

    val srcFile = srcDir / (keyNormalized + ".scala")
    srcFile.createIfNotExists()
    srcFile
  }

  private[this] def copyFiles() = {
    val root = FileService.getDir("projects")
    val src = root / "scala-js-template"
    val dest = root / key
    if (dest.exists) {
      dest.delete()
    }
    src.copyTo(dest)
    dest
  }

  private[this] def replaceStrings(dir: File) = {
    val files = Seq(
      dir / "build.sbt",
      dir / "README.md",
      dir / "project" / "Projects.scala",
      dir / "project" / "Shared.scala"
    )
    val replacements = Map(
      "key" -> key,
      "keyNormalized" -> keyNormalized,
      "name" -> key,
      "url" -> ("http://" + key),
      "version" -> "1.0",
      "dependencies" -> ""
    )
    def replace(f: File) = {
      val oldContent = f.contentAsString
      val newContent = replacements.foldLeft(oldContent)((content, r) => content.replaceAllLiterally("${" + r._1 + "}", r._2))
      if (oldContent != newContent) {
        f.delete()
        f.write(newContent)
      }
    }

    files.foreach(replace)
  }
}
