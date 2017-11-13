package services.github

import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest, WSResponse}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import upickle.Js
import upickle.default._

import scala.concurrent.Future
import scala.util.control.NonFatal

object GithubService {
  val baseUrl = "https://api.github.com/"
  val accessKey = Option(System.getProperty("GITHUB_KEY")).getOrElse("INSERT_YOUR_KEY_HERE")

  case class Repo(id: Int, name: String, url: String, description: String, forks: Int, stars: Int, watchers: Int, size: Int) {
    val key = name.stripPrefix("scala-js-")
  }

  def repoFromObj(repo: Map[String, Js.Value]) = {
    val id = repo("id").asInstanceOf[Js.Num].value.toInt
    val name = repo("name").asInstanceOf[Js.Str].value
    val url = repo("html_url").asInstanceOf[Js.Str].value
    val description = repo.get("description").map(_.toString).getOrElse("")
    val forks = repo("forks_count").asInstanceOf[Js.Num].value.toInt
    val stars = repo("stargazers_count").asInstanceOf[Js.Num].value.toInt
    val watchers = repo("watchers_count").asInstanceOf[Js.Num].value.toInt
    val size = repo("size").asInstanceOf[Js.Num].value.toInt
    GithubService.Repo(id, name, url, description, forks, stars, watchers, size)
  }
}

@javax.inject.Singleton
case class GithubService @javax.inject.Inject() (ws: WSClient) {
  private[this] val pageSize = 100

  def test() = detail("scala-js-template").map { result =>
    result.toString
  }

  def listRepos(includeTemplates: Boolean = false) = {
    fullList().map { repos =>
      val filtered = if (includeTemplates) {
        repos
      } else {
        repos.filterNot(r => r.name == "scala-js-template" || r.name == "definitelyscala.com")
      }
      filtered.sortBy(_.name)
    }
  }

  private[this] def fullList(prior: Seq[GithubService.Repo] = Nil, page: Int = 1): Future[Seq[GithubService.Repo]] = {
    val r = req(s"orgs/DefinitelyScala/repos?page=$page&per_page=$pageSize")
    val ret = trap(r, r.get()) { rsp =>
      getArray(rsp).map {
        case repo: Js.Obj => GithubService.repoFromObj(repo.value.toMap)
        case _ => throw new IllegalStateException()
      }.sortBy(_.name).reverse
    }
    ret.flatMap {
      case repos if repos.size < pageSize => Future.successful(prior ++ repos)
      case repos => fullList(prior ++ repos, page + 1)
    }
  }

  def detail(key: String) = {
    val withPrefix = if (key.startsWith("scala-js-")) { key } else { "scala-js-" + key }
    val r = req(s"repos/DefinitelyScala/$withPrefix")
    trap(r, r.get)(x => GithubService.repoFromObj(getObject(x))).map(Some(_)).recoverWith {
      case NonFatal(x) => Future.successful(None)
    }
  }

  def create(key: String, description: String) = {
    val json = Js.Obj("name" -> Js.Str(s"$key"), "description" -> Js.Str(description), "has_wiki" -> Js.False)
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
