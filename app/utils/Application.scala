package utils

import java.util.TimeZone

import org.joda.time.DateTimeZone
import play.api.Environment
import play.api.inject.ApplicationLifecycle
import play.api.libs.ws.WSClient
import services.file.FileService

import scala.concurrent.Future

object Application {
  var initialized = false
}

@javax.inject.Singleton
class Application @javax.inject.Inject() (
  val config: Config,
  val lifecycle: ApplicationLifecycle,
  val playEnv: Environment,
  val ws: WSClient) extends Logging {
  if (Application.initialized) {
    log.info("Skipping initialization after failure.")
  } else {
    start()
  }

  private[this] def start() = {
    log.info(s"${Config.projectName} is starting.")
    Application.initialized = true

    DateTimeZone.setDefault(DateTimeZone.UTC)
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    lifecycle.addStopHook(() => Future.successful(stop()))

    FileService.setRootDir(config.dataDir)
  }

  private[this] def stop() = {}
}
