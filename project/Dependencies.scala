import sbt._

object Dependencies {
  object Logging {
    val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.21"
  }

  object Play {
    private[this] val version = "2.6.19"
    val playLib = "com.typesafe.play" %% "play" % version
    val playFilters = play.sbt.PlayImport.filters
    val guice = play.sbt.PlayImport.guice
    val playWs = play.sbt.PlayImport.ws
    val playTest = "com.typesafe.play" %% "play-test" % version % "test"
    val playMailer = "com.typesafe.play" %% "play-mailer" % "5.0.0"
  }

  object Akka {
    private[this] val version = "2.5.16"
    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val remote = "com.typesafe.akka" %% "akka-remote" % version
    val logging = "com.typesafe.akka" %% "akka-slf4j" % version
    val cluster = "com.typesafe.akka" %% "akka-cluster" % version
    val clusterMetrics = "com.typesafe.akka" %% "akka-cluster-metrics" % version
    val clusterTools = "com.typesafe.akka" %% "akka-cluster-tools" % version
    val testkit = "com.typesafe.akka" %% "akka-testkit" % version % "test"
  }

  object Serialization {
    val circeVersion = "0.9.3"
    val circeProjects = Seq("circe-core", "circe-generic", "circe-generic-extras", "circe-parser", "circe-java8")
  }

  object WebJars {
    val fontAwesome = "org.webjars" % "font-awesome" % "4.7.0"
    val jquery = "org.webjars" % "jquery" % "2.2.4"
    val materialize = "org.webjars" % "materializecss" % "0.100.2"
  }

  object Metrics {
    val version = "3.2.2"
    val metrics = "nl.grons" %% "metrics-scala" % "3.5.6"
    val jvm = "io.dropwizard.metrics" % "metrics-jvm" % version
    val ehcache = "io.dropwizard.metrics" % "metrics-ehcache" % version intransitive()
    val healthChecks = "io.dropwizard.metrics" % "metrics-healthchecks" % version intransitive()
    val json = "io.dropwizard.metrics" % "metrics-json" % version
    val jettyServlet = "org.eclipse.jetty" % "jetty-servlet" % "9.3.16.v20170120"
    val servlets = "io.dropwizard.metrics" % "metrics-servlets" % version intransitive()
    val graphite = "io.dropwizard.metrics" % "metrics-graphite" % version intransitive()
  }

  object Utils {
    val scapegoatVersion = "1.3.8"
    val enumeratumVersion = "1.5.17"

    val commonsIo = "commons-io" % "commons-io" % "2.6"
    val commonsLang = "org.apache.commons" % "commons-lang3" % "3.8"
    val enumeratum = "com.beachape" %% "enumeratum-circe" % enumeratumVersion
    val scalaGuice = "net.codingwell" %% "scala-guice" % "4.2.1"
    val betterFiles = "com.github.pathikrit" %% "better-files" % "3.6.0"
    val scopts = "com.github.scopt" %% "scopt" % "3.7.0"
  }
}
