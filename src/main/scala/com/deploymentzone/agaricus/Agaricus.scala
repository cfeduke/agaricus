package com.deploymentzone.agaricus

import com.deploymentzone.agaricus.io.{Attributes, Data}
import com.deploymentzone.agaricus.ml.SVM
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.sql.SparkSession

/**
  * Agaricus Lepiota driver.
  */
object Agaricus {
  def apply(): LogisticRegressionModel = {

    val attributes = Attributes()
    val svm = SVM.initialize(attributes)

    val spark = SparkSession.builder.appName("Agaricus Lepiota").getOrCreate()

    import spark.implicits._
    val data = spark.sparkContext.parallelize(Data()).map(svm.toLabeledPoint)

    val Array(training, test) = data.randomSplit(Array(8.0, 2.0))

    val lr = new LogisticRegression()
        .setMaxIter(10)

    val model: LogisticRegressionModel = lr.fit(training.toDF)

    val predictions = model.evaluate(test.toDF).predictions
    val metrics = new MulticlassMetrics(predictions.select($"prediction", $"label").map {
      row => (row.getDouble(0), row.getDouble(1))
    }.rdd)

    print(f"Model Accuracy: ${metrics.accuracy}%2.2f")

    model
  }
}
