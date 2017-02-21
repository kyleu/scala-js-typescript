package controllers

import services.file.FileService
import services.parse.ProjectService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class ProjectController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def update(key: String) = act(s"detail.$key") { implicit request =>
    val result = ProjectService(key).update()
    Future.successful(Redirect(controllers.routes.HomeController.home()))
  }

  def updateAll(q: Option[String]) = act("script.all") { implicit request =>
    val scripts = FileService.getDir("out").list.filter(_.isDirectory).filter(_.name.startsWith(q.getOrElse(""))).toSeq
    val results = scripts.map(script => ProjectService(script.name).update())
    Future.successful(Redirect(controllers.routes.HomeController.home()))
  }
}
