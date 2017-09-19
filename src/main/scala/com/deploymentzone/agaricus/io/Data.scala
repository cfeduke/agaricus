package com.deploymentzone.agaricus.io

import java.io.InputStream
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream

import com.google.common.io.{ByteSource, Resources}

import scala.collection.JavaConverters._

object Data {

  def apply(): List[String] =
    GZippedByteSource(Resources.asByteSource(Resources.getResource("agaricus-lepiota.data.gz")))
      .asCharSource(Charset.defaultCharset()).readLines().iterator().asScala.toList


  private case class GZippedByteSource(gzippedSource: ByteSource) extends ByteSource {
    override def openStream(): InputStream = new GZIPInputStream(gzippedSource.openStream())
  }
}
