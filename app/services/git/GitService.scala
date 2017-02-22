package services.git

import better.files._
import services.file.FileService

object GitService {
  def addRemote(dir: File) = if ((dir / ".git").exists) {
    call(dir, Seq("git", "remote", "add", "origin", s"git@github.com:DefinitelyScala/${dir.name}.git"))
  } else {
    throw new IllegalStateException(s"No git repo available for [${dir.name}].")
  }

  def init(dir: File) = if ((dir / ".git").exists) {
    throw new IllegalStateException("Already initialized.")
  } else {
    call(dir, Seq("git", "init"))
  }

  private[this] def call(dir: File, cmd: Seq[String]) = {
    val f = FileService.getDir("logs") / "git" / (dir.name + ".log")
    if (f.exists) {
      f.delete()
    }
    val result = new ProcessBuilder().directory(dir.toJava).command(cmd: _*).redirectError(f.toJava).redirectOutput(f.toJava).start().waitFor()
    result -> f.contentAsString
  }
}
