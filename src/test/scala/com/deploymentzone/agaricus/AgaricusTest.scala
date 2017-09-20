package com.deploymentzone.agaricus

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FunSuite

class AgaricusTest extends FunSuite with SharedSparkContext {
  test("..."){
    val linesRDD = sc.parallelize(Seq(
      ".",
      "..."))

  }
}
