package com.deploymentzone.agaricus.ml

import com.deploymentzone.agaricus.io.Attribute
import org.apache.spark.mllib.linalg.Vectors
import org.scalatest.FunSuite

import scala.util.Try

class SVMTest extends FunSuite {

  private val attributes = Seq(Attribute.unapply("13. stalk-surface-below-ring: fibrous=f,scaly=y,silky=k,smooth=s"),
    Attribute.unapply("14. stalk-color-above-ring: brown=n,buff=b,cinnamon=c,gray=g,orange=o, pink=p,red=e,white=w,yellow=y"),
    Attribute.unapply("16. veil-type: partial=p,universal=u")).flatten
  private val svm = SVM(attributes)

  test("toVector properly encodes a series of values") {
    val values = "y,p,u" // scaly, pink, universal
    val vectors = svm.toVector(values.split(","))
    assert(vectors == Vectors.dense(2.0, 6.0, 2.0))
  }

  test("toVector properly substitutes 0.5 for missing values") {
    val values = "?,p,u"
    val vectors = svm.toVector(values.split(","))
    assert(vectors == Vectors.dense(0.5, 6.0, 2.0))
  }

  test("toVector fails when the # of categories doesn't match the # of values") {
    val values = "?,e,q,x"
    val succeeded = Try {
      svm.toVector(values.split(","))
    }.isSuccess
    assert(!succeeded)
  }
}
