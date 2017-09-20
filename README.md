# Agaricus Lepiota

Given the [Agaricus Lepiota](https://archive.ics.uci.edu/ml/datasets/mushroom) dataset provided by UCI Machine Learning
Repository learn a model that identifies poisonous/non-edible species of mushrooms.

For interactive testing purposes, a Spark shell console application is provided.

## Setup

The `bin/` commands assume you have a local Apache Spark 2.2.x available at `/usr/local/Cellar/apache-spark/2.2.0`
(where Homebrew installs it). If this isn't the case create a file named`bin/.env` with the following contents:

```bash
SPARK_HOME=/path/to/spark/2.2.x
```

`./bin/shell` assumes that `sbt` is on the `PATH`. `sbt` is used to package the JAR.

## Usage

Execute `./bin/shell` to package the source code and begin a Spark shell session locally, then run the
following command:

```scala
import com.deploymentzone.agaricus._
val model = Agaricus.model()
```

This will generate a model and then calculate its prediction error and report the accuracy to the terminal.

From another terminal you can pull random lines to test from the data set by running `./bin/random` (this
works on MacOS with the Homebrew package `coreutils` installed since it relies on GNU Shuffle).

```scala
Agaricus.paste(model, "e,f,y,n,t,n,f,c,b,n,t,b,s,s,w,g,p,w,o,p,k,v,d")
```
