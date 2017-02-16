package models.parse.sc.tree

trait JSNameable extends Symbol {
  var jsName: Option[String] = None

  def protectName(): Unit = {
    val n = name.name
    if (jsName.isEmpty && (n.contains("$") || n == "apply")) {
      jsName = Some(n)
    }
  }

  protected def jsNameStr = jsName.fold("")(n => s"""@JSName("$n") """)
}
