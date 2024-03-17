package com.baeldung.scala.strings.isomorphic

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class IsomorphicStringsUnitTest extends AnyFlatSpec with Matchers with TableDrivenPropertyChecks {

  //format:off
  val table = Table(
    ("String 1", "String 2", "Is Isomorphic"),
    ("ABCD", "PQRS", true),       // One-to-one mapping
    ("foo", "bar", false),         // Different positions
    ("abcd", "pqr", false),        // Different lengths
    ("paper", "title", true),     // One-to-one mapping
    ("aab", "xxy", true),          // One-to-one mapping
    ("abcd", "aabb", false),       // Different mappings
    ("egg", "add", true),          // One-to-one mapping
    (" ", " ", true),              // Both strings are empty
    ("a", "a", true),              // Both strings have only one character
    ("aa", "bb", true),            // Both strings have repeating characters mapped to different characters
    ("ab", "ba", true),            // Each character is mapped to the other
    ("ab", "bc", false),           // One character in str1 maps to multiple characters in str2
    ("abc", "aba", false),         // Multiple characters in str1 map to a single character in str2
    ("aaa", "bbb", true),          // One character in str1 maps to multiple characters in str2
  )
  //format:on
  it should "check if two strings are isomorphic" in {
    forAll(table) { (str1, str2, isIsomorphic) =>
      IsomorphicStrings.isIsomorphic(str1, str2) shouldBe isIsomorphic
    }
  }

}
