/**
  * This is an internal scala-cli script written to rename all the test files in src/test/scala directories
  * to end with "UnitTest.scala" to follow the naming standard.
  * It could have been combined with `ClassRenamer.scala` and do both together, 
  * but it was unnecessary as this rename was alrady performed before writing `ClassRenamer.scala`.
  */

//> using scala 3.3.3
//> using toolkit default

import os._

@main
def main = {

  val rootPath = os.pwd / os.up
  import os._

  def listFilesRecursively(dir: os.Path): Seq[os.Path] = {
    os.list(dir).flatMap { entry =>
      if (os.isDir(entry)) {
        listFilesRecursively(entry)
      } else {
        Seq(entry)
      }
    }
  }

  val allFiles = listFilesRecursively(rootPath)

  val invalidFiles = allFiles.filter(_.toString.contains("src/test/scala"))
  .filter(f => f.toString.endsWith("Test.scala") || f.toString.endsWith("Spec.scala"))
  .filterNot(_.toString.endsWith("UnitTest.scala"))

  invalidFiles.map { path =>
    val newPath = path.toString.replace("Test.scala", "UnitTest.scala").replace("Spec.scala", "UnitTest.scala")
    println(path.toString)
    println(newPath)
    println("-----")
    os.move(path, os.Path(newPath))
  }


}
