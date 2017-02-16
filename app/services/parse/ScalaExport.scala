package services.parse

import models.parse.{Comment, ScriptToken}

object ScalaExport {
  def print(x: Seq[ScriptToken]) = x.map(_.toScala).mkString
}
