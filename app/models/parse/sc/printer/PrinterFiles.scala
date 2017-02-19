package models.parse.sc.printer

import models.parse.sc.tree.Name

trait PrinterFiles {
  def pushPackage(pkg: Name): Unit
  def popPackage(pkg: Name): Unit
  def setActiveObject(n: Name): Unit = ()
  def clearActiveObject(n: Name): Unit = ()

  def print(s: String): Unit

  def onComplete(): Seq[String]
}
