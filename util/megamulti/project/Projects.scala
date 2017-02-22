import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object Projects {
  private[this] val all = Seq("")
  private[this] val one = Seq("a", "b", "c", "d", "e", "f", "g", "h")
  private[this] val two = Seq("i", "j", "k", "l", "m", "n", "o")
  private[this] val three = Seq("p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")

  private[this] def check(n: String, seq: Seq[String]) = {
    val key = n.stripPrefix("scala-js-")
    seq.exists(key.startsWith)
  }

  lazy val megamulti = {
    val proj = Project(id = Shared.projectId, base = file(".")).settings(Shared.commonSettings ++ Seq(
      libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % Dependencies.ScalaJS.domVersion),
      persistLauncher := false,
      scalaJSStage in Global := FastOptStage
    )).enablePlugins(ScalaJSPlugin)

    val root = file("../../data/projects")

    val allowed = Option(System.getProperty("stripe")) match {
      case Some(s) => s match {
        case "all" => all
        case "1" => one
        case "2" => two
        case "3" => three
        case x => throw new IllegalStateException(s"Unhandled stripe [$x].")
      }
      case None => throw new IllegalStateException(s"Missing [stripe] system property.")
    }

    val keys = root.listFiles.filter(_.isDirectory).filterNot(_.name.startsWith("_")).map(_.getName).filter(d => check(d, allowed))
    keys.foldLeft(proj) { (proj, key) =>
      val child = RootProject(file(s"../../data/projects/$key"))
      println(key + " -> " + child)
      proj.dependsOn(child)
    }
  }
}
