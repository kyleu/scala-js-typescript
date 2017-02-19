import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object Projects {
  lazy val megamulti = {
    val proj = Project(id = Shared.projectId, base = file(".")).settings(Shared.commonSettings ++ Seq(
      libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % Dependencies.ScalaJS.domVersion),
      persistLauncher := false,
      scalaJSStage in Global := FastOptStage
    )).enablePlugins(ScalaJSPlugin)

    val root = file("../../data/projects")
    val keys = root.listFiles.filter(_.isDirectory).filterNot(_.name.startsWith("_")).map(_.getName)
    keys.foldLeft(proj) { (proj, key) =>
      val child = RootProject(file(s"../../data/projects/$key"))
      println(key + " -> " + child)
      proj.dependsOn(child)
    }
  }
}
