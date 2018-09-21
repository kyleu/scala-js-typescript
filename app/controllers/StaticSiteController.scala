package controllers

import better.files._
import models.parse.ProjectDefinition
import scala.concurrent.ExecutionContext.Implicits.global
import play.twirl.api.Html
import services.file.FileService
import services.github.GithubService
import utils.Application

@javax.inject.Singleton
class StaticSiteController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  private[this] def htmlFor(projName: String, definition: ProjectDefinition) = {
    s"""<div class="collection-item">
            <div>
              <div class="right">${definition.version}</div>
              ${definition.name}
            </div>
            <a class="blue-grey-text" href="https://github.com/DefinitelyScala/$projName">https://github.com/DefinitelyScala/$projName</a>
          </div>"""
  }

  def generate() = act("static.site") { implicit request =>
    val root = "../definitelyscala.com".toFile

    if (!root.exists) {
      throw new IllegalStateException(s"Missing static site root [${root.path}].")
    }

    githubService.listRepos(includeTemplates = false).map { repos =>
      val src = root / "index.template.html"
      val dest = root / "index.html"

      val outDirs = FileService.getDir("out").list.filter(_.isDirectory).toSeq.map(_.name).sorted

      val items = outDirs.flatMap { o =>
        repos.find(_.name.stripPrefix("scala-js-") == ProjectDefinition.normalize(o)).map(o -> _)
      }

      val definitions = items.map { i =>
        val projName = "scala-js-" + ProjectDefinition.normalize(i._1)
        val outDir = FileService.getDir("out") / i._1
        (projName, i._2.stars, ProjectDefinition.fromJson(outDir))
      }

      val favorites = definitions.filter(_._2 > 0).sortBy(x => (-x._2) -> x._3.keyNormalized).map(d => htmlFor(d._1, d._3)).mkString("\n          ")

      val allString = definitions.map(d => htmlFor(d._1, d._3)).mkString("\n          ")

      val originalContent = src.contentAsString
      val favoritesContent = originalContent.replace("[favorites]", favorites)
      val allContent = favoritesContent.replace("[items]", allString)

      dest.delete(swallowIOExceptions = true)
      dest.write(allContent)

      Ok(Html(s"Site exported (${items.size} repositories)."))
    }
  }
}
