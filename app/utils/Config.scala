package utils

import play.api.{ Environment, Mode }

object Config {
  val projectId = "scala-js-typescript"
  val projectName = "scala-js-typescript"
  val projectUrl = "https://github.com/KyleU/scala-js-typescript"
  val adminEmail = "scala-js-typescript@kyleu.com"
  val version = "0.1"
  val pageSize = 100
}

@javax.inject.Singleton
class Config @javax.inject.Inject() (val cnf: play.api.Configuration, env: Environment) {
  val debug = env.mode == Mode.Dev
  val dataDir = {
    import better.files._
    cnf.get[Option[String]]("data.directory").getOrElse("./data").toFile
  }
}
