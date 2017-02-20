package models.parse.sc.transform

object IgnoredPackages {
  def forKey(key: String) = pkgs.getOrElse(key, Set.empty)

  val pkgs = Map(
    "gamepad" -> Set("Gamepad"),
    "test" -> Set("excluded"),
    "" -> Set("")
  )
}
