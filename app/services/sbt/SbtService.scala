package services.sbt

import better.files._
import services.file.FileService
import utils.{DateUtils, Logging}

object SbtService extends Logging {
  val jarpathTest = "/Users/kyle/Projects/Libraries/sbt-0.13/sbt-launch.jar".toFile
  val jarpath = if (jarpathTest.exists) {
    jarpathTest
  } else {
    "C:\\Users\\kyleu\\Projects\\Libraries\\sbt\\sbt-launch.jar".toFile
  }

  def clean(dir: File) = if (dir.exists) {
    sbt(dir, "clean")
  } else {
    throw new IllegalStateException(s"No SBT project available for [${dir.name}].")
  }

  def format(dir: File) = if (dir.exists) {
    sbt(dir, "scalariformFormat")
  } else {
    throw new IllegalStateException(s"No SBT project available for [${dir.name}].")
  }

  def build(dir: File) = if (dir.exists) {
    sbt(dir, "+clean", "+compile", "+doc", "+publishLocal")
  } else {
    throw new IllegalStateException(s"No SBT project available for [${dir.name}].")
  }

  def publish(dir: File) = {
    if (dir.exists) {
      sbt(dir, "+publish")
    } else {
      throw new IllegalStateException(s"No SBT project available for [${dir.name}].")
    }
  }

  private[this] def sbt(dir: File, cmd: String*) = {
    call(dir, Seq("java", "-Dsbt.log.noformat=true", "-jar", jarpath.pathAsString) ++ cmd)
  }

  private[this] def call(dir: File, cmd: Seq[String]) = {
    log.info(s"Running build for [${dir.name}] with arguments [${cmd.mkString(", ")}]...")
    val startMs = System.currentTimeMillis
    val logDir = FileService.getDir("logs") / "sbt"
    if (!logDir.exists) {
      logDir.createDirectory
    }
    val f = logDir / (dir.name + ".log")
    if (f.exists) {
      f.delete()
    }
    f.appendText(s"Scala.js Build Started [${DateUtils.niceDateTime(DateUtils.now)}]\n")
    val result = new ProcessBuilder().directory(dir.toJava).command(cmd: _*).redirectError(f.toJava).redirectOutput(f.toJava).start().waitFor()
    log.info(s"Build completed in [${System.currentTimeMillis - startMs}ms] with result [$result] for [${dir.name}].")
    result -> f.contentAsString
  }
}
