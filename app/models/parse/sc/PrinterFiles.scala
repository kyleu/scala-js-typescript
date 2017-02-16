package models.parse.sc

import better.files._

class PrinterFiles(path: String, pkg: String) {
  val root = "data" / "out" / path

  if (root.exists) {
    root.delete()
  }

  root.createDirectory

  val solo = root / s"$pkg.scala"
  solo.createIfNotExists()

  val output = solo.newPrintWriter()

  def print(s: String) = {
    output.write(s)
  }

  def read() = solo.contentAsString
}
