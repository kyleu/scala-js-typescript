package controllers

import models.parse.ProjectDefinition
import play.twirl.api.Html
import services.file.FileService
import services.git.GitService
import services.github.GithubService
import services.project.ProjectService
import utils.Application
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

@javax.inject.Singleton
class GitController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def detail(key: String) = act(s"project.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    if (dir.exists) {
      Future.successful(Ok(views.html.git.detail(key)))
    } else {
      Future.successful(Ok(Html(s"Git repo for [$key] not found.")))
    }
  }

  def create(key: String) = act(s"project.create.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    if (dir.exists) {
      throw new IllegalStateException(s"Git repo already exists for [$key].")
    } else {
      val result = GitService.init(projectDir)
      Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
    }
  }

  def addRemote(key: String) = act(s"project.add.remote.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.addRemote(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def firstCommit(key: String) = act(s"project.first.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.firstCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def secondCommit(key: String) = act(s"project.second.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.secondCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def thirdCommit(key: String) = act(s"project.third.commit.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.thirdCommit(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def fullInit(key: String) = act(s"project.full.init.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    val result1 = if (dir.exists) {
      throw new IllegalStateException(s"Git repo already exists for [$key].")
    } else {
      GitService.init(projectDir)
    }

    val proj = ProjectDefinition.fromJson(FileService.getDir("out") / key)

    githubService.create("scala-js-" + proj.keyNormalized, proj.description).map { result =>
      val result2 = GitService.addRemote(projectDir)
      val result3 = GitService.firstCommit(projectDir)
      val result4 = GitService.secondCommit(projectDir)

      Ok(views.html.git.result(key, result4._1, result4._2))
    }
  }

  def status(key: String) = act(s"project.status.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.status(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def update(key: String) = act(s"project.update.$key") { implicit request =>
    val msg = request.body.asFormUrlEncoded.get("msg").mkString
    val result = push(key, Nil, msg)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }
  def updateAll() = act("project.update.all") { implicit request =>
    val msg = request.body.asFormUrlEncoded.get("msg").mkString
    val results = githubService.listRepos(includeTemplate = false).map { repos =>
      repos.map { repo =>
        val result = push(repo.name, Nil, msg)
        (repo.name, result._1, result._2)
      }
    }
    results.map(r => Ok(views.html.git.results(r)))
  }

  private[this] def push(key: String, files: Seq[String], msg: String) = {
    val projectDir = ProjectService.projectDir(key)
    GitService.pushUpdate(projectDir, files, msg)
  }
}
