package models.parse.sc.transform

import models.parse.sc.tree.{ Name, QualifiedName }

object Transformations {
  def forName(n: QualifiedName) = {
    val last = n.parts.last
    types.get(last.name) match {
      case Some(tx) => QualifiedName(n.parts.dropRight(1) :+ Name(tx): _*)
      case None => n
    }
  }

  def types = Map(
    "XMLDocument" -> "Document",
    "RegExp" -> "js.RegExp",
    "PointerEvent" -> "Event",
    "WebGLContextEvent" -> "Event")
}
