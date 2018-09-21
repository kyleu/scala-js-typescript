package models.parse.parser

import scala.util.parsing.input.CharArrayReader.EofCh
import scala.util.parsing.combinator._
import scala.util.parsing.combinator.lexical._
import scala.collection.mutable
import scala.util.parsing.input.CharSequenceReader

class TSDefLexical extends Lexical with TSTokens with ImplicitConversions {
  override def whitespace: Parser[Any] = rep(elem("whitespace", _ => false))

  override def token: Parser[Token] = {
    multiLineCommentParser |
      importCommentParser |
      singleLineCommentParser |
      whitespaceParser |
      identifier |
      numericLiteral |
      stringLiteral |
      EofCh ^^^ EOF |
      delim |
      failure("illegal character")
  }

  def tokens = rep(token)
  def tokenizeString(s: String) = phrase(tokens)(new CharSequenceReader(s, 0))

  val whitespaceParser = rep1(whitespaceChar) ^^ { c => Whitespace("?") }

  val importCommentParser = 'i' ~> 'm' ~> 'p' ~> 'o' ~> 'r' ~> 't' ~> ' ' ~> rep(chrExcept(EofCh, '\n')) ^^ { text => ImportComment(text.mkString) }
  val singleLineCommentParser = '/' ~> '/' ~> rep(chrExcept(EofCh, '\n')) ^^ { text => LineComment(text.mkString) }
  val multiLineCommentParser = ('/' ~> '*' ~> rep1(not('*' ~ '/') ~> chrExcept(EofCh)) <~ '*' <~ '/') ^^ { text => MultilineComment(text.mkString) }

  def identifier = stringOf1(identifierStart, identifierPart) ^^ {
    x => if (reserved contains x) Keyword(x) else Identifier(x)
  }

  def identifierStart = elem("", isIdentifierStart) | (pseudoChar filter isIdentifierStart)
  def identifierPart = elem("", isIdentifierPart) | (pseudoChar filter isIdentifierPart)

  private[this] val hexNum = (elem('x') | 'X') ~> rep1(hexDigit) ^^ {
    digits => digits.foldLeft(0L)(_ * 16 + _).toString
  }
  private[this] val octalNum = rep1(octalDigit) ^^ {
    digits => digits.foldLeft(0L)(_ * 8 + _).toString
  }
  private[this] val digitNum = stringOf1(digit) ~ opt(stringOf1('.', digit)) ^^ {
    case part1 ~ part2 => part1 + part2.getOrElse("")
  }

  def numericLiteral = ('0' ~> (hexNum | octalNum | success("0")) | digitNum) ^^ NumericLit

  def stringLiteral = (quoted('\"') | quoted('\'')) ^^ StringLit

  def quoted(quoteChar: Char) = quoteChar ~> stringOf(inQuoteChar(quoteChar)) <~ quoteChar

  def inQuoteChar(quoteChar: Char) = chrExcept('\\', quoteChar, EofCh) | pseudoChar

  def pseudoChar = '\\' ~> (
    'x' ~> hexDigit ~ hexDigit ^^ {
      case d1 ~ d0 => (16 * d1 + d0).toChar
    }
    | 'u' ~> hexDigit ~ hexDigit ~ hexDigit ~ hexDigit ^^ {
      case d3 ~ d2 ~ d1 ~ d0 => (4096 * d3 + 256 * d2 + 16 * d1 + d0).toChar
    }
    | elem("", _ => true) ^^ {
      case '0' => '\u0000'
      case 'b' => '\u0008'
      case 't' => '\u0009'
      case 'n' => '\u000A'
      case 'v' => '\u000B'
      case 'f' => '\u000C'
      case 'r' => '\u000D'
      case c => c // including ' " \
    })

  def octalDigit = elem("octal digit", c => '0' <= c && c <= '7') ^^ (_ - '0')

  def hexDigit = accept("hex digit", {
    case c @ ('0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9') => c - '0'
    case c @ ('A' | 'B' | 'C' | 'D' | 'E' | 'F') => c - 'A' + 10
    case c @ ('a' | 'b' | 'c' | 'd' | 'e' | 'f') => c - 'a' + 10
  })

  // legal identifier chars
  def isIdentifierStart(c: Char): Boolean = c == '$' || c == '_' || c.isUnicodeIdentifierStart
  def isIdentifierPart(c: Char): Boolean = c == '$' || c.isUnicodeIdentifierPart

  def stringOf(p: => Parser[Char]): Parser[String] = rep(p) ^^ chars2string
  def stringOf1(p: => Parser[Char]): Parser[String] = rep1(p) ^^ chars2string
  def stringOf1(first: => Parser[Char], p: => Parser[Char]): Parser[String] = rep1(first, p) ^^ chars2string

  private def chars2string(chars: List[Char]) = chars.mkString("")

  // reserved words and delimiters

  /** The set of reserved identifiers: these will be returned as Keywords */
  val reserved = new mutable.HashSet[String]

  /** The set of delimiters (ordering does not matter) */
  val delimiters = new mutable.HashSet[String]

  private lazy val _delim: Parser[Token] = {
    /* construct parser for delimiters by |'ing together the parsers for the
     * individual delimiters, starting with the longest one -- otherwise a
     * delimiter D will never be matched if there is another delimiter that is
     * a prefix of D
     */
    def parseDelim(s: String): Parser[Token] = accept(s.toList) ^^ { x => Keyword(s) }

    val d = new Array[String](delimiters.size)
    delimiters.copyToArray(d, 0)
    scala.util.Sorting.quickSort(d)
    d.toList.map(parseDelim).foldRight(failure("no matching delimiter"): Parser[Token])((x, y) => y | x)
  }

  protected def delim: Parser[Token] = _delim
}
