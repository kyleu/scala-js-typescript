package services.project

import better.files._
import models.parse.ProjectDefinition

case class ProjectOutput(project: ProjectDefinition, projectDir: File) {
  def scalaRoot = projectDir / "src" / "main" / "scala" / "org" / "scalajs" / project.keyNormalized

  def exists() = projectDir.exists()

  def create() = {
    val dir = copyFiles()
    replaceStrings(dir)
    val srcRoot = scalaRoot
    if (srcRoot.exists && !srcRoot.isDirectory) {
      throw new IllegalStateException(s"Invalid [$srcRoot].")
    }

    scalaRoot.createDirectories()
    scalaRoot
  }

  private[this] def copyFiles() = {
    val src = "util" / "scala-js-template"
    val dest = projectDir
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
      dir / "readme.md",
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
