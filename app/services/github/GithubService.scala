package services.github

import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest, WSResponse}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import upickle.Js
import upickle.default._

import scala.concurrent.Future
import scala.util.control.NonFatal

object GithubService {
  val baseUrl = "https://api.github.com/"
  val accessKey = "c84dee2da3dbea0ca720a3b6573238319275ffb5"

  case class Repo(id: Int, name: String, url: String, description: String)

  def repoFromObj(repo: Map[String, Js.Value]) = {
    val id = repo("id").asInstanceOf[Js.Num].value.toInt
    val name = repo("name").asInstanceOf[Js.Str].value
    val url = repo("html_url").asInstanceOf[Js.Str].value
    val description = repo.get("description").map(_.toString).getOrElse("")
    GithubService.Repo(id, name, url, description)
  }
}

@javax.inject.Singleton
case class GithubService @javax.inject.Inject() (ws: WSClient) {
  def test() = detail("_scala-js-template").map { result =>
    result.toString
  }

  def listRepos() = {
    val r = req("orgs/DefinitelyScala/repos")
    trap(r, r.get()) { rsp =>
      getArray(rsp).map {
        case repo: Js.Obj => GithubService.repoFromObj(repo.value.toMap)
        case _ => throw new IllegalStateException()
      }
    }
  }

  def detail(key: String) = {
    val r = req(s"repos/DefinitelyScala/$key")
    trap(r, r.get)(x => GithubService.repoFromObj(getObject(x)))
  }

  def create(key: String) = {
    val json = Js.Obj("name" -> Js.Str(s"$key"), "has_wiki" -> Js.False)
    val r = req("orgs/DefinitelyScala/repos")
    trap(r, r.post(write(json))) { x =>
      GithubService.repoFromObj(getObject(x))
    }
  }

  private[this] def getArray(rsp: WSResponse) = read[Js.Arr](rsp.body).value
  private[this] def getObject(rsp: WSResponse) = read[Js.Obj](rsp.body).value.toMap

  private[this] def req(page: String) = {
    val url = GithubService.baseUrl + page
    ws.url(url).withAuth("KyleU", GithubService.accessKey, WSAuthScheme.BASIC).withHeaders(
      "User-Agent" -> "scala-js-typescript"
    )
  }

  private[this] def trap[T](req: WSRequest, rsp: Future[WSResponse])(f: WSResponse => T) = rsp.map { response =>
    try {
      if (response.status >= 200 && response.status < 300) {
        f(response)
      } else {
        throw new IllegalStateException(s"Received [${response.status}] response from [${req.method}] request to [${req.url}].")
      }
    } catch {
      case NonFatal(x) =>
        val msg = s"Error encountered for [${req.url}]:\n${x.getClass.getSimpleName}: ${x.getMessage}\nResponse: ${response.body}"
        throw new IllegalStateException(msg)
    }
  }
}
