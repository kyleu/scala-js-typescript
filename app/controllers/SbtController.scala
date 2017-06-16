package controllers

import better.files.File
import services.github.GithubService
import services.project.ProjectService
import services.sbt.{SbtHistoryService, SbtResultParser, SbtService}
import utils.Application
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

@javax.inject.Singleton
class SbtController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list(q: Option[String]) = act(s"sbt.list") { implicit request =>
    val statuses = SbtHistoryService.statuses()
    Future.successful(Ok(views.html.sbt.list(q, statuses)))
  }

  def last(key: String) = act(s"sbt.last") { implicit request =>
    val status = SbtHistoryService.status(key)
    val result = SbtResultParser.parse(status)
    Future.successful(Ok(views.html.sbt.result(key, result, status.contentAsString)))
  }

  def clean(key: String) = act(s"sbt.build.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val buildResult = SbtService.clean(projectDir)
    val result = SbtResultParser.Result(key, Nil, Nil, Nil)
    Future.successful(Ok(views.html.sbt.result(key, result, buildResult._2)))
  }

  def build(key: String) = act(s"sbt.build.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    if (projectDir.exists) {
      val buildResult = SbtService.build(projectDir)
      val status = SbtHistoryService.status(key)
      val result = SbtResultParser.parse(status)
      Future.successful(Ok(views.html.sbt.result(key, result, buildResult._2)))
    } else {
      throw new IllegalStateException(s"No project found for [$key].")
    }
  }

  def buildForm(q: Option[String]) = act(s"sbt.build.all") { implicit request =>
    Future.successful(Ok(views.html.sbt.form(q)))
  }

  private[this] def test(f: File, start: Option[String]) = {
    val name = f.name.stripPrefix("scala-js-")
    if (start.exists(_ > name)) {
      (f.name, 0, "Skipped")
    } else {
      if (name == "clone" || name == "notify") {
        (f.name, 0, "Ignored")
      } else {
        val x = SbtService.build(f)
        (f.name, x._1, x._2)
      }
    }
  }

  def formatAll(q: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val projects = ProjectService.list(q)
    val result = projects.par.map { x =>
      val ret = SbtService.format(x)
      (x.name, ret._1, ret._2)
    }.seq
    Future.successful {
      log.info(s"Formatted [${result.size}] projects.")
      Ok(views.html.sbt.results(result))
    }
  }

  def buildAll(q: Option[String], start: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val projects = ProjectService.list(q)
    val result = projects.par.map(x => test(x, start)).seq
    Future.successful {
      log.info(s"Processed [${result.size}] projects, with [${result.count(_._2 == 0)}] passing and [${result.count(_._2 != 0)}] failing.")
      Ok(views.html.sbt.results(result))
    }
  }

  def cleanAll(q: Option[String]) = act(s"sbt.clean.all") { implicit request =>
    val projects = ProjectService.list(q)
    val result = projects.par.map { x =>
      val ret = SbtService.clean(x)
      (x.name, ret._1, ret._2)
    }.seq
    Future.successful {
      log.info(s"Processed [${result.size}] projects, with [${result.count(_._2 == 0)}] passing and [${result.count(_._2 != 0)}] failing.")
      Ok(views.html.sbt.results(result))
    }
  }
}
