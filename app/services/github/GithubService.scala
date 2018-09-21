package services.github

import io.circe.Json
import utils.JsonSerializers._
import play.api.libs.ws.{ WSAuthScheme, WSClient, WSRequest, WSResponse }
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future
import scala.util.control.NonFatal

object GithubService {
  val baseUrl = "https://api.github.com/"
  val accessKey = Option(System.getProperty("GITHUB_KEY")).orElse(Option(System.getenv("GITHUB_KEY"))).getOrElse(
    throw new IllegalStateException("Please add a github key as an environment variable named [GITHUB_KEY]."))

  case class Repo(id: Int, name: String, url: String, description: String, forks: Int, stars: Int, watchers: Int, size: Int) {
    val key = name.stripPrefix("scala-js-")
  }

  def repoFromObj(repo: Map[String, Json]) = {
    val id = repo("id").asNumber.get.toInt.get
    val name = repo("name").asString.get
    val url = repo("html_url").asString.get
    val description = repo.get("description").map(_.asString.get).getOrElse("")
    val forks = repo("forks_count").asNumber.get.toInt.get
    val stars = repo("stargazers_count").asNumber.get.toInt.get
    val watchers = repo("watchers_count").asNumber.get.toInt.get
    val size = repo("size").asNumber.get.toInt.get
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
        case repo: Json => GithubService.repoFromObj(repo.asObject.get.toMap)
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
    trap(r, r.get)(x => GithubService.repoFromObj(getObject(x).toMap)).map(Some(_)).recoverWith {
      case NonFatal(x) => Future.successful(None)
    }
  }

  def create(key: String, description: String) = {
    val json = Json.obj("name" -> key.asJson, "description" -> description.asJson, "has_wiki" -> false.asJson)
    val r = req("orgs/DefinitelyScala/repos")
    trap(r, r.post(json.spaces2)) { x =>
      GithubService.repoFromObj(getObject(x).toMap)
    }
  }

  private[this] def getArray(rsp: WSResponse) = parseJson(rsp.body).right.get.asArray.get
  private[this] def getObject(rsp: WSResponse) = parseJson(rsp.body).right.get.asObject.get

  private[this] def req(page: String) = {
    val url = GithubService.baseUrl + page
    ws.url(url).withAuth("KyleU", GithubService.accessKey, WSAuthScheme.BASIC).withHttpHeaders(
      "User-Agent" -> "scala-js-typescript")
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
