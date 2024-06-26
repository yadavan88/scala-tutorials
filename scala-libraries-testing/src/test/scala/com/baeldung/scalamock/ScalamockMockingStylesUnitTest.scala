package com.baeldung.scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.wordspec.AnyWordSpec

class ScalamockMockingStylesUnitTest extends AnyWordSpec with MockFactory {

  "MockingStyle" should {
    trait MockitoWannabe {
      def foo(i: Int): Int
    }
    "record and then verify" in {
      val mockedWannabe = stub[MockitoWannabe]
      (mockedWannabe.foo).when(*).onCall((i: Int) => i * 2)
      assert(mockedWannabe.foo(12) === 24)
      (mockedWannabe.foo).verify(12)
    }
  }

}
