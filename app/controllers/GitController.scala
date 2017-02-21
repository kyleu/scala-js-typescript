package controllers

import play.twirl.api.Html
import services.git.GitService
import services.github.GithubService
import services.project.ProjectService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class GitController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def test() = act(s"project.test") { implicit request =>
    val result = "..."
    Future.successful(Ok(views.html.git.test(result)))
  }

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
      GitService.init(projectDir)
      Future.successful(Redirect(controllers.routes.GitController.detail(key)))
    }
  }

  def addRemote(key: String) = act(s"project.add.remote.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    GitService.addRemote(projectDir)
    Future.successful(Redirect(controllers.routes.GitController.detail(key)))
  }
}
