package controllers

import play.twirl.api.Html
import services.git.GitService
import services.github.GithubService
import services.project.ProjectService
import utils.Application

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
}
