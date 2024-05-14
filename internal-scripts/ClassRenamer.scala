/**
  * This is an internal scala-cli script written just to reduce manual work of renaming class names.
  * This doesn't fully correct all the class names, however reduces the manual effort by 90%.
  */
//> using toolkit default
import os._

object RenameClassNames {
  def main(args: Array[String]): Unit = {
    val directory = os.pwd / os.up

    os.walk(directory).filter(_.ext == "scala").filter(_.toString.contains("src/test/scala")).foreach { filePath =>
      val fileName = filePath.last
      val content = os.read(filePath)

      val existingClassName = getClassFromContent(content)

      val fileNameWithoutExtension = fileName.dropRight(6)

      // Rename the class if it doesn't match the filename
      if (!existingClassName.toLowerCase.startsWith("case ") && existingClassName.toLowerCase != fileNameWithoutExtension.toLowerCase) {
        // Update the content with the new class name
        val updatedContent = content.replaceAll(s"class\\s+$existingClassName", s"class $fileNameWithoutExtension")

        os.write.over(filePath, updatedContent)
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
      case None => ""
    }
  }
}
