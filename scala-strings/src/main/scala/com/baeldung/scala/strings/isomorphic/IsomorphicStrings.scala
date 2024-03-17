package com.baeldung.scala.strings.isomorphic

object IsomorphicStrings {

  def isIsomorphic(str1: String, str2: String) = {
    val charMappings = scala.collection.mutable.Map.empty[Char, Char]
    if (str1.length != str2.length) {
      false
    } else {
      str1.zip(str2).forall { (c1, c2) =>
        charMappings.get(c1) match {
          case Some(mappedChar) if mappedChar == c2 => true
          case Some(_) | None if !charMappings.values.toSet.contains(c2) =>
            charMappings.put(c1, c2)
            true
          case _ => false
        }
      }
    }
  }
}
