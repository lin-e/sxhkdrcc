package sxhkdrcc

import parsley.Char._
import parsley.Combinator._
import parsley.Parsley._
import parsley._

import sxhkdrcc.Nodes._
import sxhkdrcc.parsers._
import sxhkdrcc.parsers.Utils._

object Compiler {
  def main(args: Array[String]): Unit = {

    val declare = lift2(Declare.apply, "let" *> ident, "=" *> ident)
    val binding = lift2(Binding.apply, sepBy1(TriggerParser.parser, "+"), ":" *>  many(CommandParser.parser))

    val prog = sepEndBy1(declare <|> binding, some(endOfLine)) <* many(whitespace) <* notFollowedBy(anyChar)

    val src =
      """let primary = alt
        |#primary + {a,b,c,_} + x:   this is a test command with arguments {1, 2, 3, 5}
      """.stripMargin

    runParser(prog, src) match {
      case Success(x) => print(x)
      case Failure(msg) => print(msg)
    }
  }
}
