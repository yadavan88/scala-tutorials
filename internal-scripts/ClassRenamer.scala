/** This is an internal scala-cli script written just to reduce manual work of
  * renaming class names. This doesn't fully correct all the class names,
  * however reduces the manual effort by 90%.
  */
//> using toolkit default
import os._

object RenameClassNames {
  def main(args: Array[String]): Unit = {
    val directory = os.pwd / os.up

    os.walk(directory)
      .filter(_.ext == "scala")
      .filter(_.toString.contains("src/test/scala"))
      .filter(f =>
        f.toString.endsWith("Test.scala") || f.toString.endsWith("Spec.scala")
      )
      .foreach { filePath =>
        val fileName = filePath.last
        val content = os.read(filePath)

        val existingClassName = getClassFromContent(content)

        val fileNameWithoutExtension = fileName.dropRight(6)

        def isTestClass(existingClassName: String): Boolean = {
          existingClassName.endsWith("Spec") || existingClassName.endsWith("Test")
        }

        // Rename the class if it doesn't match the filename
        if (
          isTestClass(existingClassName) && existingClassName.toLowerCase != fileNameWithoutExtension.toLowerCase
        ) {
          // Update the content with the new class name
          val updatedContent = content.replaceAll(
            s"class\\s+$existingClassName",
            s"class $fileNameWithoutExtension"
          )

          val newFileName = filePath.toString.replace("Test.scala", "UnitTest.scala").replace("Spec.scala", "UnitTest.scala")
          //rename file
          os.move(filePath, os.Path(newFileName))

          //Now, rename the class
          os.write.over(os.Path(newFileName), updatedContent)
          println(s"Renamed class in $fileName to $fileNameWithoutExtension")
        }
      }
  }

  // Extract class name from file content
  def getClassFromContent(content: String): String = {
    val classNameRegex = """class\s+(\w+)""".r
    val matchResult = classNameRegex.findFirstMatchIn(content)
    matchResult match {
      case Some(m) => m.group(1)
      case None    => ""
    }
  }
}
