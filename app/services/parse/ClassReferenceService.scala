package services.parse

import utils.Logging

object ClassReferenceService extends Logging {
  private[this] val files = Seq(
    ("org.scalajs.jqueryui.JQuery", Seq("jqueryui/JQueryUI/"), Seq(" JQuery")),

    ("org.scalajs.less.LessStatic", Seq("less/Less/"), Seq("LessStatic")),

    ("org.scalajs.awssdk._", Seq(
      "/AutoScaling/", "/SQS/", "/SES/", "/sts/", "/sts/"
    ), Seq("ClientConfig", "Credentials", "Tags", "BlockDeviceMapping", "StepAdjustment")),

    ("org.scalajs.pixijs._", Seq(
      "/accessibility", "/CanvasTinter", "/core", "/extras", "/extract", "/filters", "/glCore", "/GroupD8", "/interaction", "/loaders",
      "/mesh", "/particles", "/prepare", "/utils", "pixijs/package.scala"
    ), Seq(
        "CanvasRenderer", "Container", "DisplayObject", " Filter", "IDecomposedDataUri", "IHitArea",
        " Matrix", "ObjectRenderer", "Point", " Texture", "[Texture", "WebGLRenderer", "WebGLState"
      )),

    ("org.scalajs.pixijs.extras._", Seq("pixijs/package.scala"), Seq("@js.native")),

    ("org.scalajs.pixijs.glCore._", Seq("pixijs/package.scala"), Seq("@js.native")),

    ("scala.scalajs.js", Seq("package.scala"), Seq("@js.native")),

    ("org.scalajs.asciify._", Seq("Asciify.scala"), Seq("AsciifyCallback")),

    ("org.scalajs.react.React._", Seq("/react"), Seq("ReactNode", "Ref[T]", "CSSWideKeyword")),

    ("org.scalajs.phaser._", Seq("/Physics", "/Particles", "/Plugin", "/Utils", "/Filter"), Seq(
      "Physics.", "Particles.", " Plugin", " Game", " Filter"
    )),

    ("org.scalajs.phaserp2._", Seq("/Physics/P2"), Seq("p2."))
  )

  private[this] val refs = Seq(
    "org.scalajs.dom.raw._" -> Seq(
      " HTMLElement", " Promise", " Event", "[Event", " Element", "[Element", "HTMLImageElement", "HTMLCanvasElement", "HTMLVideoElement",
      "WebGLRenderingContext", "CanvasPattern", "WebGLBuffer", "CanvasRenderingContext2D", "XMLHttpRequest", "SVGPathElement", "StyleMedia",
      "SVGElement", "DataTransfer", " Node", "WebGLFramebuffer", "MouseEvent", "KeyboardEvent"
    ),
    "scala.scalajs.js.|" -> Seq(" | "),
    "scala.scalajs.js.Date" -> Seq(": Date", " Date "),
    "org.scalajs.dom.raw.Blob" -> Seq(" Blob "),
    "org.scalajs.css.Css.AtRule" -> Seq(" AtRule"),
    "scala.scalajs.js.typedarray._" -> Seq(" ArrayBuffer", "Uint32Array", "Float32Array", "Uint16Array")
  )

  def insertImports(path: String, content: Seq[String]) = {
    var importIdx = content.indexOf("import scala.scalajs.js")
    if (importIdx < 0) {
      importIdx = 0
    }
    val fileImports = importsForPath(path, content).map("import " + _)
    val contentImports = importsForContent(content).map("import " + _)
    content.take(importIdx + 1) ++ fileImports ++ contentImports ++ content.drop(importIdx + 1)
  }

  private[this] def importsForContent(content: Seq[String]) = refs.flatMap { ref =>
    if (ref._2.exists(key => content.exists(_.contains(key)))) {
      Some(ref._1)
    } else {
      None
    }
  }

  private[this] def importsForPath(path: String, content: Seq[String]) = files.flatMap { file =>
    val fileMatch = file._2.exists(key => path.contains(key))
    if (fileMatch && file._3.exists(key => content.exists(_.contains(key)))) {
      Some(file._1)
    } else {
      None
    }
  }
}
