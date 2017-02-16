package org.scalajs.tools.tsimporter.sc

import scala.collection.mutable

class TypeAliasSymbol(nme: Name) extends Symbol(nme) {
  val tparams = new mutable.ListBuffer[TypeParamSymbol]
  var alias: TypeRef = TypeRef.Any

  override def toString = s"type $name" + (if (tparams.isEmpty) { "" } else { tparams.mkString("<", ", ", ">") })
}
