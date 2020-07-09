package sxhkdrcc.parsers

import parsley.Char._
import parsley.Combinator._
import parsley.Parsley._
import parsley._

import sxhkdrcc.parsers.Utils._
import sxhkdrcc.Nodes._

object TriggerParser {

  private val nothing = "_" #> Nothing
  private val key = some(alphaNum).map(_.mkString) <#> Key
  private val selectTrig = ("{" *> sepBy1(nothing <|> parser, ",") <* "}") <#> TSelect

  lazy val parser: Parsley[Trigger] = substitute <|> selectTrig <|> key
}
