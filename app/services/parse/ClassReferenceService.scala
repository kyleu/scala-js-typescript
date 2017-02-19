package services.parse

import utils.Logging

object ClassReferenceService extends Logging {
  private[this] val refs = Seq(
    "org.scalajs.dom.raw._" -> Seq(" HTMLElement", " Promise", " Event"),
    "scala.scalajs.js.|" -> Seq(" | "),
    "scala.scalajs.js.Date" -> Seq(": Date", " Date "),
    "org.scalajs.dom.raw.Blob" -> Seq(" Blob "),
    "org.scalajs.css.Css.AtRule" -> Seq(" AtRule"),
    "scala.scalajs.js.typedarray._" -> Seq(" ArrayBuffer")
  )

  def importsFor(content: Seq[String]) = refs.flatMap { ref =>
    if (ref._2.exists(key => content.exists(_.contains(key)))) {
      println(s"Matched [$ref].")
      Some(ref._1)
    } else {
      None
    }
  }

  def insertImports(content: Seq[String]) = {
    val importIdx = content.indexOf("import scala.scalajs.js")
    if (importIdx < 0) {
      log.warn(s"Missing import statement for content: [$content]")
      content
    } else {
      val imports = importsFor(content).map("import " + _)
      content.take(importIdx + 1) ++ imports ++ content.drop(importIdx + 1)
    }
  }
}
