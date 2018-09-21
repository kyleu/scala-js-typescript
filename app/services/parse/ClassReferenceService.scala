package services.parse

import utils.Logging

object ClassReferenceService extends Logging {
  private[this] val files = Seq(
    ("scala.scalajs.js", Seq("package.scala"), Seq("@js.native")),

    ("com.definitelyscala.jqueryui.JQuery", Seq("jqueryui/JQueryUI/"), Seq(" JQuery")),

    ("com.definitelyscala.less.LessStatic", Seq("less/Less/"), Seq("LessStatic")),

    ("com.definitelyscala.awssdk._", Seq(
      "/AutoScaling/", "/SQS/", "/SES/", "/sts/", "/sts/"), Seq("ClientConfig", "Credentials", "Tags", "BlockDeviceMapping", "StepAdjustment")),

    ("com.definitelyscala.pixijs._", Seq(
      "/accessibility", "/CanvasTinter", "/core", "/extras", "/extract", "/filters", "/glCore", "/GroupD8", "/interaction", "/loaders",
      "/mesh", "/particles", "/prepare", "/utils", "pixijs/package.scala"), Seq(
        "CanvasRenderer", "Container", "DisplayObject", " Filter", "IDecomposedDataUri", "IHitArea",
        " Matrix", "ObjectRenderer", "Point", " Texture", "[Texture", "WebGLRenderer", "WebGLState")),
    ("com.definitelyscala.pixijs.extras._", Seq("pixijs/package.scala"), Seq("@js.native")),
    ("com.definitelyscala.pixijs.glCore._", Seq("pixijs/package.scala"), Seq("@js.native")),

    ("com.definitelyscala.asciify._", Seq("Asciify.scala"), Seq("AsciifyCallback")),

    ("com.definitelyscala.react.React._", Seq("/react"), Seq("ReactNode", "Ref[T]", "CSSWideKeyword")),

    ("com.definitelyscala.phaser._", Seq("/Physics/", "/Particles/", "/Plugin/", "/Utils/", "/Filter/"), Seq(
      "Physics.", "Particles.", " Plugin", " Game", " Filter")),
    ("com.definitelyscala.phaserp2._", Seq("/Physics/P2"), Seq("p2.")),

    ("com.definitelyscala.materializecss.Materialize._", Seq("/Materialize"), Seq("options: CollapsibleOptions")),

    ("com.definitelyscala.node.NodeJS._", Seq("node/"), Seq("ReadWriteStream")),
    ("com.definitelyscala.node.crypto.Crypto._", Seq("node/"), Seq("Utf8AsciiBinaryEncoding", "HexBase64Latin1Encoding", "ECDHKeyFormat")),
    ("com.definitelyscala.node.NodeJS", Seq("node/"), Seq("NodeJS.")),
    ("com.definitelyscala.node.net", Seq("node/"), Seq("extends net.")),
    ("com.definitelyscala.node.events.{ internal => events }", Seq("node/"), Seq(" events.")),
    ("com.definitelyscala.node.http", Seq("node/"), Seq(" http.")),
    ("com.definitelyscala.node.net", Seq("node/"), Seq(" net.")),
    ("com.definitelyscala.node.readline", Seq("node/"), Seq(" readline.")),
    ("com.definitelyscala.node.stream.{ internal => stream }", Seq("node/"), Seq("extends stream.")),
    ("com.definitelyscala.node.tls", Seq("node/"), Seq(" tls.")),
    ("com.definitelyscala.node.ErrnoException", Seq("node/"), Seq("ErrnoException")),
    ("com.definitelyscala.node.WritableStream", Seq("node/"), Seq("WritableStream")),
    ("com.definitelyscala.node.path.ParsedPath", Seq("node/"), Seq("ParsedPath")),
    ("com.definitelyscala.node.v8.V8.DoesZapCodeSpaceFlag", Seq("node/"), Seq("DoesZapCodeSpaceFlag")))

  private[this] val refs = Seq(
    "org.scalajs.dom.raw._" -> Seq(
      " HTMLElement", " Event", "[Event", " Element", "[Element", "HTMLImageElement", "HTMLCanvasElement", "HTMLVideoElement",
      "WebGLRenderingContext", "CanvasPattern", "WebGLBuffer", "CanvasRenderingContext2D", "XMLHttpRequest", "SVGPathElement", "StyleMedia",
      "SVGElement", "DataTransfer", " Node", "WebGLFramebuffer", "MouseEvent", "KeyboardEvent"),
    "com.definitelyscala.node.Buffer" -> Seq(": Buffer"),
    "scala.scalajs.js.|" -> Seq(" | "),
    "scala.scalajs.js.Date" -> Seq(": Date", " Date "),
    "org.scalajs.dom.raw.Blob" -> Seq(" Blob "),
    "org.scalajs.css.Css.AtRule" -> Seq(" AtRule"),
    "scala.scalajs.js.typedarray._" -> Seq(" ArrayBuffer", "Uint32Array", "Float32Array", "Uint16Array", "Uint8Array"),
    "scala.scalajs.js.Promise" -> Seq(" Promise"))

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
