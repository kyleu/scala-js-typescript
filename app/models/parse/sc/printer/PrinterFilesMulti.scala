package models.parse.sc.printer

import better.files._
import models.parse.sc.transform.ReplacementManager
import models.parse.sc.tree.Name
import services.parse.ClassReferenceService
import utils.Logging

case class PrinterFilesMulti(key: String, keyNormalized: String, root: File) extends PrinterFiles with Logging {
  log.info(s"Parsing multiple files for [$key]...")

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
  }

  override def popPackage(pkg: Name) = {
    val popped = stack.pop()
    if (pkg != popped._1) {
      throw new IllegalStateException(s"Observed [$popped], not expected [$pkg].")
    }
    activeDir = activeDir.map(_.parent)
  }

  override def getActiveObject = activeObject

  override def setActiveObject(n: Name) = {
    activeObject.foreach(current => if (current.name != n.name) {
      throw new IllegalStateException(s"Attempt to set active object [$n] when [$current] is already set.")
    })
    activeObject = Some(n)
    val file = activeDir match {
      case Some(active) => active / s"${n.name.replaceAllLiterally(".", "").replaceAllLiterally("-", "")}.scala"
      case None => throw new IllegalStateException("No active directory.")
    }
    if (!file.exists) {
      val pkg = stack.map(_._1.name).reverse.mkString(".")
      file.createIfNotExists(createParents = true)
      if (pkg.isEmpty) {
        file.append(s"package com.definitelyscala.$keyNormalized\n")
      } else {
        file.append(s"package com.definitelyscala.$keyNormalized.$pkg\n")
      }

      file.append("\n")
      file.append("import scala.scalajs.js\n")
    }
    activeFile = Some(file)
  }

  override def clearActiveObject(n: Name) = activeObject match {
    case Some(active) => if (n == active) {
      activeFile = None
      activeObject = None
    } else {
      // Noop for now
    }
    case None => //throw new IllegalStateException(s"Attempt to clear active object with none active.")
  }

  def packageObject() = {
    activeDir.map(_ / "package.scala").getOrElse(rootObj)
  }

  def print(s: String) = {
    //log(s)
    activeFile match {
      case Some(file) => file.append(s)
      case None => packageObject().append(s)
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
          val originalContent = if (file.name == "package.scala") {
            Seq(s"package com.definitelyscala.$keyNormalized\n") ++ file.lines.toList
          } else {
            file.lines.toList
          }
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
