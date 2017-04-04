package controllers

import models.parse.ProjectDefinition
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.twirl.api.Html
import services.file.FileService
import services.git.GitService
import services.github.GithubService
import services.project.ProjectService
import utils.Application

@javax.inject.Singleton
class GithubController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list = act(s"github.list") { implicit request =>
    githubService.listRepos(includeTemplates = false).map { repos =>
      Ok(views.html.github.list(repos))
    }
  }

  def mergeAll = act(s"github.merge") { implicit request =>
    githubService.listRepos(includeTemplates = false).map { repos =>
      val result = repos.map { repo =>
        val dir = ProjectService.projectDir(repo.name.stripPrefix("scala-js-"))
        if (!dir.exists) {
          dir.createDirectory()
        }
        val gitDir = dir / ".git"
        val ret = gitDir.exists
        if (!ret) {
          val parent = dir.parent
          dir.delete(swallowIOExceptions = true)
          val result = GitService.cloneRepo(parent, repo.name)
        }
        ret
      }
      Ok(Html(s"Ok: ${result.filterNot(x => x).size} github repos merged, ${result.count(x => x)} already present."))
    }
  }

  def detail(key: String) = act(s"github.$key") { implicit request =>
    githubService.detail(key).map {
      case Some(repo) => Ok(views.html.github.detail(repo))
      case None => Ok(Html(s"Github repo for [$key] not found."))
    }
  }

  def create(key: String) = act(s"github.create.$key") { implicit request =>
    val proj = ProjectDefinition.fromJson(ProjectService.outDirFor(key))
    githubService.create("scala-js-" + proj.keyNormalized, proj.description).map { result =>
      Redirect(controllers.routes.GithubController.detail(proj.keyNormalized))
    }
  }
}
