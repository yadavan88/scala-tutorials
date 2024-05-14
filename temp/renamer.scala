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
