package com.deploymentzone.agaricus.io

import java.nio.charset.Charset

import com.google.common.io.Resources

import scala.collection.JavaConverters._
import scala.util.Try

object Attributes {
  def apply(): Seq[Attribute] = {
    val url = Attributes.getClass.getClassLoader.getResource("attributes.txt")
    val lines = Resources.asCharSource(url, Charset.defaultCharset()).readLines().asScala
    val attributes = lines.flatMap(l => Attribute.unapply(l))
    assert(lines.length == attributes.length, "failed to parse at least one attribute from attributes.txt")
    attributes
  }
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
            val descriptions = protoDescriptions.flatMap(d => Description.unapply(d))
            assert(protoDescriptions.length == descriptions.length, "failed to parse at least one description")
            Some(Attribute(c.stripPrefix(" "), descriptions))
          case _ => None
        }}.getOrElse(None)
}

case class Description(letter: String, name: String)

object Description {
  def unapply(str: String): Option[Description] =
    str.split('=') match {
      case Array(n, l) if l.length == 1 => Some(Description(l, n.stripPrefix(" ")))
      case _ => None
    }
}
