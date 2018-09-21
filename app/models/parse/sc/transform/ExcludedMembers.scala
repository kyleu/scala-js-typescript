package models.parse.sc.transform

import enumeratum._

object ExcludedMembers {
  sealed abstract class Exclusions(val key: String, val global: Set[String] = Set.empty, val files: Map[String, Set[String]]) extends EnumEntry {
    override def toString = key
  }

  object Exclusions extends Enum[Exclusions] {
    case object AmCharts extends Exclusions("amcharts", files = Map(
      "AmCoordinateChart" -> Set("addListener"), "AmPieChart" -> Set("addListener"), "ValueAxis" -> Set("position", "addGuide", "removeGuide")))
    case object AWSSDK extends Exclusions("aws-sdk", files = Map("ClientConfig" -> Set("credentials", "region")))
    case object BigInteger extends Exclusions("big-integer", files = Map("BigInteger" -> Set("toString", "valueOf")))
    case object Blocks extends Exclusions("blocks", files = Map("BlocksArray" -> Set("update", "extend")))
    case object CanvasJS extends Exclusions("canvasjs", files = Map("ChartDataPoint" -> Set("legendMarkerColor")))
    case object JQuery extends Exclusions("jquery", files = Map(
      "BaseJQueryEventObject" -> Set("currentTarget", "preventDefault", "stopImmediatePropagation", "stopPropagation", "target"),
      "JQueryInputEventObject" -> Set("metaKey", "pageX", "pageY"),
      "JQueryMouseEventObject" -> Set("pageX", "pageY")))
    case object JQueryUI extends Exclusions("jqueryui", files = Map("DialogOptions" -> Set("open", "close")))
    case object PixiJS extends Exclusions("pixi.js", global = Set("clone"), files = Map(
      "BaseRenderTexture" -> Set("height", "width", "realHeight", "realWidth", "resolution", "scaleMode", "hasLoaded", "destroy", "once", "on", "off"),
      "BaseTexture" -> Set("on", "once"),
      "BitmapText" -> Set("updateTransform"),
      "BlurFilter" -> Set("resolution", "padding"),
      "BlurXFilter" -> Set("resolution"),
      "BlurYFilter" -> Set("resolution"),
      "CanvasRenderer" -> Set("destroy", "resize", "on", "once"),
      "CanvasSpriteRenderer" -> Set("destroy"),
      "Container" -> Set("updateTransform", "renderWebGL", "renderCanvas", "once", "on", "off"),
      "DisplayObject" -> Set("interactive", "buttonMode", "hitArea", "interactiveChildren", "defaultCursor", "on", "once", "destroy"),
      "FilterManager" -> Set("destroy"),
      "Graphics" -> Set("_renderCanvas", "_calculateBounds", "destroy"),
      "GraphicsRenderer" -> Set("destroy"),
      "Loader" -> Set("on", "once"),
      "Mesh" -> Set("_renderWebGL", "_renderCanvas", "_calculateBounds"),
      "NineSlicePlane" -> Set("width", "height"),
      "ParticleContainer" -> Set("interactiveChildren", "onChildrenChange", "destroy"),
      "ParticleRenderer" -> Set("start", "destroy"),
      "Plane" -> Set("drawMode"),
      "RenderTexture" -> Set("valid"),
      "Rope" -> Set("_onTextureUpdate", "updateTransform"),
      "Sprite" -> Set("width", "height", "_calculateBounds", "_renderWebGL", "_renderCanvas", "destroy"),
      "SpriteRenderer" -> Set("flush", "start", "stop", "destroy"),
      "StencilManager" -> Set("destroy"),
      "Text" -> Set("width", "height", "renderWebGL", "_renderCanvas", "_calculateBounds", "destroy"),
      "TextStyle" -> Set(
        "align", "breakWords", "dropShadow", "dropShadowAngle", "dropShadowBlur", "dropShadowColor", "dropShadowDistance", "fill", "fillGradientType",
        "fontFamily", "fontSize", "fontStyle", "fontVariant", "fontWeight", "letterSpacing", "lineHeight", "lineJoin", "miterLimit", "padding",
        "stroke", "strokeThickness", "styleID", "textBaseline", "wordWrap", "wordWrapWidth"),
      "Texture" -> Set("on", "once"),
      "TilingSprite" -> Set(
        "_width", "_height", "_onTextureUpdate", "_renderWebGL", "_renderCanvas", "_calculateBounds", "getLocalBounds", "containsPoint", "width", "height"),
      "TransformStatic" -> Set("updateLocalTransform", "updateTransform"),
      "VideoBaseTexture" -> Set("update", "destroy", "source"),
      "VoidFilter" -> Set("glShaderKey"),
      "WebGLRenderer" -> Set("_backgroundColorRgba", "destroy", "resize", "on", "once")))

    override val values = findValues
  }

  val badNames = Seq("toString", "clone", "notify")

  def check(key: String, cls: String, member: String) = Exclusions.withNameInsensitiveOption(key) match {
    case _ if badNames.contains(member) => false
    case Some(ex) =>
      val matchKey = ex.global(member)
      val matchMember = ex.files.get(cls).exists(_.contains(member))
      !(matchKey || matchMember)
    case None => true
  }
}
