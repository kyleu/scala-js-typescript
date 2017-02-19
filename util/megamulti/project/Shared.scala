import com.typesafe.sbt.SbtScalariform.{ ScalariformKeys, scalariformSettings }
import sbt.Keys._
import sbt._

object Shared {
  val projectId = "megamulti"
  val projectName = "megamulti"

  lazy val commonSettings = Seq(
    version := Shared.Versions.app,
    scalaVersion := Shared.Versions.scala,

    scalacOptions ++= Seq(
      "-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked", "–Xcheck-null", "-Xfatal-warnings", "-Xlint",
      "-Ywarn-adapted-args", /* "-Ywarn-dead-code", */ "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-numeric-widen"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos"),

    publishMavenStyle := false,

    shellPrompt := { state => s"[${Project.extract(state).currentProject.id}] $$ " },

    // Code Quality
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
  ) ++ scalariformSettings

  object Versions {
    val app = "1.0.0"
    val scala = "2.11.8"
  }
}
