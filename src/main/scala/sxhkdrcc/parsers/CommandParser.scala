package sxhkdrcc.parsers

import parsley.Char._
import parsley.Combinator._
import parsley.Parsley._
import parsley._

import sxhkdrcc.parsers.Utils._
import sxhkdrcc.Nodes._

object CommandParser {

  private val chars = satisfy {
    case '"' | '\\' => false
    case _ => true
  }
  private val literals = satisfy {
    case ' ' | '\t' | '\f' | '\u000b' | ',' => false
    case _ => true
  }
  private val string = between(char('"'), '"', many(chars)).map(_.mkString) <#> StringLiteral
  private val selectComm = ("{" *> sepBy1(parser, ",") <* "}") <#> CSelect
  private val literal = token(some(literals).map(_.mkString)) <#> Literal

  lazy val parser: Parsley[Command] = substitute <|> selectComm <|> string <|> literal
}
