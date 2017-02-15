package models.parse.importer

import fastparse.all._

object ParseTest {
  val newline = P("\n" | "\r\n" | "\r" | "\f")
  val whitespace = P(" " | "\t" | newline).!

  val lineComment = P("//" ~ (!"\n" ~ AnyChar).rep.! ~ (newline | End))
  val multiLineComment = P("/*" ~ (!"*/" ~ AnyChar).rep.! ~ "*/")
  val comment = lineComment | multiLineComment

  val hexDigit = P(CharIn('0' to '9', 'a' to 'f', 'A' to 'F'))

  val escape = P("\\" ~ ((!(newline | hexDigit) ~ AnyChar) | (hexDigit.rep(min = 1, max = 6) ~ whitespace.?)))

  val whitespaceToken = P(comment | whitespace)

  val ws = P(whitespaceToken.rep)
}
