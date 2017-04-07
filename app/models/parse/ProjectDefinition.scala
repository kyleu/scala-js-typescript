package models.parse

object ProjectDefinition {
  def normalize(key: String) = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "").replaceAllLiterally("_", "")

  def fromJson(dir: better.files.File) = {
    val file = dir / "project.json"
    val content = file.contentAsString
    val whatever = if (content.contains("dependencies")) {
      if (content.contains("buildVersion")) {
        content
      } else {
        content.replaceAllLiterally("}", ",  \"buildVersion\": \"1.0.0\"\n}")
      }
    } else {
      if (content.contains("buildVersion")) {
        content.replaceAllLiterally("}", ",  \"dependencies\": []\n}")
      } else {
        content.replaceAllLiterally("}", ",  \"dependencies\": []\n,  \"buildVersion\": \"1.0.0\"\n}")
      }
    }
    upickle.default.read[ProjectDefinition](whatever)
  }
}

case class ProjectDefinition(
    key: String,
    name: String,
    url: String,
    version: String,
    authors: String,
    buildVersion: String = "1.0.0",
    dependencies: Seq[String] = Nil
) {
  val keyNormalized = ProjectDefinition.normalize(key)

  val description = s"Scala.js facades for $name $version."

  private[this] val dependencyString = if (dependencies.isEmpty) {
    ""
  } else {
    ", " + dependencies.map(d => s""""com.definitelyscala" %%% "scala-js-$d" % "1.0.0"""").mkString(", ")
  }

  private[this] val dependencySummary = if (dependencies.isEmpty) {
    ""
  } else {
    s"\nThis project depends on ${dependencies.map(d => s"`scala-js-$d`").mkString(", ")}.\n"
  }

  val asMap = Map(
    "key" -> key,
    "keyNormalized" -> keyNormalized,
    "name" -> name,
    "url" -> url,
    "version" -> version,
    "authors" -> authors,
    "dependencies" -> dependencyString,
    "buildVersion" -> buildVersion,
    "dependencySummary" -> dependencySummary
  )
}
