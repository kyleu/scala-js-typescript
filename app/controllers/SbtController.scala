package controllers

import better.files.File
import services.github.GithubService
import services.project.ProjectService
import services.sbt.{SbtHistoryService, SbtService}
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
    Future.successful(Ok(views.html.sbt.result(key, 0, status)))
  }

  def build(key: String) = act(s"sbt.build.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    if (projectDir.exists) {
      val result = SbtService.build(projectDir)
      Future.successful(Ok(views.html.sbt.result(key, result._1, result._2)))
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

  def buildAll(q: Option[String], start: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val projects = ProjectService.list(q)
    val result = projects.map(x => test(x, start))
    Future.successful {
      log.info(s"Processed [${result.size}] projects, with [${result.count(_._2 == 0)}] passing and [${result.count(_._2 != 0)}] failing.")
      Ok(views.html.sbt.results(result))
    }
  }

  def buildAllBatch(q: Option[String], start: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val projects = ProjectService.list(q)
    val batchSize = 800
    val grouped = projects.grouped(batchSize).toSeq
    log.info(s"Processing [${projects.size}] projects, in ${grouped.size} groups of $batchSize.")

    val futures = grouped.zipWithIndex.map { chunk =>
      Future {
        log.info(s"Processing chunk [${chunk._2}] containing [${chunk._1.size}] files.")
        chunk._1.map(x => test(x, start))
      }
    }

    Future.sequence(futures).map(_.flatten.toSeq).map { result =>
      log.info(s"Processed [${result.size}] projects, with [${result.count(_._2 == 0)}] passing and [${result.count(_._2 != 0)}] failing.")
      Ok(views.html.sbt.results(result))
    }
  }

  def publish(key: String) = act(s"sbt.publish.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    if (projectDir.exists) {
      val result = SbtService.publish(projectDir)
      Future.successful(Ok(views.html.sbt.result(key, result._1, result._2)))
    } else {
      throw new IllegalStateException(s"No project found for [$key].")
    }
  }

  def publishAll(q: Option[String]) = act(s"sbt.publish.all") { implicit request =>
    val result = ProjectService.list(q).map { f =>
      val x = SbtService.publish(f)
      (f.name, x._1, x._2)
    }
    Future.successful(Ok(views.html.sbt.results(result)))
  }
}
