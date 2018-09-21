package utils

import org.slf4j.LoggerFactory

trait Logging {
  protected[this] val log = LoggerFactory.getLogger(s"${utils.Config.projectId}.${this.getClass.getSimpleName.replace("$", "")}")
}
