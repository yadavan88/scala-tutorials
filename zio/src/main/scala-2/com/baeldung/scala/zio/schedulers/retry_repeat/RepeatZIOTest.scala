package com.baeldung.scala.zio.schedulers.retry_repeat

import zio._

object RepeatZIOTest extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zio = ZIO.succeed(println("Hello...."))
    zio.repeat(Schedule.recurs(2))
  }
}
