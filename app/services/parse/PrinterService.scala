package services.parse

import models.parse.{ Importer, ProjectDefinition }
import models.parse.parser.tree.{ DeclTree, LineCommentDecl }
import models.parse.sc.printer.{ Printer, PrinterFiles, PrinterFilesMulti }
import models.parse.sc.transform.IgnoredPackages
import services.project.ProjectService

case class PrinterService(key: String, t: List[DeclTree]) {
  private[this] val ignoredPackages = IgnoredPackages.forKey(key)

  def export() = {
    val (proj, decls) = extractFrom(key, t)
    // exportSingle(key, proj.keyNormalized, decls)
    exportMulti(proj, decls)
  }

  private[this] def exportMulti(project: ProjectDefinition, decls: List[DeclTree]) = {
    import utils.JsonSerializers._
    val outDir = ProjectService.outDirFor(key)
    val multi = PrinterFilesMulti(key, project.keyNormalized, outDir)
    val ret = printer(multi, decls)

    val outJson = outDir / "project.json"
    outJson.createIfNotExists()
    outJson.append(project.asJson.spaces2)
    ret
  }

  private[this] def extractFrom(key: String, t: List[DeclTree]) = {
    val comments = t.flatMap {
      case c: LineCommentDecl => Some(c.text.trim)
      case _ => None
    }
    val (nameLine, name, version) = comments.find(_.startsWith("Type definitions for")) match {
      case Some(l) =>
        val str = l.substring("Type definitions for".length + 1).trim.split(' ').map(_.trim)
        val version = str.last
        if (version.exists(_.isDigit)) {
          (l, str.dropRight(1).mkString(" ").trim, str.last)
        } else {
          (l, str.mkString(" ").trim, "")
        }
      case None => ("UNKNOWN", key, "0.1")
    }
    val cleanName = if (name.isEmpty) { key } else { name }
    val (urlLine, url) = comments.find(_.startsWith("Project:")) match {
      case Some(l) => l -> l.substring("Project:".length + 1).trim
      case None => "" -> key
    }
    val (authorsLine, authors) = comments.find(_.startsWith("Definitions by:")) match {
      case Some(l) => l -> l.substring("Definitions by:".length).trim
      case _ => "" -> key
    }
    val buildVersion = "1.1.0"
    val defsLine = comments.find(_.startsWith("Definitions:")) match {
      case Some(l) => l
      case None => ""
    }
    val dependencies = comments.filter(_.contains("<reference types")).map { dep =>
      dep.split('\"').toList match {
        case _ :: x :: _ :: Nil => ProjectDefinition.normalize(x)
        case _ => dep.split('\'').toList match {
          case _ :: x :: _ :: Nil => ProjectDefinition.normalize(x)
          case _ => throw new IllegalStateException(s"Invalid reference [$dep].")
        }
      }
    }

    val p = ProjectDefinition(key, cleanName, url, version, authors, buildVersion, dependencies)
    val trimmedLines = Seq(nameLine, urlLine, authorsLine, defsLine)

    val remaining = t.filter {
      case c: LineCommentDecl => !trimmedLines.contains(c.text.trim)
      case _ => true
    }

    p -> remaining
  }

  private[this] def printer(files: PrinterFiles, decls: List[DeclTree]) = {
    val pkg = Importer(key)(decls)
    new Printer(files, key, ignoredPackages).printSymbol(pkg, fancy = false)
    files.onComplete()
  }
}
