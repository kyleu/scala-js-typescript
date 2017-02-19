package services.github

import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import upickle.Js
import upickle.default._

object GithubService {
  val baseUrl = "https://api.github.com/"
  val accessKey = "c84dee2da3dbea0ca720a3b6573238319275ffb5"

  case class Repo(id: Int, name: String, url: String, description: String)
}

case class GithubService(ws: WSClient) {
  def test() = detail("scala-js-template").map { result =>
    result.toString
  }

  def listRepos() = getArray(req("orgs/DefinitelyScala/repos")).map { rsp =>
    rsp.map {
      case repo: Js.Obj =>
        val id = repo("id").asInstanceOf[Js.Num].value.toInt
        val name = repo("name").asInstanceOf[Js.Str].value
        val url = repo("html_url").asInstanceOf[Js.Str].value
        val description = repo("description").asInstanceOf[Js.Str].value
        GithubService.Repo(id, name, url, description)
      case _ => throw new IllegalStateException()
    }
  }

  def detail(key: String) = getObject(req(s"repos/DefinitelyScala/$key")).map { repo =>
    val id = repo("id").asInstanceOf[Js.Num].value.toInt
    val name = repo("name").asInstanceOf[Js.Str].value
    val url = repo("html_url").asInstanceOf[Js.Str].value
    val description = repo("description").asInstanceOf[Js.Str].value
    GithubService.Repo(id, name, url, description)
  }

  def create(key: String) = detail(key)

  private[this] def get(req: WSRequest) = req.get()
  private[this] def getArray(req: WSRequest) = get(req).map { rsp => read[Js.Arr](rsp.body).value }
  private[this] def getObject(req: WSRequest) = get(req).map { rsp => read[Js.Obj](rsp.body).value.toMap }

  private[this] def req(page: String) = {
    val url = GithubService.baseUrl + page
    ws.url(url).withAuth("KyleU", GithubService.accessKey, WSAuthScheme.BASIC).withHeaders(
      "User-Agent" -> "scala-js-typescript"
    )
  }
}
