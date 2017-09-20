package com.deploymentzone.agaricus

import com.deploymentzone.agaricus.io.{Attributes, Data}
import com.deploymentzone.agaricus.ml.SVM
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

/**
  * Agaricus Lepiota driver.
  */
object Agaricus {
  def main(args: Array[String]): Unit = {

    val attributes = Attributes()
    val svm = SVM.initialize(attributes)

    val spark = SparkSession.builder.appName("Agaricus Lepiota").getOrCreate()

    import spark.implicits._
    val data = spark.sparkContext.parallelize(Data()).map(svm.toLabeledPoint)

    val Array(training, test) = data.randomSplit(Array(8.0, 2.0))

    val lr = new LogisticRegression()
        .setMaxIter(100)

    val model = lr.fit(training.toDF)

    model.evaluate(test.toDF).predictions.show(20)
  }
}
