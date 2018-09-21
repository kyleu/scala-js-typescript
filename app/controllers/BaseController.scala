package controllers

import play.api.mvc._
import utils.{ Application, Logging }

import scala.concurrent.Future

abstract class BaseController() extends InjectedController with Logging {
  def app: Application

  def act(action: String)(block: Request[AnyContent] => Future[Result]) = Action.async { implicit request =>
    block(request)
  }
}
