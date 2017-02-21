package services.parse

import models.parse.ProjectDefinition
import services.file.FileService

case class ProjectService(key: String) {
  val outDir = FileService.getDir("out") / key

  if (!outDir.exists) {
    throw new IllegalStateException(s"Missing out dir [$outDir].")
  }

  private[this] def updateProject(project: ProjectDefinition) = {
    val proj = ProjectOutput(project)
    val projectSrcDir = if (proj.exists()) {
      proj.scalaRoot
    } else {
      proj.create()
    }
    projectSrcDir.delete()
    projectSrcDir.createDirectory()

    outDir.copyTo(projectSrcDir)
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
