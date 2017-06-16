package models.parse

object ProjectDefinition {
  def normalize(key: String) = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "").replaceAllLiterally("_", "")

  def fromJson(dir: better.files.File) = {
    val file = dir / "project.json"
    val original = file.contentAsString
    val depsReplaced = if (original.contains("dependencies")) {
      original
    } else {
      original.replaceAllLiterally("}", ",  \"dependencies\": []\n}")
    }
    val buildVerReplaced = if (depsReplaced.contains("buildVersion")) {
      depsReplaced
    } else {
      depsReplaced.replaceAllLiterally("}", ",  \"buildVersion\": \"1.0.1\"\n}")
    }
    upickle.default.read[ProjectDefinition](buildVerReplaced)
  }
}

case class ProjectDefinition(
    key: String,
    name: String,
    url: String,
    version: String,
    authors: String,
    buildVersion: String = "1.0.1",
    dependencies: Seq[String] = Nil
) {
  val keyNormalized = ProjectDefinition.normalize(key)

  val description = s"Scala.js facades for $name $version."

  private[this] val dependencyString = if (dependencies.isEmpty) {
    ""
  } else {
    ", " + dependencies.map(d => s""""com.definitelyscala" %%% "scala-js-$d" % "$buildVersion"""").mkString(", ")
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
