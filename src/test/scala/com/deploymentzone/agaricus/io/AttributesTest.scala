package com.deploymentzone.agaricus.io

import org.scalatest.FunSuite

class AttributesTest extends FunSuite {

  test("descriptions formatted properly parse") {
    " knobbed=k" match {
      case Description(d) =>
        assert(d.letter == "k")
        assert(d.name == "knobbed")
      case _ =>
        fail("did not extract a description from provided string")
    }
  }

  test("descriptions not formatted properly do not parse") {
    "cat-dog" match {
      case Description(_) =>
        fail("did not expect to match a Description from 'cat-dog'")
      case _ =>
        assert(true)
    }
  }

  test("attribute lines formatted properly parse") {
    "11. stalk-root: bulbous=b,club=c,cup=u,equal=e, rhizomorphs=z,rooted=r,missing=?" match {
      case Attribute(a) =>
        assert(a.category == "stalk-root")
        assert(a.descriptions.length == 7)
    }
  }

  test("attribute lines improperly formatted do not parse") {
    "stalk-root: bulbous=b,club=c,cup=u,equal=e, rhizomorphs=z,rooted=r,missing=? 11." match {
      case Attribute(_) =>
        fail("did not expect to match an attribute from the provided string")
      case _ =>
        assert(true)
    }
  }

  test("attribute lines with an improperly formatted description do not parse") {
    "stalk-root: bulbous=b,clubc,cup=u,equal=e, rhizomorphs=z,rooted=r,missing=? 11." match {
      case Attribute(_) =>
        fail("did not expect to match an attribute from the provided string")
      case _ =>
        assert(true)
    }
  }
}
