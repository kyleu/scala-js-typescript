package models.parse

import better.files._
import models.parse.parser.tree._

import scala.util.parsing.input._
import parser.TSDefParser

object Main {
  def main(args: Array[String]) {
    if (args.length < 2) {
      Console.err.println("""
        |Usage: scalajs-ts-importer <input.d.ts> <output.scala> [<package>]
        |  <input.d.ts>     TypeScript type definition file to read
        |  <output.scala>   Output Scala.js file
        |  <package>        Package name for the output (defaults to "importedjs")
      """.stripMargin.trim)
      System.exit(1)
    }

    val inputFileName = args(0)
    val outputPath = args(1)
    val outputPackage = if (args.length > 2) args(2) else "importedjs"

    val definitions = parseDefinitions(readerForFile(inputFileName.toFile))

    //val output = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)))
    process(inputFileName, definitions, outputPath, outputPackage)
  }

  private def process(key: String, definitions: List[DeclTree], path: String, outputPackage: String) {
    new Importer(key, path)(definitions, outputPackage)
  }

  private def parseDefinitions(reader: Reader[Char]): List[DeclTree] = {
    val parser = new TSDefParser
    parser.parseDefinitions(reader) match {
      case parser.Success(rawCode: List[models.parse.parser.tree.DeclTree], _) => rawCode

      case parser.NoSuccess(msg, next) =>
        val m = s"Parse error at ${next.pos.toString}:" + "\n  " + msg + "\n  Position: " + next.pos.longString
        throw new IllegalStateException(m)
    }
  }

  private def readerForFile(file: File): Reader[Char] = new CharSequenceReader(file.contentAsString, 0)
}
