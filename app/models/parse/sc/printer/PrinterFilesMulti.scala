package models.parse.sc.printer

import better.files._
import models.parse.sc.tree.Name

case class PrinterFilesMulti(root: File) extends PrinterFiles {
  private[this] val stack = collection.mutable.Stack[(Name, File)]()
  private[this] var activeDir: Option[File] = Some(root)
  private[this] var activeObject: Option[Name] = None
  private[this] var activeFile: Option[File] = None

  val textContent = new StringBuilder

  private[this] def log(s: String) = {
    textContent.append(s)
  }

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

    log("PUSH  ::: " + stack.map(_._1.name).mkString(".") + "\n")
  }

  override def popPackage(pkg: Name) = {
    val popped = stack.pop()
    if (pkg != popped._1) {
      throw new IllegalStateException(s"Observed [$popped], not expected [$pkg].")
    }
    activeDir = activeDir.map(_.parent)
    log("POP   ::: " + stack.map(_._1.name).mkString(".") + "\n")
  }

  override def setActiveObject(n: Name) = activeObject match {
    case Some(active) => // throw new IllegalStateException(s"Attempt to set active object to [${n.name}] before clearing [$active].")
    case None =>
      activeObject = Some(n)
      val file = activeDir match {
        case Some(active) => active / s"${n.name}.scala"
        case None => throw new IllegalStateException("No active directory.")
      }
      if (!file.exists) {
        val pkg = stack.map(_._1.name).mkString(".")
        file.append(s"package org.scalajs.$pkg\n")

        file.append("\n")
        file.append("import org.scalajs.dom.raw._\n")
        file.append("import scala.scalajs.js\n")
        file.append("import scala.scalajs.js.|\n")
      }
      activeFile = Some(file)
      log(s"SET   ::: ${n.name}\n")
  }

  override def clearActiveObject(n: Name) = activeObject match {
    case Some(active) => if (n == active) {
      activeFile = None
      activeObject = None
      log(s"CLEAR ::: ${active.name}\n")
    } else {
      // Noop for now
    }
    case None => //throw new IllegalStateException(s"Attempt to clear active object with none active.")
  }

  def print(s: String) = {
    log(s)
    activeFile match {
      case Some(file) => file.append(s)
      case None => rootObj.append(s)
    }
  }

  override def onComplete() = {
    root.children.toList.map { f =>
      if ((!f.isDirectory) && f.size <= 1024 && f.contentAsString.trim.isEmpty) {
        f.delete()
      }
    }
  }
}
