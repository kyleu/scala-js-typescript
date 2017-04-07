package controllers

import better.files._
import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.twirl.api.Html
import services.file.FileService
import services.github.GithubService
import services.project.ProjectService
import utils.Application

@javax.inject.Singleton
class StaticSiteController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def generate() = act("static.site") { implicit request =>
    val root = "../definitelyscala.com".toFile

    if (!root.exists) {
      throw new IllegalStateException(s"Missing static site root [${root.path}].")
    }

    githubService.listRepos(includeTemplates = false).map { repos =>
      val src = root / "index.template.html"
      val dest = root / "index.html"

      val outDirs = FileService.getDir("out").list.filter(_.isDirectory).toSeq.map(_.name)

      val items = outDirs.filter { o =>
        repos.exists(_.name.stripPrefix("scala-js-") == ProjectDefinition.normalize(o))
      }

      val itemsString = items.map { i =>
        val projName = "scala-js-" + ProjectDefinition.normalize(i)
        val outDir = FileService.getDir("out") / i
        val definition = ProjectDefinition.fromJson(outDir)

        s"""<div class="collection-item">
            <div>
              <div class="right">${definition.version}</div>
              ${definition.name}
            </div>
            <a class="blue-grey-text" href="https://github.com/DefinitelyScala/$projName">https://github.com/DefinitelyScala/$projName</a>
          </div>"""
      }.mkString("\n          ")

      val originalContent = src.contentAsString
      val newContent = originalContent.replace("[items]", itemsString)

      dest.delete(swallowIOExceptions = true)
      dest.write(newContent)

      Ok(Html(s"Site exported (${items.size} repositories)."))
    }
  }
}
