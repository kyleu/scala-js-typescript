package controllers

import models.parse.ProjectDefinition
import scala.concurrent.ExecutionContext.Implicits.global
import play.twirl.api.Html
import services.git.GitService
import services.github.GithubService
import services.project.{ ProjectDetailsService, ProjectService }
import utils.Application

import scala.concurrent.Future

@javax.inject.Singleton
class GithubController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def list = act(s"github.list") { implicit request =>
    githubService.listRepos().map { repos =>
      Ok(views.html.github.list(repos))
    }
  }

  def mergeAll = act(s"github.merge") { implicit request =>
    githubService.listRepos().map { repos =>
      val result = repos.map { repo =>
        val dir = ProjectService.projectDir(repo.name.stripPrefix("scala-js-"))
        if (!dir.exists) {
          dir.createDirectory()
        }
        val gitDir = dir / ".git"
        val ret = gitDir.exists
        if (ret) {
          log.info(s"Skipping existing github repository for [${repo.name}].")
        } else {
          log.info(s"Merging github repository for [${repo.name}].")
          val parent = dir.parent
          dir.delete(swallowIOExceptions = true)
          GitService.cloneRepo(parent, repo.name)
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

  def createAll(q: Option[String]) = act(s"github.create.all") { implicit request =>
    githubService.listRepos().flatMap { repos =>
      val projects = ProjectDetailsService.getAll(q, Some("built"), repos)
      val results = projects.flatMap { project =>
        val projectDir = ProjectService.projectDir(project.key)
        //val commitCount = GitService.commitCount(projectDir)
        if (project.repo && !project.github) {
          val proj = ProjectDefinition.fromJson(ProjectService.outDirFor(project.key))
          Some(proj)
        } else {
          None
        }
      }
      val f = results.foldLeft(Future.successful(Seq.empty[ProjectDefinition])) { (ret, p) =>
        ret.flatMap { r =>
          githubService.create("scala-js-" + p.keyNormalized, p.description).map { result =>
            val projectDir = ProjectService.projectDir(p.key)
            GitService.addRemote(projectDir)
            GitService.push(projectDir)
            log.info(p.key + "!!!!!!!!!")
            r :+ p
          }
        }
      }
      f.map(results => Ok(s"Ok (${results.size}):\n\n" + results.mkString("\n")))
    }
  }
}
