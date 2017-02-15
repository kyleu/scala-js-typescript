package utils

import play.api.{Environment, Mode}
import utils.metrics.MetricsConfig

@javax.inject.Singleton
class Configuration @javax.inject.Inject() (val cnf: play.api.Configuration, env: Environment) {
  val debug = env.mode == Mode.Dev
  val dataDir = {
    import better.files._
    cnf.getString("data.directory").getOrElse("./data").toFile
  }

  // Metrics
  val jmxEnabled = cnf.getBoolean("metrics.jmx.enabled").getOrElse(true)
  val graphiteEnabled = cnf.getBoolean("metrics.graphite.enabled").getOrElse(false)
  val graphiteServer = cnf.getString("metrics.graphite.server").getOrElse("127.0.0.1")
  val graphitePort = cnf.getInt("metrics.graphite.port").getOrElse(2003)
  val servletEnabled = cnf.getBoolean("metrics.servlet.enabled").getOrElse(true)
  val servletPort = cnf.getInt("metrics.servlet.port").getOrElse(9001)

  val metrics: MetricsConfig = MetricsConfig(
    jmxEnabled,
    graphiteEnabled,
    graphiteServer,
    graphitePort,
    servletEnabled,
    servletPort
  )
}
