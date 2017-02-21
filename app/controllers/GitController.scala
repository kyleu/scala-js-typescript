package controllers

import play.twirl.api.Html
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
    Future.successful(Redirect(controllers.routes.GitController.detail(key)))
  }
}
