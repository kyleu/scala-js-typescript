package controllers

import models.parse.parser.tree.{DeclTree, LineCommentDecl}
import models.parse.sc.printer.{Printer, PrinterFilesSingle}
import models.parse.{Importer, ProjectDefinition}
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

  private[this] def extractFrom(key: String, t: List[DeclTree]) = {
    val comments = t.flatMap {
      case c: LineCommentDecl => Some(c.text.trim)
      case _ => None
    }
    val (name, version) = comments.find(_.startsWith("Type definitions for")) match {
      case Some(l) =>
        val str = l.substring("Type definitions for".length + 1).trim.split(' ')
        str.dropRight(1).mkString(" ") -> str.last
      case None => key -> "0.1"
    }
    val url = comments.find(_.startsWith("Project:")) match {
      case Some(l) => l.substring("Project:".length + 1).trim
      case None => key
    }
    val authors = comments.find(_.startsWith("Definitions by:")) match {
      case Some(l) => l.substring("Definitions by:".length + 1).trim
      case None => key
    }

    val p = ProjectDefinition(key, name, url, version, authors)
    println(p)
    p -> t
  }

  def script(key: String) = act(s"detail.$key") { implicit request =>
    val dir = FileService.getDir("DefinitelyTyped") / key
    val ts = dir / "index.d.ts"
    val content = ts.contentAsString
    val tree = TypeScriptImport.parse(content)
    val res = tree match {
      case Right(t) =>
        val (project, decls) = extractFrom(key, t)
        val proj = ProjectService(project)
        val srcFile = proj.create()

        val pkg = new Importer(key).apply(decls)

        val single = PrinterFilesSingle(project, srcFile)
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
