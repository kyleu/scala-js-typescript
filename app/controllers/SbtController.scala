package controllers

import services.file.FileService
import services.github.GithubService
import services.project.ProjectService
import services.sbt.SbtService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class SbtController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list(q: Option[String]) = act(s"sbt.list") { implicit request =>
    val projects = (FileService.getDir("logs") / "sbt").list.filter(_.isRegularFile).filter(_.name.contains(q.getOrElse(""))).toSeq
    val statuses = projects.map { p =>
      val content = p.contentAsString
      (p.name, content.contains("[success]"))
    }
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

  def buildAll(q: Option[String]) = act(s"sbt.build.all") { implicit request =>
    val result = ProjectService.list(q).map { f =>
      val x = SbtService.build(f)
      (f.name, x._1, x._2)
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
}
