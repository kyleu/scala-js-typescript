package controllers

import akka.util.Timeout
import models.sandbox.SandboxTask
import scala.concurrent.ExecutionContext.Implicits.global
import utils.Application

import scala.concurrent.Future
import scala.concurrent.duration._

@javax.inject.Singleton
class SandboxController @javax.inject.Inject() (override val app: Application) extends BaseController {
  implicit val timeout = Timeout(10.seconds)

  def list = act("sandbox.list") { implicit request =>
    Future.successful(Ok(views.html.sandbox.list()))
  }

  def sandbox(key: String) = act("sandbox." + key) { implicit request =>
    val sandbox = SandboxTask.withName(key)
    sandbox.run(app).map { result =>
      Ok(views.html.sandbox.run(sandbox, result))
    }
  }
}
