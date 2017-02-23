package services.git

import better.files._
import services.file.FileService

object GitService {
  def init(dir: File) = if ((dir / ".git").exists) {
    throw new IllegalStateException("Already initialized.")
  } else {
    call(dir, Seq("git", "init"))
  }

  def addRemote(dir: File) = if ((dir / ".git").exists) {
    call(dir, Seq("git", "remote", "add", "origin", s"git@github.com:DefinitelyScala/${dir.name}.git"))
  } else {
    throw new IllegalStateException(s"No git repo available for [${dir.name}].")
  }

  private[this] val firstCommitFiles = Seq(
    ".gitignore", "build.sbt", "readme.md", "scalastyle-config.xml",
    "project/Projects.scala", "project/build.properties", "project/plugins.sbt"
  )
  def firstCommit(dir: File) = {
    call(dir, Seq("git", "add") ++ firstCommitFiles)
    call(dir, Seq("git", "commit", "-m", "Initial project structure."))
    call(dir, Seq("git", "push", "origin", "master"))
  }

  def secondCommit(dir: File) = {
    call(dir, Seq("git", "add", "src/*"))
    call(dir, Seq("git", "commit", "-m", "Scala.js facade."))
    call(dir, Seq("git", "push", "origin", "master"))
  }

  private[this] val thirdCommitFiles = Seq(".travis.yml")
  def thirdCommit(dir: File) = {
    call(dir, Seq("git", "add") ++ thirdCommitFiles)
    call(dir, Seq("git", "commit", "-m", "TravisCI integration."))
    call(dir, Seq("git", "push", "origin", "master"))
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
