package services.parse

import better.files._
import services.file.FileService

object TypeScriptFiles {
  val root = {
    val gitRepo = "typescript" / "DefinitelyTyped" / "types"
    if (gitRepo.exists) {
      gitRepo
    } else {
      FileService.getDir("DefinitelyTyped")
    }
  }

  val overrideRoot = "typescript" / "DefinitelyScala"

  def list(q: Option[String]) = {
    root.list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name)
  }

  def getContent(key: String) = {
    val overrideFile = overrideRoot / s"$key.ts"
    val f = if (overrideFile.exists) {
      overrideFile
    } else {
      root / key / "index.d.ts"
    }
    f.contentAsString
  }
}
