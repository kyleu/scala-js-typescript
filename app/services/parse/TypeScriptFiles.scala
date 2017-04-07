package services.parse

import better.files._

object TypeScriptFiles {
  private[this] val root = "typescript" / "DefinitelyTyped" / "types"
  private[this] val overrideRoot = "typescript" / "DefinitelyScala" / "types"

  def list(q: Option[String]) = {
    val core = root.list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name)
    val over = overrideRoot.list.filter(_.isDirectory).filter(_.name.contains(q.getOrElse(""))).toSeq.map(_.name)
    (core.toSet ++ over).toSeq.sorted
  }

  def getContent(key: String) = {
    val overrideFile = overrideRoot / key / "index.d.ts"
    val f = if (overrideFile.exists) {
      overrideFile
    } else {
      root / key / "index.d.ts"
    }
    f.contentAsString
  }
}
