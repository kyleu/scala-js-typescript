package models.parse.parser

import scala.util.parsing.combinator.token.StdTokens

trait TSTokens extends StdTokens {
  case class LineComment(chars: String) extends Token {
    override def toString = "//" + chars
  }

  case class MultilineComment(chars: String) extends Token {
    override def toString = "/*" + chars + "*/"
  }

  case class Whitespace(chars: String) extends Token {
    override def toString = "[whitespace]"
  }
}
