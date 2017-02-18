package models.parse.sc.transform

object Replacements {
  sealed trait Rule {
    def replace(lines: Seq[String]): Seq[String]
  }

  object Rule {
    case class AddLine(tgt: String) extends Rule {
      override def replace(lines: Seq[String]) = lines
    }
    case class RemoveLine(tgt: String) extends Rule {
      override def replace(lines: Seq[String]) = lines.filterNot(_ == tgt)
    }
    case class ReplaceLine(src: String, tgt: String) extends Rule {
      override def replace(lines: Seq[String]) = lines.filterNot(_ == tgt)
    }
  }

  def toRules(lines: Seq[String]) = {
    lines.zipWithIndex.foldLeft(Seq.empty[Rule]) { (rules, y) => y._1 match {
      case "+" => rules :+ Rule.AddLine(lines(y._2 + 1))
      case "-" => rules :+ Rule.RemoveLine(lines(y._2 + 1))
      case "=" => rules :+ Rule.ReplaceLine(lines(y._2 + 1), lines(y._2 + 2))
      case _ => rules
    } }
  }
}

case class Replacements(key: String, rules: Seq[Replacements.Rule]) {
  def replace(lines: Seq[String]) = rules.foldLeft(lines) { (l, r) => r.replace(l)}
}
