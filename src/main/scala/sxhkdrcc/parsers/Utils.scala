package sxhkdrcc.parsers

import parsley._
import parsley.Char._
import parsley.Combinator._
import parsley.Parsley._

import scala.language.implicitConversions

import sxhkdrcc.Nodes._

object Utils {

  val ident: Parsley[String] = token(some(alphaNum).map(_.mkString))

  val substitute: Parsley[Substitute] = "#" *> ident <#> Substitute

  implicit def charToken(c: Char): Parsley[Char] = token(char(c))

  implicit def stringToken(s: String): Parsley[String] = token(string(s))

  def token[A](t: => Parsley[A]): Parsley[A] = attempt(t) <* many(
    char(' ') <|> char('\t') <|> char('\f') <|> char('\u000b')
  )
}