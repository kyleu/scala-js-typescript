package services.git

import better.files._
import org.eclipse.jgit.api.InitCommand
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

object GitService {
  private[this] def gitFor(dir: File) = new FileRepositoryBuilder().setGitDir(dir.toJava).readEnvironment().build()

  def addRemote(dir: File) = {
    val git = gitFor(dir)
    val cfg = git.getConfig
    cfg.setString("remote", "origin", "url", s"git@github.com:DefinitelyScala/${dir.name}.git")
    cfg.save()
    git
  }

  def init(dir: File) = {
    new InitCommand().setDirectory(dir.toJava).call()
  }
}
