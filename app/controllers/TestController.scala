package controllers

import models.parse.Importer
import models.parse.sc.printer.{Printer, PrinterFilesMulti, PrinterFilesSingle}
import services.file.FileService
import services.parse.{ProjectService, TypeScriptImport}
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class TestController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def index() = act("index") { implicit request =>
    val scripts = FileService.getDir("DefinitelyTyped").list.filter(_.isDirectory).toSeq
    Future.successful(Ok(views.html.parse.index(scripts, app.config.debug)))
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
      case Right(t) =>
        val proj = ProjectService(key)
        val srcFile = proj.create()

        val pkg = new Importer(key).apply(t)

        val single = PrinterFilesSingle(key, srcFile)
        new Printer(single, key).printSymbol(pkg)
        single.onComplete()

        //val multi = PrinterFilesMulti(key)
        //new Printer(multi, key).printSymbol(pkg)

        single.file.contentAsString
      case Left(err) => "Error: " + err
    }

    Future.successful(Ok(views.html.parse.script(dir.name, ts, tree, res, app.config.debug)))
  }
}
