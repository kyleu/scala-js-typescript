package models.parse.sc.printer

import better.files._
import models.parse.sc.transform.ReplacementManager
import models.parse.sc.tree.Name
import services.parse.ClassReferenceService

case class PrinterFilesMulti(key: String, root: File) extends PrinterFiles {
  private[this] val stack = collection.mutable.Stack[(Name, File)]()
  private[this] var activeDir: Option[File] = Some(root)
  private[this] var activeObject: Option[Name] = None
  private[this] var activeFile: Option[File] = None

  if (root.exists) {
    root.delete()
  }
  root.createDirectory

  val rootObj = root / "package.scala"

  override def pushPackage(pkg: Name) = {
    val dir = activeDir match {
      case Some(active) => active / pkg.name
      case None => root / pkg.name
    }
    dir.createIfNotExists(asDirectory = true)

    activeDir = Some(dir)
    stack.push(pkg -> dir)

    //log("PUSH  ::: " + stack.reverse.map(_._1.name).mkString(".") + "\n")
  }

  override def popPackage(pkg: Name) = {
    val popped = stack.pop()
    if (pkg != popped._1) {
      throw new IllegalStateException(s"Observed [$popped], not expected [$pkg].")
    }
    activeDir = activeDir.map(_.parent)
    //log("POP   ::: " + stack.reverse.map(_._1.name).mkString(".") + "\n")
  }

  override def setActiveObject(n: Name) = activeObject match {
    case Some(active) => // throw new IllegalStateException(s"Attempt to set active object to [${n.name}] before clearing [$active].")
    case None =>
      activeObject = Some(n)
      val file = activeDir match {
        case Some(active) => active / s"${n.name.replaceAllLiterally(".", "").replaceAllLiterally("-", "")}.scala"
        case None => throw new IllegalStateException("No active directory.")
      }
      if (!file.exists) {
        val pkg = stack.map(_._1.name).mkString(".")
        if (pkg.isEmpty) {
          file.append(s"package org.scalajs.${root.name}\n")
        } else {
          file.append(s"package org.scalajs.${root.name}.$pkg\n")
        }

        file.append("\n")
        file.append("import scala.scalajs.js\n")
      }
      activeFile = Some(file)
    //log(s"SET   ::: ${n.name} (${stack.reverse.map(_._1.name).mkString(".")})\n")
  }

  override def clearActiveObject(n: Name) = activeObject match {
    case Some(active) => if (n == active) {
      activeFile = None
      activeObject = None
      //log(s"CLEAR ::: ${active.name} (${stack.reverse.map(_._1.name).mkString(".")})\n")
    } else {
      // Noop for now
    }
    case None => //throw new IllegalStateException(s"Attempt to clear active object with none active.")
  }

  def print(s: String) = {
    //log(s)
    activeFile match {
      case Some(file) => file.append(s)
      case None => rootObj.append(s)
    }
  }

  override def onComplete() = {
    val textContent = new StringBuilder
    val replacements = ReplacementManager.getReplacements(key)

    root.listRecursively().toList.map { file =>
      if (!file.isDirectory)
        if (file.size <= 1024 && file.contentAsString.trim.isEmpty) {
          file.delete()
        } else {
          val originalContent = file.lines.toList
          println(originalContent)
          val newContent = replacements.replace(originalContent)
          val ret = ClassReferenceService.insertImports(file.pathAsString, newContent)
          val body = ret.mkString("\n")
          file.delete()
          file.write(body)
          textContent.append(" ::: " + file.pathAsString + "\n")
          textContent.append(body + "\n")
        }
    }
    textContent.toString.split('\n')
  }
}
