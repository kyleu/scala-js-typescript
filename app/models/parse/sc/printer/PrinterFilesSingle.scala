package models.parse.sc.printer

import better.files._
import models.parse.sc.transform.ReplacementManager
import models.parse.sc.tree.Name

case class PrinterFilesSingle(key: String, file: File) extends PrinterFiles {
  private[this] val dir = "data" / "projects" / "_megaproject" / "src" / "main" / "scala" / "org" / "scalajs"

  if (!dir.exists) {
    throw new IllegalStateException(s"Missing output directory [${dir.path}].")
  }

  val keyNormalized = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "")

  file.append(s"package org.scalajs.$keyNormalized\n")

  file.append("\n")
  file.append("import scala.scalajs.js\n")
  file.append("import scala.scalajs.js.|\n")
  file.append("import org.scalajs.dom.raw._\n")

  override def pushPackage(pkg: Name) = {
    val p = pkg.name.replaceAllLiterally("-", "")
    file.append(s"package $p {\n")
  }
  override def popPackage(pkg: Name) = file.append(s"}\n")

  override def print(s: String) = file.append(s)

  override def onComplete() = {
    val replacements = ReplacementManager.getReplacements(key)
    val newContent = replacements.replace(file.lines.toArray[String]).mkString("\n")
    file.delete()
    file.write(newContent)
  }
}
