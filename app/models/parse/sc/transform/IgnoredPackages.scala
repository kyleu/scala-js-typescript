package models.parse.sc.transform

object IgnoredPackages {
  def forKey(key: String) = pkgs.getOrElse(key, Set.empty)

  val pkgs = Map(
    "amcharts" -> Set("AmCharts"),
    "atmosphere" -> Set("Atmosphere"),
    "bigint" -> Set("BigInt"),
    "canvasjs" -> Set("CanvasJS"),
    "gamepad" -> Set("Gamepad"),
    "jqueryui" -> Set("JQueryUI"),
    "less" -> Set("Less"),
    "materialize-css" -> Set("Materialize"),
    "matter-js" -> Set("Matter"),
    "pixi.js" -> Set("PIXI"),
    "react" -> Set("React"),
    "test" -> Set("excluded"),
    "" -> Set("")
  )
}
