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
