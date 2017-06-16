package models.parse.sc.transform

import scala.io.Source

object ReplacementManager {
  def getReplacements(key: String) = Option(getClass.getClassLoader.getResourceAsStream(s"replacement/$key.txt")) match {
    case Some(stream) =>
      val src = Source.fromInputStream(stream).getLines.toArray
      Replacements(key, Replacements.toRules(key, src))
    case None => Replacements(key, Nil)
  }
}
