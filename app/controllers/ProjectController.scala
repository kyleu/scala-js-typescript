package controllers

import services.file.FileService
import services.project.ProjectService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class ProjectController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def update(key: String) = act(s"detail.$key") { implicit request =>
    val created = ProjectService(key).update()
    Future.successful(Ok(views.html.project.update(key, created, app.config.debug)))
  }

  def updateAll(q: Option[String]) = act("script.all") { implicit request =>
    val outDirs = FileService.getDir("out").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq
    val results = outDirs.map(outDir => outDir.name -> ProjectService(outDir.name).update())
    Future.successful(Ok(views.html.project.updateAll(results, app.config.debug)))
  }
}
