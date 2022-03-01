package com.baeldung.clearconfig

import cats.Id
import japgolly.clearconfig.*
import cats.implicits.*
import cats.syntax.apply
import cats.catsInstancesForId
import japgolly.clearconfig.internals.ConfigDef
import java.io.File

case class DatabaseConfig(
  host: String,
  dbName: String,
  username: String,
  password: String,
  maxConnection: Option[Int]
)

// case class Credential(username: String, password: String)
// object Credential {
//   def config: ConfigDef[Credential] = (
//     ConfigDef.need[String]("USERNAME"),
//     ConfigDef.need[String]("PASSWORD")
//   ).mapN(apply)
// }

// case class ConnectionPool(minConnection: Int, maxConnection: Int)
// object ConnectionPool {
//   def config: ConfigDef[ConnectionPool] = (
//     ConfigDef.need[Int]("MINCONNECTION") |@|
//     ConfigDef.need[Int]("MAXCONNECTION")
//   )(apply)

//   def default = ConnectionPool(2, 10)
// }

object DatabaseConfig {

  def config: ConfigDef[DatabaseConfig] = (
    ConfigDef.need[String]("host"),
    ConfigDef.need[String]("dbname"),
    ConfigDef.need[String]("username"),
    ConfigDef.need[String]("password"),
    ConfigDef.get[Int]("maxConnection")
  ).mapN(apply)
}


object ConfigLoader extends App {

  val sysFilePath = new File(".").getAbsolutePath + File.separator + "scala3-lang-2" + File.separator + "config" + File.separator + "env.conf"
  def configSources: ConfigSources[Id] =
    ConfigSource.environment[Id].caseInsensitive > // from system environment
    ConfigSource.propFile[Id](sysFilePath, optional = true) >
      ConfigSource
        .propFileOnClasspath[Id]("application-prod.conf", optional = true)
        .caseInsensitive >
      ConfigSource
        .propFileOnClasspath[Id]("/application.conf", optional = false)
        .caseInsensitive >
      ConfigSource.system[Id].caseInsensitive // from jvm parameter and can run as: sbt -Dapp_home=/Users/krish "project scala3_lang_2;runMain com.baeldung.clearconfig.ConfigLoader"

  val (dbCfg, report): (DatabaseConfig, ConfigReport) =
    DatabaseConfig.config
      .withReport
      .run(configSources)
      .getOrDie()

  println(dbCfg)
  println(report.full)
}
