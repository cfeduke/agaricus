package com.deploymentzone.agaricus

/**
 * A simple test for everyone's favourite wordcount example.
 */

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FunSuite

class AgaricusTest extends FunSuite with SharedSparkContext {
  test("..."){
    val linesRDD = sc.parallelize(Seq(
      ".",
      "..."))

  }
}
