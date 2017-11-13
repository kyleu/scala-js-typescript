package services.file

import better.files._

object FileService {
  private[this] var dataDir: Option[File] = None

  def setRootDir(d: File) = {
    dataDir = Some(d)
    if (!d.exists) {
      d.createDirectory()
    }
    if (!d.isDirectory) {
      throw new IllegalStateException(s"Cannot load data directory [$d], as it is a file.")
    }
  }

  def getDir(name: String, createIfMissing: Boolean = true) = {
    val d = dataDir.getOrElse(throw new IllegalStateException("File service not initialized.")) / name
    if (createIfMissing && (!d.exists)) {
      d.createDirectory()
    }
    d
  }
}
