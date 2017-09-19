package com.deploymentzone.agaricus

import org.apache.spark.rdd._
import org.apache.spark.sql.SparkSession

/**
  * Agaricus Lepiota driver.
  */
object Agaricus {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Agaricus Lepiota").getOrCreate()
  }
}
