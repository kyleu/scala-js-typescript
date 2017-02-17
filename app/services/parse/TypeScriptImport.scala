package services.parse

import models.parse.parser.{TSDefLexical, TSDefParser}
import models.parse.parser.tree.DeclTree

import scala.util.parsing.input.CharSequenceReader

object TypeScriptImport {
  def parse(s: String): Either[String, scala.List[DeclTree]] = {
    val parser = new TSDefParser

    //testLexical(parser.lexical, s)

    testParser(parser, s)
  }

  private[this] def testLexical(lexical: TSDefLexical, s: String) = {
    val p = lexical.tokenizeString(s) match {
      case lexical.Success(rawCode, _) => Right(rawCode)
      case lexical.NoSuccess(msg, next) => Left(s"Parse error at ${next.pos.toString}:" + "\n  " + msg + "\n  Position: " + next.pos.longString)
    }

    println("-------------------")
    println(p.getClass.toString)
    println(p.toString)
    println("-------------------")
    p
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
