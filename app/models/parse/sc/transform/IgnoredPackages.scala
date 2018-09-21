package models.parse.sc.transform

object IgnoredPackages {
  def forKey(key: String) = pkgs.getOrElse(key, Set.empty)

  val pkgs = Map(
    "accounting" -> Set("accounting"),
    "alertify" -> Set("alertify"),
    "algoliasearch" -> Set("algoliasearch"),
    "amcharts" -> Set("AmCharts"),
    "amplify" -> Set("amplify"),
    "atmosphere" -> Set("Atmosphere"),
    "bigint" -> Set("BigInt"),
    "busboy" -> Set("busboy"),
    "canvasjs" -> Set("CanvasJS"),
    "gamepad" -> Set("Gamepad"),
    "jqueryui" -> Set("JQueryUI"),
    "less" -> Set("Less"),
    "materializecss" -> Set("Materialize"),
    "matterjs" -> Set("Matter"),
    "node" -> Set("NodeJS"),
    "phaser" -> Set("Phaser"),
    "phaserpixi" -> Set("PIXI"),
    "pixijs" -> Set("PIXI"),
    "react" -> Set("React"),
    "test" -> Set("excluded"),
    "" -> Set(""))
}
