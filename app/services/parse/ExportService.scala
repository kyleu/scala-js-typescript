package services.parse

import better.files._
import models.parse.parser.tree.{DeclTree, LineCommentDecl}
import models.parse.sc.printer.{Printer, PrinterFiles, PrinterFilesMulti, PrinterFilesSingle}
import models.parse.sc.transform.IgnoredPackages
import models.parse.{Importer, ProjectDefinition}

case class ExportService(key: String, t: List[DeclTree]) {
  private[this] val ignoredPackages = IgnoredPackages.forKey(key)

  def export() = {
    val (project, decls) = extractFrom(key, t)
    exportSingle(project, decls)
    exportMulti(project, decls)
  }

  private[this] def exportSingle(project: ProjectDefinition, decls: List[DeclTree]) = {
    val dir = "util" / "megasingle" / "src" / "main" / "scala" / "org" / "scalajs"
    if (!dir.exists) {
      throw new IllegalStateException(s"Missing output directory [${dir.path}].")
    }
    val srcFile = dir / project.keyNormalized / (project.keyNormalized + ".scala")
    srcFile.createIfNotExists(createParents = true)
    val single = PrinterFilesSingle(project, srcFile)
    printer(single, decls)
  }

  private[this] def exportMulti(project: ProjectDefinition, decls: List[DeclTree]) = {
    val proj = ProjectService(project)
    val srcDir = proj.create()
    val multi = PrinterFilesMulti(project, srcDir)
    printer(multi, decls)
  }

  private[this] def extractFrom(key: String, t: List[DeclTree]) = {
    val comments = t.flatMap {
      case c: LineCommentDecl => Some(c.text.trim)
      case _ => None
    }
    val (nameLine, name, version) = comments.find(_.startsWith("Type definitions for")) match {
      case Some(l) =>
        val str = l.substring("Type definitions for".length + 1).trim.split(' ')
        (l, str.dropRight(1).mkString(" "), str.last)
      case None => (-1, key, "0.1")
    }
    val (urlLine, url) = comments.find(_.startsWith("Project:")) match {
      case Some(l) => l -> l.substring("Project:".length + 1).trim
      case None => "" -> key
    }
    val (authorsLine, authors) = comments.find(_.startsWith("Definitions by:")) match {
      case Some(l) => l -> l.substring("Definitions by:".length + 1).trim
      case None => "" -> key
    }
    val defsLine = comments.find(_.startsWith("Definitions:")) match {
      case Some(l) => l
      case None => ""
    }

    val p = ProjectDefinition(key, name, url, version, authors)
    val trimmedLines = Seq(nameLine, urlLine, authorsLine, defsLine)

    val remaining = t.filter {
      case c: LineCommentDecl => !trimmedLines.contains(c.text.trim)
      case _ => true
    }

    p -> remaining
  }

  private[this] def printer(files: PrinterFiles, decls: List[DeclTree]) = {
    val pkg = new Importer(key).apply(decls)
    new Printer(files, key, ignoredPackages).printSymbol(pkg)
    files.onComplete()
  }
}
