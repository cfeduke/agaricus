package com.deploymentzone.agaricus.io

import org.scalatest.FunSuite

class DataTest extends FunSuite {

  test("gzipped data set can be retrieved from resources") {
    assert(Data.apply().size == 8124)
  }

}
