package models.parse.sc

object ExcludedMembers {
  def check(key: String, cls: String, member: String) = {
    val matchKey = exclusions.get(key).exists(_.contains(member))
    val matchMember = exclusions.get(cls).exists(_.contains(member))
    !matchKey && !matchMember
  }

  private[this] val exclusions = Map(
    // JQuery Overrides
    "BaseJQueryEventObject" -> Set("currentTarget", "preventDefault", "stopImmediatePropagation", "stopPropagation", "target"),
    "JQueryInputEventObject" -> Set("metaKey", "pageX", "pageY"),

    // Pixi Overrides
    "pixi.js" -> Set("clone"),
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
      "stroke", "strokeThickness", "styleID", "textBaseline", "wordWrap", "wordWrapWidth"
    ),
    "Texture" -> Set("on", "once"),
    "TilingSprite" -> Set(
      "_width", "_height", "_onTextureUpdate", "_renderWebGL", "_renderCanvas", "_calculateBounds", "getLocalBounds", "containsPoint", "width", "height"
    ),
    "TransformStatic" -> Set("updateLocalTransform", "updateTransform"),
    "VideoBaseTexture" -> Set("update", "destroy", "source"),
    "VoidFilter" -> Set("glShaderKey"),
    "WebGLRenderer" -> Set("_backgroundColorRgba", "destroy", "resize", "on", "once")

  )
}
