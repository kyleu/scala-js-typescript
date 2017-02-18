package models.parse.sc.transform

import scala.io.Source

object ReplacementManager {
  //private[this] val repls = collection.mutable.HashMap.empty[String, Replacements]

  def getReplacements(key: String) = /*repls.getOrElseUpdate(key,*/ {
    Option(getClass.getClassLoader.getResourceAsStream(s"replacement/$key.txt")) match {
      case Some(stream) =>
        val src = Source.fromInputStream(stream).getLines.toArray
        Replacements(key, Replacements.toRules(src))
      case None => Replacements(key, Nil)
    }
  } /*)*/
}
