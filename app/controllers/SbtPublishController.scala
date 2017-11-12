package controllers

import play.api.libs.ws.WSClient
import services.github.GithubService
import services.project.{ProjectDetailsService, ProjectService}
import services.sbt.{SbtResultParser, SbtService}
import utils.Application
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

@javax.inject.Singleton
class SbtPublishController @javax.inject.Inject() (override val app: Application, githubService: GithubService, wsClient: WSClient) extends BaseController {
  private[this] val key = "href=\":scala-js-"

  private[this] def getPublishedJars = {
    val mFuture = wsClient.url("https://dl.bintray.com/definitelyscala/maven/com/definitelyscala/").get
    wsClient.url("https://jcenter.bintray.com/com/definitelyscala/").get.flatMap { jRsp =>
      val j = jRsp.body.split('\n').flatMap { line =>
        line.indexOf(key) match {
          case -1 => None
          case x => Some(line.substring(x + key.length, line.indexOf("_sjs")))
        }
      }
      mFuture.map { mRsp =>
        val m = mRsp.body.split('\n').flatMap { line =>
          line.indexOf(key) match {
            case -1 => None
            case x => Some(line.substring(x + key.length, line.indexOf("_sjs")))
          }
        }
        m -> j
      }
    }
  }

  def list() = act(s"publish.list") { implicit request =>
    getPublishedJars.flatMap { publishedKeys =>
      githubService.listRepos().map { repos =>
        val repoKeys = repos.map(_.key)

        val keys = (publishedKeys._1 ++ publishedKeys._2 ++ repoKeys).distinct.sorted

        Ok(views.html.sbt.published(keys, repoKeys.toSet, publishedKeys._1.toSet, publishedKeys._2.toSet))
      }
    }
  }

  def publish(key: String) = act(s"sbt.publish.$key") { implicit request =>
    val projectDir = ProjectService.projectDir(key)
    if (projectDir.exists) {
      val publishResult = SbtService.publish(projectDir)

      val result = SbtResultParser.Result(key, Nil, Nil, Nil)

      Future.successful(Ok(views.html.sbt.result(key, result, publishResult._2)))
    } else {
      throw new IllegalStateException(s"No project found for [$key].")
    }
  }

  def publishAll(q: Option[String]) = act(s"sbt.publish.all") { implicit request =>
    getPublishedJars.flatMap { publishedKeys =>
      githubService.listRepos().map { repos =>
        val deetSet = ProjectDetailsService.getAll(q = None, filter = Some("built"), repos = repos).filter(_.github).map(_.key).toSet
        val results = repos.map { repo =>
          val f = ProjectService.projectDir(repo.key)
          if (deetSet(repo.key)) {
            val x = SbtService.publish(f)
            (f.name, x._1, x._2)
          } else {
            (f.name, 0, "Skipped")
          }
        }
        Ok(views.html.sbt.results(results))
      }
    }
  }
}
