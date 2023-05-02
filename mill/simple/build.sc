import mill._, scalalib._

object simple extends ScalaModule {
  override def scalaVersion = "2.13.10"

  override def ivyDeps = Agg(
    ivy"com.lihaoyi::fansi:0.4.0"
  )
}