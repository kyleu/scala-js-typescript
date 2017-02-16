package services.parse

import fastparse.all._
import fastparse.core.Parsed
import models.parse.{Comment, Module, ScriptToken, Whitespace}

object TypeScriptImport {
  private[this] val tokenParser = P(Comment.parser | Whitespace.parser | Module.parser)

  val parser: Parser[Seq[ScriptToken]] = P(tokenParser.rep)

  def parse(s: String) = parser.parse(s) match {
    case Parsed.Success(value, idx) => Right(value)
    case Parsed.Failure(p, idx, extra) => Left(extra.traced.trace)
  }
}
