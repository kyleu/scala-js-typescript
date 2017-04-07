package controllers

import services.github.GithubService
import services.sbt.SbtHistoryService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class SbtHistoryController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list() = act(s"sbt.history") { implicit request =>
    val result = SbtHistoryService.list()
    Future.successful(Ok(views.html.history.list(result)))
  }

  def compare() = act(s"sbt.history.compare") { implicit request =>
    val files = request.queryString.getOrElse("q", throw new IllegalStateException("Missing [q] param.")).toList
    files match {
      case lName :: rName :: Nil =>
        val lSeq = SbtHistoryService.read(lName)
        val rSeq = SbtHistoryService.read(rName)
        val keys = (lSeq.map(_._1) ++ rSeq.map(_._1)).distinct.sorted
        Future.successful(Ok(views.html.history.compare(keys, lName, lSeq.toMap, rName, rSeq.toMap)))
      case _ => throw new IllegalStateException(s"Invalid [q] param: [${files.mkString(", ")}].")
    }

  }

  def write() = act(s"sbt.history.write") { implicit request =>
    SbtHistoryService.write()
    Future.successful(Redirect(routes.SbtHistoryController.list()))
  }

  def detail(key: String) = act(s"sbt.history.detail") { implicit request =>
    val result = SbtHistoryService.read(key)
    Future.successful(Ok(views.html.history.detail(key, result)))
  }

  def delete(key: String) = act(s"sbt.history.delete") { implicit request =>
    SbtHistoryService.delete(key)
    Future.successful(Redirect(routes.SbtHistoryController.list()))
  }
}
