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


    val declare = lift2(Declare.apply, "let" *> ident, "=" *> ident)
  }

  implicit def charToken(c: Char): Parsley[Char] = token(char(c))
  implicit def stringToken(s: String): Parsley[String] = token(string(s) <* notFollowedBy(char('_') <|> alphaNum))
  def token[A](t: => Parsley[A]): Parsley[A] = attempt(t) <* many(whitespace)
}
