package services.parse

import better.files._
import models.parse.ProjectDefinition
import services.file.FileService

case class ProjectService(project: ProjectDefinition) {
  def create() = {
    val dir = copyFiles()
    replaceStrings(dir)

    val srcRoot = dir / "src" / "main" / "scala" / "org" / "scalajs"
    if (!srcRoot.isDirectory) {
      throw new IllegalStateException(s"Missing [$srcRoot].")
    }

    val srcDir = srcRoot / project.keyNormalized
    srcDir.createDirectories()

    srcDir
  }

  private[this] def copyFiles() = {
    val src = "util" / "scala-js-template"
    val dest = FileService.getDir("projects") / ("scala-js-" + project.keyNormalized)
    if (dest.exists) {
      dest.delete()
    }
    src.copyTo(dest)
    (dest / ".git").delete()
    (dest / "readme.md").delete()
    (dest / "template.readme.md").renameTo("readme.md")
    dest
  }

  private[this] def replaceStrings(dir: File) = {
    val files = Seq(
      dir / "build.sbt",
      dir / "README.md",
      dir / "project" / "Projects.scala"
    )
    val replacements = project.asMap
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
