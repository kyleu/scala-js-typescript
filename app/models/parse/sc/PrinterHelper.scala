package models.parse.sc

object PrinterHelper {
  class ListElemSeparator(val s: String) extends AnyVal

  object ListElemSeparator {
    val Comma = new ListElemSeparator(", ")
    val WithKeyword = new ListElemSeparator(" with ")
  }

  implicit class OutputHelper(val sc: StringContext) extends AnyVal {
    def p(args: Any*)(implicit printer: Printer, sep: ListElemSeparator = ListElemSeparator.Comma) {
      val strings = sc.parts.iterator
      val expressions = args.iterator

      val output = printer.files.output
      output.print(strings.next())
      while (strings.hasNext) {
        expressions.next() match {
          case seq: Seq[_] =>
            val iter = seq.iterator
            if (iter.hasNext) {
              printer.print(iter.next())
              while (iter.hasNext) {
                output.print(sep.s)
                printer.print(iter.next())
              }
            }

          case expr => printer.print(expr)
        }
        output.print(strings.next())
      }
    }

    def pln(args: Any*)(implicit printer: Printer, sep: ListElemSeparator = ListElemSeparator.Comma) {
      p(args: _*)
      printer.files.output.println()
    }
  }
}
