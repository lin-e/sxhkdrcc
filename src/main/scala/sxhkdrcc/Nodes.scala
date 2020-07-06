package sxhkdrcc

object Nodes {

  sealed trait Statement

  case class Declare(identifier: String, value: String) extends Statement

  case class Binding(trigger: Trigger, body: String) extends Statement

  sealed trait Trigger
}
