package controllers

import fastparse.core.Parsed
import models.parse.importer.ParseTest
import services.file.FileService
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class TestController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def script(key: String) = act(s"detail.$key") { implicit request =>
    val dir = FileService.getDir("DefinitelyTyped") / key
    val ts = dir / "index.d.ts"

    val definitions: Either[String, Seq[String]] = Right(Nil)

    Future.successful(Ok(views.html.parse.detail(dir.name, ts, definitions, "TODO", app.config.debug)))
  }

  def test(key: String) = act(s"detail.$key") { implicit request =>
    val f = FileService.getDir("test") / s"$key.d.ts"
    val content = f.contentAsString
    val ret = ParseTest.ws.parse(content) match {
      case Parsed.Success(value, idx) => Right(value)
      case Parsed.Failure(parser, idx, extra) => Left(extra.traced.trace)
    }
    Future.successful(Ok(views.html.parse.test(key, f, ret, app.config.debug)))
  }
}
