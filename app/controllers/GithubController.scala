package controllers

import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.twirl.api.Html
import services.file.FileService
import services.github.GithubService
import utils.Application

@javax.inject.Singleton
class GithubController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def test() = act(s"project.test") { implicit request =>
    githubService.test().map { result =>
      Ok(views.html.github.test(result))
    }
  }

  def detail(key: String) = act(s"project.$key") { implicit request =>
    githubService.detail(key).map {
      case Some(repo) => Ok(views.html.github.detail(repo))
      case None => Ok(Html(s"Github repo for [$key] not found."))
    }
  }

  def create(key: String) = act(s"project.create.$key") { implicit request =>
    val file = FileService.getDir("out") / key / "project.json"
    val content = file.contentAsString
    val proj = upickle.default.read[ProjectDefinition](content)
    githubService.create("scala-js-" + proj.keyNormalized, proj.description).map { result =>
      Redirect(controllers.routes.GithubController.detail(key))
    }
  }
}
