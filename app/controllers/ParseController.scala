package controllers

import better.files._
import models.parse.parser.tree.DeclTree
import services.file.FileService
import services.parse.{PrinterService, TypeScriptFiles, TypeScriptImport}
import utils.Application

import scala.concurrent.Future

object ParseController {
  case class Result(key: String, content: String, tree: Option[List[DeclTree]], text: Seq[String])
}

@javax.inject.Singleton
class ParseController @javax.inject.Inject() (override val app: Application) extends BaseController {
  def parseAll(q: Option[String]) = act("parse.all") { implicit request =>
    val scripts = TypeScriptFiles.list(q)
    val results = scripts.map { script =>
      log.info(s"Parsing [$script]...")
      parseLibrary(script)
    }
    Future.successful(Ok(views.html.parse.processAll(results, app.config.debug)))
  }

  def parse(key: String) = act(s"parse.$key") { implicit request =>
    val result = parseLibrary(key)
    Future.successful(Ok(views.html.parse.process(key, result.content, result.tree, None, result.text, app.config.debug)))
  }

  def refresh(key: String) = act(s"refresh.$key") { implicit request =>
    val result = parseLibrary(key)
    if (result.tree.isDefined) {
      Future.successful(Redirect(controllers.routes.ProjectController.update(key)))
    } else {
      throw new IllegalStateException("Cannot parse.")
    }
  }

  def parseLibrary(key: String) = {
    val content = TypeScriptFiles.getContent(key)
    val tree = TypeScriptImport.parse(content)
    val res = tree match {
      case Right(t) => Some(t) -> PrinterService(key, t).export()
      case Left(err) => None -> Seq("Error: " + err)
    }
    ParseController.Result(key, content, res._1, res._2)
  }
}
