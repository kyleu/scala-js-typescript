package controllers

import play.api.libs.ws.WSClient
import services.file.FileService
import services.github.GithubService
import utils.Application
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

@javax.inject.Singleton
class GithubController @javax.inject.Inject() (override val app: Application, ws: WSClient) extends BaseController {
  val githubService = GithubService(ws)

  def test() = act(s"project.test") { implicit request =>
    githubService.test().map { result =>
      Ok(views.html.github.test(result))
    }
  }

  def list = act(s"projects") { implicit request =>
    val projectDirs = FileService.getDir("projects").list.filter(_.isDirectory).toSeq.map(_.name)
    githubService.listRepos().map { repos =>
      Ok(views.html.github.list(repos, projectDirs))
    }
  }

  def detail(key: String) = act(s"project.$key") { implicit request =>
    githubService.detail(key).map { repo =>
      Ok(views.html.github.detail(repo))
    }
  }

  def create(key: String) = act(s"project.create.$key") { implicit request =>
    githubService.create(key).map { result =>
      Redirect(controllers.routes.GithubController.detail(key))
    }
  }
}
