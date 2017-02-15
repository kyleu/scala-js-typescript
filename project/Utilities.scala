import Dependencies._
import com.typesafe.sbt.SbtScalariform.{ScalariformKeys, scalariformSettings}
import net.virtualvoid.sbt.graph.DependencyGraphSettings.graphSettings
import sbt.Keys._
import sbt._

object Utilities {
  private[this] val metricsLibs = Seq(
    Play.playLib, Akka.actor,
    Metrics.metrics, Metrics.healthChecks, Metrics.json, Metrics.jvm, Metrics.ehcache, Metrics.jettyServlet, Metrics.servlets, Metrics.graphite
  )

  lazy val metrics = (project in file("util/metrics"))
    .settings(ScalariformKeys.preferences := ScalariformKeys.preferences.value)
    .settings(libraryDependencies ++= metricsLibs)
    .settings(Shared.commonSettings: _*)
    .settings(graphSettings: _*)
    .settings(scalariformSettings: _*)
}
