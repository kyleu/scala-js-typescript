package models.parse.sc.tree

case class TypeRef(typeName: QualifiedName, targs: List[TypeRef] = Nil) {
  override def toString = s"$typeName[${targs.mkString(", ")}]"
}

object TypeRef {
  import QualifiedName.{ java_lang, scala, scala_js }

  val ScalaAny = TypeRef(scala dot Name("Any"))

  val Any = TypeRef(scala_js dot Name("Any"))
  val Dynamic = TypeRef(scala_js dot Name("Dynamic"))
  val Double = TypeRef(scala dot Name("Double"))
  val Boolean = TypeRef(scala dot Name("Boolean"))
  val String = TypeRef(java_lang dot Name("String"))
  val Object = TypeRef(scala_js dot Name("Object"))
  val Function = TypeRef(scala_js dot Name("Function"))
  val Unit = TypeRef(scala dot Name("Unit"))
  val Null = TypeRef(scala dot Name("Null"))

  object Union {
    def apply(left: TypeRef, right: TypeRef): TypeRef = TypeRef(QualifiedName.Union, List(left, right))

    def unapply(typeRef: TypeRef): Option[(TypeRef, TypeRef)] = typeRef match {
      case TypeRef(QualifiedName.Union, List(left, right)) => Some((left, right))
      case _ => None
    }
  }

  object Singleton {
    def apply(underlying: QualifiedName): TypeRef = TypeRef(QualifiedName(Name.SINGLETON), List(TypeRef(underlying)))

    def unapply(typeRef: TypeRef): Option[QualifiedName] = typeRef match {
      case TypeRef(QualifiedName(Name.SINGLETON), List(TypeRef(underlying, Nil))) => Some(underlying)
      case _ => None
    }
  }

  object Repeated {
    def apply(underlying: TypeRef): TypeRef = TypeRef(QualifiedName(Name.REPEATED), List(underlying))

    def unapply(typeRef: TypeRef) = typeRef match {
      case TypeRef(QualifiedName(Name.REPEATED), List(underlying)) => Some(underlying)
      case _ => None
    }
  }
}
