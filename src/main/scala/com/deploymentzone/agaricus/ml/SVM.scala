package com.deploymentzone.agaricus.ml

import com.deploymentzone.agaricus.io.Attribute
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.Vectors

class SVM private(categories: Seq[Map[String, Double]]) {
  def toVector(values: Array[String]): linalg.Vector = {
    require(values.length == categories.length)

    Vectors.dense(values.zip(categories).map { case (key, map) =>
        map.getOrElse(key, 0.5) // 0.5 is used for non-explicit ? [missing] values
    })
  }
}

object SVM {
  def apply(attributes: Seq[Attribute]): SVM = {
    val categories = attributes.map { attr =>
      attr.descriptions.zipWithIndex.map { case (d, idx) => (d.letter, idx + 1.0) }.toMap
    }
    new SVM(categories)
  }
}
