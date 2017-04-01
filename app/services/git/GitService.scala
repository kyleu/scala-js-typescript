package services.git

import better.files._
import services.file.FileService

object GitService {
  def init(dir: File) = if ((dir / ".git").exists) {
    throw new IllegalStateException("Already initialized.")
  } else {
    call(dir, Seq("init"))
  }

  def status(dir: File) = {
    call(dir, Seq("status"))
  }

  def addRemote(dir: File) = if ((dir / ".git").exists) {
    call(dir, Seq("remote", "add", "origin", s"git@github.com:DefinitelyScala/${dir.name}.git"))
  } else {
    throw new IllegalStateException(s"No git repo available for [${dir.name}].")
  }

  private[this] val firstCommitFiles = Seq(
    ".gitignore", "build.sbt", "readme.md", "scalastyle-config.xml",
    "project/Projects.scala", "project/build.properties", "project/plugins.sbt"
  )
  def firstCommit(dir: File) = {
    call(dir, Seq("add") ++ firstCommitFiles)
    call(dir, Seq("commit", "-m", "Initial project structure."))
    call(dir, Seq("push", "origin", "master"))
  }

  def secondCommit(dir: File) = {
    call(dir, Seq("add", "src/*"))
    call(dir, Seq("commit", "-m", "Scala.js facade."))
    call(dir, Seq("push", "origin", "master"))
  }

  private[this] val thirdCommitFiles = Seq(".travis.yml")
  def thirdCommit(dir: File) = {
    call(dir, Seq("add") ++ thirdCommitFiles)
    call(dir, Seq("commit", "-m", "TravisCI integration."))
    call(dir, Seq("push", "origin", "master"))
  }

  def pushUpdate(dir: File, files: Seq[String], message: String) = {
    if (files.isEmpty) {
      call(dir, Seq("add", "."))
    } else {
      files.foreach(f => call(dir, Seq("add", f)))
    }
    call(dir, Seq("commit", "-m", message))
    call(dir, Seq("push", "origin", "master"))
  }

  def cloneRepo(dir: File, key: String) = {
    call(dir, Seq("clone", s"git@github.com:DefinitelyScala/$key.git"))
  }

  private[this] def call(dir: File, cmd: Seq[String]) = {
    val f = FileService.getDir("logs") / "git" / (dir.name + ".log")
    if (f.exists) {
      f.delete()
    }
    val result = new ProcessBuilder().directory(dir.toJava).command("git" +: cmd: _*).redirectError(f.toJava).redirectOutput(f.toJava).start().waitFor()
    result -> f.contentAsString
  }
}
