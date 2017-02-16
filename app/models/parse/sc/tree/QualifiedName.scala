package models.parse.sc.tree

import scala.language.implicitConversions

object QualifiedName {
  implicit def fromName(name: Name): QualifiedName = QualifiedName(name)

  val Root = QualifiedName()
  val scala = Root dot Name.scala
  val scala_js = scala dot Name.scalajs dot Name.js
  val java_lang = Root dot Name.java dot Name.lang

  val Array = scala_js dot Name("Array")
  val Dictionary = scala_js dot Name("Dictionary")
  val FunctionBase = scala_js dot Name("Function")
  def Function(arity: Int) = scala_js dot Name("Function" + arity)
  def Tuple(arity: Int) = scala_js dot Name("Tuple" + arity)
  val Union = scala_js dot Name("|")
}

case class QualifiedName(parts: Name*) {
  def isRoot = parts.isEmpty

  override def toString = if (isRoot) {
    "_root_"
  } else {
    parts.mkString(".")
  }

  def dot(name: Name) = QualifiedName(parts :+ name: _*)
  def init = QualifiedName(parts.init: _*)
  def last = parts.last
}
