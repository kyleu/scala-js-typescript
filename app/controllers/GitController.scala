package controllers

import play.twirl.api.Html
import services.git.GitService
import services.github.GithubService
import services.project.ProjectService
import utils.Application
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future
import scala.util.control.NonFatal

@javax.inject.Singleton
class GitController @javax.inject.Inject() (override val app: Application, githubService: GithubService) extends BaseController {
  def detail(key: String) = act(s"git.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val dir = projectDir / ".git"
    if (dir.exists) {
      Future.successful(Ok(views.html.git.detail(key)))
    } else {
      Future.successful(Ok(Html(s"Git repo for [$key] not found.")))
    }
  }

  def status(key: String) = act(s"git.status.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    val result = GitService.status(projectDir)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def statusAll() = act("git.status.all") { implicit request =>
    val results = githubService.listRepos(includeTemplates = false).map { repos =>
      repos.map { repo =>
        val key = repo.name.stripPrefix("scala").stripPrefix("-").stripPrefix("js")
        val result = try {
          GitService.status(ProjectService.projectDir(key))
        } catch {
          case NonFatal(x) => (-1, x.toString)
        }
        val out = result._2.split('\n').filterNot(_.contains("../../../")).mkString("\n")
        (repo.name, result._1, out)
      }
    }
    results.map(r => Ok(views.html.git.results(r)))
  }

  def commitForm() = act(s"project.commit.form") { implicit request =>
    Future.successful(Ok(views.html.git.form()))
  }

  def commit(key: String) = act(s"git.commit.$key") { implicit request =>
    val msg = request.body.asFormUrlEncoded.get("msg").mkString
    val result = GitService.commit(ProjectService.projectDir(key), Nil, msg)
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def commitAll() = act("git.commit.all") { implicit request =>
    val body = request.body.asFormUrlEncoded.get
    val msg = body("msg").mkString
    val files = body.getOrElse("files", Nil)
    val results = githubService.listRepos(includeTemplates = false).map { repos =>
      repos.map { repo =>
        val key = repo.name.stripPrefix("scala-js-")
        val result = GitService.commit(ProjectService.projectDir(key), files, msg)
        (repo.name, result._1, result._2)
      }
    }
    results.map(r => Ok(views.html.git.results(r)))
  }

  def push(key: String) = act(s"git.update.$key") { implicit request =>
    val result = GitService.push(ProjectService.projectDir(key))
    Future.successful(Ok(views.html.git.result(key, result._1, result._2)))
  }

  def pushAll() = act("git.push.all") { implicit request =>
    val results = githubService.listRepos(includeTemplates = false).map { repos =>
      repos.map { repo =>
        val key = repo.name.stripPrefix("scala").stripPrefix("-").stripPrefix("js")
        val result = GitService.push(ProjectService.projectDir(key))
        (repo.name, result._1, result._2)
      }
    }
    results.map(r => Ok(views.html.git.results(r)))
  }
}
