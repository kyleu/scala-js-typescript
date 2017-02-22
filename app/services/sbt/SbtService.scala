package services.sbt

import better.files._
import services.file.FileService
import utils.Logging

object SbtService extends Logging {
  def build(dir: File) = if (dir.exists) {
    call(dir, Seq("java", "-Dsbt.log.noformat=true", "-jar", "/Users/kyle/Projects/Libraries/sbt-0.13/sbt-launch.jar", "compile"))
  } else {
    throw new IllegalStateException(s"No SBT project available for [${dir.name}].")
  }

  private[this] def call(dir: File, cmd: Seq[String]) = {
    log.info(s"Running build for [${dir.name}]...")
    val startMs = System.currentTimeMillis
    val f = FileService.getDir("logs") / "sbt" / (dir.name + ".log")
    if (f.exists) {
      f.delete()
    }
    val result = new ProcessBuilder().directory(dir.toJava).command(cmd: _*).redirectError(f.toJava).redirectOutput(f.toJava).start().waitFor()
    log.info(s"Build completed in [${System.currentTimeMillis - startMs}ms] with result [$result] for [${dir.name}].")
    result -> f.contentAsString
  }
}
