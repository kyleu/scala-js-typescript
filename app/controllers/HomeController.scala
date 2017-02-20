package controllers

import play.api.mvc.Action
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class HomeController @javax.inject.Inject() (override val app: Application, testController: TestController) extends BaseController {
  def home() = act("home") { implicit request =>
    Future.successful(Ok(views.html.index(app.config.debug)))
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
