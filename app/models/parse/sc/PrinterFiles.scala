package models.parse.sc

import better.files._
import models.parse.sc.tree.Name

class PrinterFiles(path: String) {
  private[this] val root = "data" / "out" / path
  if (root.exists) {
    root.delete()
  }
  root.createDirectory

  val rootObj = root / s"$path.scala"
  rootObj.append(s"package org.scalajs\n")

  rootObj.append("\n")
  rootObj.append("import org.scalajs.dom.raw._\n")
  rootObj.append("import scala.scalajs.js\n")
  rootObj.append("import scala.scalajs.js.|\n")

  def pushPackage(pkg: Name) = {
    rootObj.append(s"package ${pkg.name} {\n")
    //println(s"Pushed [${pkg.name}] to package history.")
  }

  def popPackage(pkg: Name) = {
    rootObj.append(s"}\n")
    //println(s"Popped [${pkg.name}] from package history.")
  }

  def setActiveObject(n: Name) = {
    //println(s"Set active object to [${n.name}].")
  }

  def clearActiveObject(n: Name) = {
  }

  def print(s: String) = rootObj.append(s)
}
