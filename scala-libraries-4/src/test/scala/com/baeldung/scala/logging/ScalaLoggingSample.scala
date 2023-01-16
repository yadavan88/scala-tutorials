package com.baeldung.scala.logging

import com.typesafe.scalalogging.Logger
import com.typesafe.scalalogging.LazyLogging
import com.typesafe.scalalogging.StrictLogging
import com.typesafe.scalalogging.CanLog

class ScalaLoggingSample {
  val logger = Logger(this.getClass)
  def query(error: Boolean) = {

    logger.whenDebugEnabled {
      println("Logging from print statement")
    }

    if (error)
      logger.error("Some error occurred during querying")
    else
      logger.error("Queried from database")

    List(1, 2)
  }
}

class MyLaziLogging extends StrictLogging {
  logger.info("This is from a lazy logger class")

  logger.whenDebugEnabled {
    println("Logging from >>>>>>>")
  }
}


object LoggingImplicits {
  implicit case object WithRequestId extends CanLog[RequestId] {
    override def logMessage(originalMsg: String, a: RequestId): String =
      s"[REQ: ${a.id}] $originalMsg"
  }
}
class TracingLogger extends StrictLogging {
  import LoggingImplicits.WithRequestId
  def getFromDB(id: Long)(implicit req: RequestId) = {
    val logger = Logger.takingImplicit[RequestId](this.getClass)
  }
}

object LoggingApp extends App {
  val logger = Logger(this.getClass())
  logger.info("Logger from Logging Main App")
  val sample = new ScalaLoggingSample()
  sample.query(true)
  sample.query(false)
  val l = new MyLaziLogging()

  val part1 = "Hello "

  logger.info(s"$part1 World")
  logger.info("{} World",part1)

}

case class RequestId(id: String)
