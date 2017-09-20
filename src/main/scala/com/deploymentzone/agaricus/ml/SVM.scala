package com.deploymentzone.agaricus.ml

import com.deploymentzone.agaricus.io.Attribute
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors

case class SVM(categories: Seq[Map[String, Double]]) {
  def toVector(values: Seq[String]): linalg.Vector = {
    require(values.length == categories.length)

    Vectors.dense(values.zip(categories).map { case (key, map) =>
        map.getOrElse(key, 0.5) // 0.5 is used for non-explicit ? [missing] values
    }.toArray)
  }

  def toLabeledPoint(line: String): LabeledPoint = {
    line.split(",") match {
      case Array(h, rest @ _*) => LabeledPoint(if (h == "e") 1.0 else 0.0, toVector(rest))
      case _ => throw new MatchError(line)
    }
  }
}

object SVM {
  def initialize(attributes: Seq[Attribute]): SVM = {
    val categories = attributes.map { attr =>
      attr.descriptions.zipWithIndex.map { case (d, idx) => (d.letter, idx + 1.0) }.toMap
    }
    new SVM(categories)
  }
}
