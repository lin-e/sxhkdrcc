package sxhkdrcc

import parsley.Char._
import parsley.Combinator._
import parsley.Parsley._
import parsley._

import scala.language.implicitConversions

import sxhkdrcc.Nodes._

object Main {
  def main(args: Array[String]): Unit = {
    val ident = token(some(alphaNum).map(_.mkString))

    val substitute = "#" *> ident <#> Substitute

    val nothing = "_" #> Nothing

    lazy val selectTrig: Parsley[Trigger] = ("{" *> sepBy1(nothing <|> trigger, ",") <* "}") <#> TSelect
    lazy val selectComm: Parsley[Command] = ("{" *> sepBy1(command, ",") <* "}") <#> CSelect

    lazy val trigger = substitute <|> selectTrig <|> ("gtrig" #> Trig)
    lazy val command = substitute <|> selectComm <|> ("gcomm" #> Comm)

    val declare = lift2(Declare.apply, "let" *> ident, "=" *> ident)
    val binding = lift2(Binding.apply, sepBy1(trigger, "+"), ":" *>  many(command))

    val prog = sepEndBy1(declare <|> binding, some(endOfLine)) <* many(whitespace) <* notFollowedBy(anyChar)

    val src =
      """let primary = alt
        |let secondary = super
        |let arg1 = q
        |let arg2 = r
        |#primary + #secondary + {_, gtrig}:     gcomm {#arg1, #arg2}
        |#primary + gtrig:                       gcomm #arg1 #arg2
      """.stripMargin

    runParser(prog, src) match {
      case Success(x) => print(x)
      case Failure(msg) => print(msg)
    }
  }

  implicit def charToken(c: Char): Parsley[Char] = token(char(c))
  implicit def stringToken(s: String): Parsley[String] = token(string(s))
  def token[A](t: => Parsley[A]): Parsley[A] = attempt(t) <* many(
    char(' ') <|> char('\t') <|> char('\f') <|> char('\u000b')
  )
}
