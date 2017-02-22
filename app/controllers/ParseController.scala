package controllers

import better.files._
import models.parse.parser.tree.DeclTree
import services.file.FileService
import services.parse.{PrinterService, TypeScriptImport}
import utils.Application

import scala.concurrent.Future

object ParseController {
  case class Result(key: String, src: File, tree: Option[List[DeclTree]], text: Seq[String]) {
    import upickle.default._
    lazy val json = tree.map(t => write(t, 2))
  }
}

@javax.inject.Singleton
class ParseController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def parse(key: String) = act(s"detail.$key") { implicit request =>
    val result = parseLibrary(key)
    Future.successful(Ok(views.html.parse.process(key, result.src, result.tree, result.json, result.text, app.config.debug)))
  }

  def parseAll(q: Option[String]) = act("script.all") { implicit request =>
    val scripts = FileService.getDir("DefinitelyTyped").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq
    val results = scripts.map { script =>
      log.info(s"Parsing [${script.name}]...")
      parseLibrary(script.name)
    }
    Future.successful(Ok(views.html.parse.processAll(results, app.config.debug)))
  }

  private[this] def parseLibrary(key: String) = {
    val dir = FileService.getDir("DefinitelyTyped") / key
    val ts = dir / "index.d.ts"
    val content = ts.contentAsString
    val tree = TypeScriptImport.parse(content)
    val res = tree match {
      case Right(t) => Some(t) -> PrinterService(key, t).export()
      case Left(err) => None -> Seq("Error: " + err)
    }
    ParseController.Result(key, ts, res._1, res._2)
  }
}
