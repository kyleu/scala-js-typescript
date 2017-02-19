package models.parse.sc.printer

import better.files._
import models.parse.ProjectDefinition
import models.parse.sc.transform.ReplacementManager
import models.parse.sc.tree.Name

case class PrinterFilesSingle(project: ProjectDefinition, file: File) extends PrinterFiles {
  if (file.exists) {
    file.delete()
  }

  file.append(s"package org.scalajs.${project.keyNormalized}\n")

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
    val replacements = ReplacementManager.getReplacements(project.key)
    val originalContent = file.lines.toList
    val newContent = replacements.replace(originalContent)
    if (originalContent != newContent) {
      file.delete()
      file.write(newContent.mkString("\n"))
      newContent
    } else {
      originalContent
    }
  }
}
