package controllers

import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import services.file.FileService
import services.github.GithubService
import services.parse.TypeScriptFiles
import services.project.ProjectService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class HomeController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def home(q: Option[String], filter: Option[String]) = act("home") { implicit request =>
    githubService.listRepos(includeTemplates = false).map { repos =>
      val filteredRepos = repos.filter(_.name.contains(q.getOrElse("")))

      val srcDirs = TypeScriptFiles.list(q)
      val outDirs = FileService.getDir("out").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name)
      val projectDirs = FileService.getDir("projects").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse("scala-js-" + ""))).toSeq

      val keys = srcDirs.sorted.map(x => x -> x.replaceAllLiterally("-", "").replaceAllLiterally(".", ""))

      val filteredKeys = filter match {
        case None => keys
        case Some("all") => keys
        case Some("parsed") => keys.filter(k => outDirs.contains(k._1))
        case Some("built") => keys.filter(k => projectDirs.exists(d => d.name == ("scala-js-" + k._2)))
        case Some("published") => keys.filter(k => repos.exists(_.name == ("scala-js-" + k._2)))
        case Some(x) => throw new IllegalStateException(s"Invalid filter [$x].")
      }

      Ok(views.html.index(q, filter, filteredKeys, outDirs, projectDirs, repos, app.config.debug))
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
