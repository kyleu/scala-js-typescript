package services.project

import models.parse.ProjectDefinition
import services.file.FileService

object ProjectService {
  private[this] val dir = FileService.getDir("projects")

  def list(q: Option[String]) = dir.list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq

  def projectDir(key: String) = {
    val keyNormalized = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "")
    dir / ("scala-js-" + keyNormalized)
  }
}

case class ProjectService(key: String) {
  val outDir = FileService.getDir("out") / key

  if (!outDir.exists) {
    throw new IllegalStateException(s"Missing out dir [$outDir].")
  }

  private[this] def updateProject(project: ProjectDefinition) = {
    val proj = ProjectOutput(project, ProjectService.projectDir(project.key))
    val (created, projectSrcDir) = if (proj.exists()) {
      false -> proj.scalaRoot
    } else {
      true -> proj.create()
    }
    projectSrcDir.delete()
    projectSrcDir.createDirectory()

    outDir.copyTo(projectSrcDir)

    (projectSrcDir / "project.json").delete()
    created
  }

  def update() = {
    val f = outDir / "project.json"
    if (!f.exists) {
      throw new IllegalStateException(s"Missing project definition at [$f].")
    }
    val project = upickle.default.read[ProjectDefinition](f.contentAsString)
    updateProject(project)
  }
}
