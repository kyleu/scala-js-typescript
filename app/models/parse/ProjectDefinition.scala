package models.parse

case class ProjectDefinition(key: String, name: String, url: String, version: String, authors: String) {
  val keyNormalized = key.replaceAllLiterally("-", "").replaceAllLiterally(".", "").replaceAllLiterally("_", "")

  val description = s"Scala.js facades for $name $version."

  val asMap = Map(
    "key" -> key,
    "keyNormalized" -> keyNormalized,
    "name" -> name,
    "url" -> url,
    "version" -> version,
    "authors" -> authors,
    "dependencies" -> ""
  )
}
