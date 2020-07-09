package sxhkdrcc

object Nodes {

  sealed trait Statement

  case class Declare(identifier: String, value: String) extends Statement

  case class Binding(trigger: List[Trigger], body: List[Command]) extends Statement

  sealed trait Trigger

  case object Trig extends Trigger

  case object Nothing extends Trigger

  case class Key(value: String) extends Trigger

  case class TSelect(options: List[Trigger]) extends Trigger

  sealed trait Command

  case class Literal(value: String) extends Command

  case class StringLiteral(value: String) extends Command

  case class CSelect(options: List[Command]) extends Command

  case class Substitute(identifier: String) extends Trigger with Command
}
