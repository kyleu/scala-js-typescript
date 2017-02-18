package services.parse

import models.parse.parser.TSDefParser
import models.parse.parser.tree.DeclTree

import scala.util.parsing.input.CharSequenceReader

object TypeScriptImport {
  def parse(s: String): Either[String, scala.List[DeclTree]] = {
    val parser = new TSDefParser()
    testParser(parser, s)
  }

  private[this] def testParser(parser: TSDefParser, s: String) = {
    parser.parseDefinitions(new CharSequenceReader(s, 0)) match {
      case parser.Success(rawCode: List[models.parse.parser.tree.DeclTree], _) => Right(rawCode)

      case parser.NoSuccess(msg, next) =>
        val m = s"Parse error at ${next.pos.toString}:" + "\n  " + msg + "\n  Position: " + next.pos.longString
        Left(m)
    }
  }
}
