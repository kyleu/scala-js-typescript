package controllers

import services.file.FileService
import services.parse.{ScalaExport, TypeScriptImport}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class TestController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def index() = act("index") { implicit request =>
    val scripts = FileService.getDir("DefinitelyTyped").list.filter(_.isDirectory).toSeq
    val tests = FileService.getDir("test").list.toSeq.map(_.name.stripSuffix(".d.ts"))
    Future.successful(Ok(views.html.parse.index(scripts, tests, app.config.debug)))
  }

  def allScripts() = act("script.all") { implicit request =>
    val scripts = FileService.getDir("DefinitelyTyped").list.filter(_.isDirectory).toSeq
    Future.successful(Ok(views.html.parse.allScripts(scripts, app.config.debug)))
  }

  def script(key: String) = act(s"detail.$key") { implicit request =>
    val dir = FileService.getDir("DefinitelyTyped") / key
    val ts = dir / "index.d.ts"
    val content = ts.contentAsString
    val tree = TypeScriptImport.parse(content)
    val res = tree match {
      case Left(err) => "Parse Error: " + err
      case Right(decls) => ScalaExport.print(decls)
    }

    Future.successful(Ok(views.html.parse.script(dir.name, ts, tree, res, app.config.debug)))
  }

  def allTests() = act("script.all") { implicit request =>
    val tests = FileService.getDir("test").list.toSeq.map(_.name.stripSuffix(".d.ts"))
    Future.successful(Ok(views.html.parse.allTests(tests, app.config.debug)))
  }

  def test(key: String) = act(s"detail.$key") { implicit request =>
    val f = FileService.getDir("test") / s"$key.d.ts"
    val content = f.contentAsString
    val ret = TypeScriptImport.parse(content)
    val res = ret match {
      case Left(err) => "Parse Error: " + err
      case Right(decls) => ScalaExport.print(decls)
    }
    Future.successful(Ok(views.html.parse.test(key, f, ret, res, app.config.debug)))
  }
}
