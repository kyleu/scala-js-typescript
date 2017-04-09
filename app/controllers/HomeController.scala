package controllers

import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import services.github.GithubService
import services.parse.TypeScriptFiles
import services.project.{ProjectDetailsService, ProjectService}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class HomeController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def home(q: Option[String], filter: Option[String]) = act("home") { implicit request =>
    val srcDirs = TypeScriptFiles.list(q)
    val keys = srcDirs.sorted.map(x => x -> ProjectDefinition.normalize(x))

    Future.successful(Ok(views.html.home(q, filter, keys, app.config.debug)))
  }

  def list(q: Option[String], filter: Option[String]) = act("home") { implicit request =>
    githubService.listRepos(includeTemplates = false).map { repos =>
      val details = ProjectDetailsService.getAll(q, filter, repos)
      Ok(views.html.list(q, filter, details, app.config.debug))
    }
  }

  def problems() = act("problems") { implicit request =>
    githubService.listRepos(includeTemplates = false).map { repos =>
      val details = ProjectDetailsService.getAll(None, None, repos)
      Ok(views.html.problems(details, app.config.debug))
    }
  }

  def detail(key: String) = act(s"home.$key") { implicit request =>
    githubService.detail(key).map { github =>
      val outDir = ProjectService.outDirFor(key)
      val projectDir = ProjectService.projectDir(key)
      val hasRepo = (projectDir / ".git").exists
      val details = if (outDir.exists) {
        ProjectDefinition.fromJson(outDir)
      } else {
        ProjectDefinition(key, "?", "?", "?", "?")
      }

      Ok(views.html.detail(key, details, outDir, projectDir, hasRepo, github, app.config.debug))
    }
  }

  def untrail(path: String) = Action.async {
    Future.successful(MovedPermanently(s"/$path"))
  }

  def externalLink(url: String) = act("external.link") { implicit request =>
    Future.successful(Redirect(if (url.startsWith("http")) { url } else { "http://" + url }))
  }

  def ping(timestamp: Long) = act("ping") { implicit request =>
    Future.successful(Ok(timestamp.toString))
  }

  def robots() = act("robots") { implicit request =>
    Future.successful(Ok("User-agent: *\nDisallow: /"))
  }
}
