package controllers

import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.file.FileService
import services.git.GitService
import services.github.GithubService
import services.project.{ProjectDetailsService, ProjectService}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class GitInitController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def createAll(q: Option[String]) = act("git.create.all") { implicit request =>
    val projects = ProjectDetailsService.getAll(q, Some("norepo"))
    val results = projects.map { project =>
      val projectDir = ProjectService.projectDir(project.key)
      val dir = projectDir / ".git"
      if (dir.exists) {
        project.key + ": Skipping invalid project with repo."
      } else {
        val result = GitService.init(projectDir)
        project.key + ": " + result._2
      }
    }
    Future.successful(Ok("Ok:\n\n" + results.mkString("\n")))
  }

  def create(key: String) = act(s"git.create.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    if (dir.exists) {
      throw new IllegalStateException(s"Git repo already exists for [$key].")
    } else {
      val result = GitService.init(projectDir)
      Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
    }
  }

  def addRemote(key: String) = act(s"git.add.remote.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.addRemote(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def firstCommit(key: String) = act(s"git.first.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.firstCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def secondCommit(key: String) = act(s"git.second.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.secondCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def thirdCommit(key: String) = act(s"git.third.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.thirdCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2, Some("Publish" -> controllers.routes.SbtPublishController.publish(key)))))
  }

  def fullInit(key: String) = act(s"git.full.init.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    val result1 = if (dir.exists) {
      throw new IllegalStateException(s"Git repo already exists for [$key].")
    } else {
      GitService.init(projectDir)
    }

    val proj = ProjectDefinition.fromJson(ProjectService.outDirFor(key))

    githubService.create("scala-js-" + proj.keyNormalized, proj.description).map { result =>
      val result2 = GitService.addRemote(projectDir)
      val result3 = GitService.firstCommit(projectDir)
      val result4 = GitService.secondCommit(projectDir)

      Ok(views.html.git.result(key, result4._1, result4._2, Some("Third" -> controllers.routes.GitInitController.thirdCommit(key))))
    }
  }

  def initCommitsAll(q: Option[String]) = act("git.create.all") { implicit request =>
    val projects = ProjectDetailsService.getAll(q, Some("built"))
    val results = projects.map { project =>
      val projectDir = ProjectService.projectDir(project.key)
      val commitCount = GitService.commitCount(projectDir)
      if (commitCount == 0) {
        //GitService.firstCommit(projectDir)
        //GitService.secondCommit(projectDir)
        //GitService.thirdCommit(projectDir)
        project.key + ": GO!"
      } else {
        project.key + ": Skip."
      }
    }
    Future.successful(Ok(s"Ok (${results.size}):\n\n" + results.mkString("\n")))
  }
}
