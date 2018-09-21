package services.git

import better.files._
import services.file.FileService
import utils.Logging

object GitService extends Logging {
  def init(dir: File) = if ((dir / ".git").exists) {
    throw new IllegalStateException("Already initialized.")
  } else {
    call(dir, Seq("init"))
  }

  def reset(dir: File) = call(dir, Seq("reset", "--hard", "origin/master"))

  def status(dir: File) = call(dir, Seq("status", "--short", "--ignore-submodules"))

  def commitCount(dir: File) = {
    call(dir, Seq("log", "--pretty=oneline"))._2.split("\\n").toList match {
      case Nil => -1
      case h :: Nil if h.contains("does not have") => 0
      case h :: Nil if h.contains("or any of the parent") => -1
      case x => x.length
    }
  }

  def addRemote(dir: File) = if ((dir / ".git").exists) {
    call(dir, Seq("remote", "add", "origin", s"git@github.com:DefinitelyScala/${dir.name}.git"))
  } else {
    throw new IllegalStateException(s"No git repo available for [${dir.name}].")
  }

  private[this] val firstCommitFiles = Seq(
    ".gitignore", "build.sbt", "readme.md", "scalastyle-config.xml",
    "project/Projects.scala", "project/build.properties", "project/plugins.sbt")
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

  def commit(dir: File, files: Seq[String], message: String) = {
    if (files.isEmpty) {
      call(dir, Seq("add", "."))
    } else {
      files.foreach(f => call(dir, Seq("add", f)))
    }
    call(dir, Seq("commit", "-m", message))
  }

  def push(dir: File) = call(dir, Seq("push", "origin", "master"))

  def cloneRepo(dir: File, key: String) = {
    call(dir, Seq("clone", s"git@github.com:DefinitelyScala/$key.git"))
  }

  private[this] def call(dir: File, cmd: Seq[String]) = if (dir.exists) {
    log.info(s"Calling [git] with arguments [${cmd.mkString("  ")}]")
    val f = FileService.getDir("logs") / "git" / (dir.name + ".log")
    if (f.exists) {
      f.delete()
    }
    val result = new ProcessBuilder().directory(dir.toJava).command("git" +: cmd: _*).redirectError(f.toJava).redirectOutput(f.toJava).start().waitFor()
    result -> f.contentAsString
  } else {
    -1 -> s"Missing directory [$dir]."
  }
}
