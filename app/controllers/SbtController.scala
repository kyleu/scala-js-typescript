package controllers

import services.file.FileService
import services.github.GithubService
import services.project.ProjectService
import services.sbt.{SbtHistoryService, SbtService}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class SbtController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list(q: Option[String]) = act(s"sbt.list") { implicit request =>
    val statuses = SbtHistoryService.statuses()
    Future.successful(Ok(views.html.sbt.list(q, statuses)))
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

  def buildAll(q: Option[String], start: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val result = ProjectService.list(q).map { f =>
      val name = f.name.stripPrefix("scala-js-")
      if (start.exists(_ > name)) {
        (f.name, 0, "Skipped")
      } else {
        val x = SbtService.build(f)
        (f.name, x._1, x._2)
      }
    }
    Future.successful(Ok(views.html.sbt.results(result)))
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

  def history() = act(s"sbt.history") { implicit request =>
    val result = SbtHistoryService.list()
    Future.successful(Ok(views.html.sbt.history(result)))
  }

  def historyCompare() = act(s"sbt.history.compare") { implicit request =>
    val files = request.queryString.getOrElse("q", throw new IllegalStateException("Missing [q] param.")).toList
    files match {
      case lName :: rName :: Nil =>
        val lSeq = SbtHistoryService.read(lName)
        val rSeq = SbtHistoryService.read(rName)
        val keys = (lSeq.map(_._1) ++ rSeq.map(_._1)).distinct.sorted
        Future.successful(Ok(views.html.sbt.historyCompare(keys, lName, lSeq.toMap, rName, rSeq.toMap)))
      case _ => throw new IllegalStateException(s"Invalid [q] param: [${files.mkString(", ")}].")
    }

  }

  def historyWrite() = act(s"sbt.history.write") { implicit request =>
    SbtHistoryService.write()
    Future.successful(Redirect(routes.SbtController.history()))
  }

  def historyDetail(key: String) = act(s"sbt.history.detail") { implicit request =>
    val result = SbtHistoryService.read(key)
    Future.successful(Ok(views.html.sbt.historyDetail(key, result)))
  }

  def historyDelete(key: String) = act(s"sbt.history.delete") { implicit request =>
    SbtHistoryService.delete(key)
    Future.successful(Redirect(routes.SbtController.history()))
  }
}
