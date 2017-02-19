import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object Projects {
  lazy val megasingle = Project(id = Shared.projectId, base = file(".")).settings(Shared.commonSettings ++ Seq(
    libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % Dependencies.ScalaJS.domVersion),
    persistLauncher := false,
    scalaJSStage in Global := FastOptStage
  )).enablePlugins(ScalaJSPlugin)
}
