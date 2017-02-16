package services.parse

import models.parse.parser.TSDefParser

import scala.util.parsing.input.CharSequenceReader

object TypeScriptImport {

  def parse(s: String) = {
    val parser = new TSDefParser
    parser.parseDefinitions(new CharSequenceReader(s, 0)) match {
      case parser.Success(rawCode: List[models.parse.parser.tree.DeclTree], _) => rawCode

      case parser.NoSuccess(msg, next) =>
        val m = s"Parse error at ${next.pos.toString}:" + "\n  " + msg + "\n  Position: " + next.pos.longString
        throw new IllegalStateException(m)
    }

  }
}
