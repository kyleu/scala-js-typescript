package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import utils.metrics.Instrumented
import utils.{Application, Logging}

import scala.concurrent.Future

abstract class BaseController() extends Controller with Instrumented with Logging {
  def app: Application

  def act(action: String)(block: (Request[AnyContent]) => Future[Result]) = Action.async { implicit request =>
    metrics.timer(action).timeFuture {
      block(request)
    }
  }
}
