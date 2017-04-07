package services.project

import models.parse.ProjectDefinition
import services.file.FileService
import services.github.GithubService
import services.parse.TypeScriptFiles
import services.sbt.SbtHistoryService

object ProjectDetailsService {
  case class Details(key: String, keyNormal: String, parsed: Boolean, project: Boolean, built: Boolean, repo: Boolean, github: Boolean)

  def getAll(q: Option[String], filter: Option[String], repos: Seq[GithubService.Repo]) = {
    val srcDirs = TypeScriptFiles.list(q)
    val outDirs = FileService.getDir("out").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name)
    val projectDirs = FileService.getDir("projects").list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse("scala-js-" + ""))).toSeq
    val buildStatus = SbtHistoryService.statuses().toMap

    val keys = srcDirs.sorted.map(x => x -> ProjectDefinition.normalize(x))

    val filteredKeys = filter match {
      case None => keys
      case Some("all") => keys
      case Some("parsed") => keys.filter(k => outDirs.contains(k._1))
      case Some("built") => keys.filter(k => projectDirs.exists(d => d.name == ("scala-js-" + k._2)))
      case Some("published") => keys.filter(k => repos.exists(_.name == ("scala-js-" + k._2)))
      case Some(x) => throw new IllegalStateException(s"Invalid filter [$x].")
    }

    filteredKeys.map { key =>
      val parsed = outDirs.contains(key._1)
      val projectDir = projectDirs.find(_.name == "scala-js-" + key._2)
      val project = projectDir.isDefined
      val built = buildStatus.getOrElse(key._2, false)
      val repo = projectDir.exists(x => (x / ".git").exists)
      val github = repos.exists(_.name == ("scala-js-" + models.parse.ProjectDefinition.normalize(key._2)))
      Details(key._1, key._2, parsed, project, built, repo, github)
    }
  }
}
