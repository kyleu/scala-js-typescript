package models.parse.sc.transform

object IgnoredPackages {
  def forKey(key: String) = pkgs.getOrElse(key, Set.empty)

  val pkgs = Map(
    "accounting" -> Set("accounting"),
    "alertify" -> Set("alertify"),
    "algoliasearch" -> Set("algoliasearch"),
    "amcharts" -> Set("AmCharts"),
    "atmosphere" -> Set("Atmosphere"),
    "bigint" -> Set("BigInt"),
    "busboy" -> Set("busboy"),
    "canvasjs" -> Set("CanvasJS"),
    "gamepad" -> Set("Gamepad"),
    "jqueryui" -> Set("JQueryUI"),
    "less" -> Set("Less"),
    "materialize-css" -> Set("Materialize"),
    "matter-js" -> Set("Matter"),
    "phaser" -> Set("Phaser"),
    "pixi.js" -> Set("PIXI"),
    "react" -> Set("React"),
    "test" -> Set("excluded"),
    "" -> Set("")
  )
}
