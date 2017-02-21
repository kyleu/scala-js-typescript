package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import services.file.FileService
import services.github.GithubService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class HomeController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def home(q: Option[String]) = act("home") { implicit request =>
    githubService.listRepos().map { repos =>
      val filteredRepos = repos.filter(_.name.startsWith("scala-js-" + q.getOrElse("")))

      val srcDirs = FileService.getDir("DefinitelyTyped").list.filter(_.isDirectory).filter(_.name.startsWith(q.getOrElse(""))).toSeq.map(_.name)
      val outDirs = FileService.getDir("out").list.filter(_.isDirectory).filter(_.name.startsWith(q.getOrElse(""))).toSeq.map(_.name)
      val projectDirs = FileService.getDir("projects").list.filter(_.isDirectory).filter(_.name.startsWith(q.getOrElse("scala-js-" + ""))).toSeq

      val keys = srcDirs.sorted.map(x => x -> x.replaceAllLiterally("-", "").replaceAllLiterally(".", ""))
      Ok(views.html.index(q, keys, outDirs, projectDirs, repos, app.config.debug))
    }
  }

  def detail(key: String) = act(s"home.$key") { implicit request =>
    githubService.detail(key).map { github =>
      val outDir = FileService.getDir("out") / key
      val projectDir = FileService.getDir("projects") / ("scala-js-" + key)
      val hasRepo = (projectDir / ".git").exists

      Ok(views.html.detail(key, outDir, projectDir, hasRepo, github, app.config.debug))
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
