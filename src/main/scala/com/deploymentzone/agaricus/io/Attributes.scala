package com.deploymentzone.agaricus.io

import scala.util.Try

object Attributes {
}

case class Attribute(category: String, descriptions: Seq[Description])

object Attribute {
  def unapply(str: String): Option[Attribute] =
    if (!str.contains(". "))
      None
      else
        Try {
        str.substring(str.indexOf(". ") + 1).split(':') match {
          case Array(c, ds) =>
            val protoDescriptions = ds.split(",")
            val descriptions = protoDescriptions.map(Description.unapply).filter(_.isDefined).map(_.get)
            assert(protoDescriptions.length == descriptions.length)
            Some(Attribute(c.stripPrefix(" "), descriptions))
          case _ => None
        }}.getOrElse(None)
}

case class Description(letter: String, name: String) {
}

object Description {
  def unapply(str: String): Option[Description] =
    str.split('=') match {
      case Array(n, l) if l.length == 1 => Some(Description(l, n.stripPrefix(" ")))
      case _ => None
    }
}
