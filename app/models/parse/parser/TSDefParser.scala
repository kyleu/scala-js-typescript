package models.parse.parser

import models.parse.parser.tree._

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._
import scala.util.parsing.input._

class TSDefParser() extends StdTokenParsers with ImplicitConversions {
  override type Tokens = TSTokens
  override val lexical: TSDefLexical = new TSDefLexical

  lexical.reserved ++= List(
    // Value keywords
    "true", "false",

    // Current JavaScript keywords
    "break", "case", "catch", "continue", "debugger", "default", "delete",
    "do", "else", "finally", "for", "function", "if", "in", "instanceof",
    "new", "return", "switch", "this", "throw", "try", "typeof", "var",
    "void", "while", "with",

    // Future reserved keywords - some used in TypeScript
    "class", "readonly", "const", "enum", "export", "as", "extends", "import", "super",

    // Future reserved keywords in Strict mode - some used in TypeScript
    "implements", "interface", "let", "package", "private", "protected",
    "public", "static", "yield",

    // Additional keywords of TypeScript
    "declare", "module", "type", "namespace", "abstract", "//")

  lazy val wsParser = accept("ws", {
    case lexical.Whitespace(chars) => WhitespaceDecl(chars)
  })
  def ws[T](p: Parser[T]) = opt(wsParser) ~> p <~ opt(wsParser)
  val terminator = opt(";")

  lexical.delimiters ++= List("{", "}", "(", ")", "[", "]", "<", ">", ".", ";", ",", "?", ":", "=", "|", "...", "=>")

  def parseDefinitions(input: Reader[Char]) = phrase(ambientDeclarations)(new lexical.Scanner(input))

  lazy val ambientDeclarations: Parser[List[DeclTree]] = rep(ambientDeclaration)

  val exportDelaration = ws {
    val simple = ws("export") ~ ws("=") ~ ws(identifier) <~ terminator ^^ {
      case a ~ b ~ c => ExportDecl(Seq(a, b, c.name).mkString(" "))
    }
    val namespaced = ws("export") ~ ws("as") ~ ws("namespace") ~ ws(identifier) <~ terminator ^^ {
      case a ~ b ~ c ~ d => ExportDecl(Seq(a, b, c, d.name).mkString(" "))
    }
    simple | namespaced
  }

  val moduleDelaration = opt(ws("declare")) ~> opt(ws("export")) ~> opt(ws("declare")) ~> moduleElementDecl1

  lazy val commentDecl = ws(accept("CommentDecl", {
    case lexical.ImportComment(text) => ImportCommentDecl(text)
    case lexical.LineComment(text) => LineCommentDecl(text)
    case lexical.MultilineComment(text) => MultilineCommentDecl(text)
  }))
  lazy val commentType = ws(accept("CommentType", {
    case lexical.ImportComment(text) => ImportCommentType(text)
    case lexical.LineComment(text) => LineCommentType(text)
    case lexical.MultilineComment(text) => MultilineCommentType(text)
  }))
  lazy val commentMember = ws(accept("CommentMember", {
    case lexical.ImportComment(text) => ImportCommentMember(text)
    case lexical.LineComment(text) => LineCommentMember(text)
    case lexical.MultilineComment(text) => MultilineCommentMember(text)
  }))

  lazy val ambientDeclaration: Parser[DeclTree] = commentDecl | wsParser | moduleDelaration | exportDelaration

  lazy val ambientModuleDecl: Parser[DeclTree] = ws("module" | "namespace") ~> rep1sep(propertyName, ws(".")) ~ ws(moduleBody) ^^ {
    case nameParts ~ body => nameParts.init.foldRight(ModuleDecl(nameParts.last, body))((name, inner) => ModuleDecl(name, inner :: Nil))
  }

  lazy val moduleBody: Parser[List[DeclTree]] = ws("{") ~> rep(ws(moduleElementDecl)) <~ ws("}") ^^ (_.flatten)

  lazy val moduleElementDecl: Parser[Option[DeclTree]] = {
    ws("export") ~> (moduleElementDecl1 ^^ (Some(_)) | ws("=") ~> identifier <~ terminator ^^^ None) | moduleElementDecl1 ^^ (Some(_))
  }

  lazy val moduleElementDecl1: Parser[DeclTree] = ws {
    commentDecl | ambientModuleDecl | ambientValDecl | ambientVarDecl | ambientFunctionDecl |
      ambientEnumDecl | ambientClassDecl | ambientInterfaceDecl | typeAliasDecl
  }

  lazy val ambientVarDecl: Parser[DeclTree] = ws("var") ~> identifier ~ ws(optTypeAnnotation) <~ terminator ^^ VarDecl

  lazy val ambientValDecl: Parser[DeclTree] = ws("const" | "let") ~> identifier ~ ws(optTypeAnnotation) <~ terminator ^^ ValDecl

  lazy val ambientFunctionDecl: Parser[DeclTree] = maybeProtected ~ ws("function") ~ identifier ~ functionSignature ~ terminator ^^ {
    case prot ~ _ ~ id ~ sig ~ _ => FunctionDecl(prot, id, sig)
  }

  lazy val ambientEnumDecl: Parser[DeclTree] = ws("enum") ~> typeName ~ (ws("{") ~> ambientEnumBody <~ ws("}")) ^^ EnumDecl

  lazy val ambientEnumBody: Parser[List[Ident]] = repsep(identifier <~ opt(ws("=") ~ numericLit), ws(",")) <~ opt(",")

  lazy val ambientClassDecl: Parser[DeclTree] = maybeAbstract ~ ws("class") ~ typeName ~ tparams ~ classParent ~ classImplements ~ memberBlock ~ terminator ^^ {
    case abst ~ _ ~ name ~ params ~ parent ~ impls ~ members ~ _ => ClassDecl(abst, name, params, parent, impls, members)
  }

  lazy val ambientInterfaceDecl: Parser[DeclTree] = ws("interface") ~> typeName ~ tparams ~ intfInheritance ~ memberBlock <~ terminator ^^ InterfaceDecl

  lazy val typeAliasDecl: Parser[DeclTree] = ws("type") ~> typeName ~ tparams ~ (ws("=") ~> typeDesc) <~ terminator ^^ TypeAliasDecl

  lazy val tparams = ws("<") ~> rep1sep(typeParam, ws(",")) <~ ws(">") | success(Nil)

  lazy val typeParam: Parser[TypeParam] = typeName ~ opt(ws("extends") ~> typeRef) ^^ TypeParam

  lazy val classParent = opt(ws("extends") ~> typeRef)

  lazy val classImplements = ws("implements") ~> repsep(typeRef, ws(",")) | success(Nil)

  lazy val intfInheritance = ws("extends") ~> repsep(typeRef, ws(",")) | success(Nil)

  lazy val functionSignature = tparams ~ (ws("(") ~> repsep(functionParam, ws(",")) <~ ws(")")) ~ optResultType ^^ FunSignature

  lazy val functionParam = repeatedParamMarker ~ identifier ~ optionalMarker ~ optParamType ^^ {
    case false ~ i ~ o ~ t => FunParam(i, o, t)
    case _ ~ i ~ o ~ Some(ArrayType(t)) => FunParam(i, o, Some(RepeatedType(t)))
    case _ ~ i ~ o ~ t => FunParam(i, o, t)
  }

  lazy val repeatedParamMarker = opt(ws("...")) ^^ (_.isDefined)

  lazy val optionalMarker = opt(ws("?")) ^^ (_.isDefined)

  lazy val optParamType = opt(ws(":") ~> paramType)

  lazy val paramType: Parser[TypeTree] = typeDesc | stringLiteral ^^ ConstantType

  lazy val optResultType = opt(ws(":") ~> resultType)

  lazy val resultType: Parser[TypeTree] = (ws("void") ^^^ TypeRefTree(CoreType("void"))) | typeDesc | (ws("this") ^^^ TypeRefTree(CoreType("dynamic")))

  lazy val optTypeAnnotation = opt(typeAnnotation)

  lazy val typeAnnotation = ws(":") ~> typeDesc

  lazy val stringLiteralToType = stringLiteral.map(x => TypeRefTree(CoreType("string")))
  lazy val numericLiteralToType = numericLit.map(x => TypeRefTree(CoreType("number")))

  lazy val typeDesc: Parser[TypeTree] = rep1sep(singleTypeDesc | stringLiteralToType | numericLiteralToType, /* ws("&") | */ ws("|")) ^^ (_.reduceLeft(UnionType))

  lazy val singleTypeDesc: Parser[TypeTree] = baseTypeDesc ~ rep("[" ~ "]") ^^ {
    case base ~ arrayDims => (base /: arrayDims)((elem, _) => ArrayType(elem))
  }

  lazy val baseTypeDesc: Parser[TypeTree] = commentType | typeRef | objectType | functionType | typeQuery | tupleType | "(" ~> typeDesc <~ ")"

  lazy val typeRef: Parser[TypeRefTree] = baseTypeRef ~ opt(typeArgs) ^^ {
    case base ~ optTargs => TypeRefTree(base, optTargs.getOrElse(Nil))
  }

  lazy val baseTypeRef: Parser[BaseTypeRef] = rep1sep(ws("void") | ident, ".") ^^ { parts =>
    if (parts.tail.isEmpty) {
      typeNameToTypeRef(parts.head)
    } else {
      QualifiedTypeName(parts.init map Ident, TypeName(parts.last))
    }
  }

  lazy val typeArgs: Parser[List[TypeTree]] = ws("<") ~> rep1sep(typeDesc, ws(",")) <~ ws(">")

  lazy val functionType: Parser[TypeTree] = tparams ~ (ws("(") ~> repsep(functionParam, ws(",")) <~ ws(")")) ~ (ws("=>") ~> resultType) ^^ {
    case tparamsX ~ params ~ resultTypeX => FunctionType(FunSignature(tparamsX, params, Some(resultTypeX)))
  }

  lazy val typeQuery: Parser[TypeTree] = ws("typeof") ~> rep1sep(ident, ".") ^^ { parts =>
    TypeQuery(QualifiedIdent(parts.init.map(Ident), Ident(parts.last)))
  }

  lazy val tupleType: Parser[TypeTree] = ws("[") ~> rep1sep(typeDesc, ws(",")) <~ ws("]") ^^ { parts =>
    TupleType(parts)
  }

  lazy val objectType: Parser[TypeTree] = memberBlock ^^ ObjectType

  lazy val memberBlock: Parser[List[MemberTree]] = ws("{") ~> rep(typeMember <~ opt(ws(";") | ws(","))) <~ ws("}")

  lazy val typeMember: Parser[MemberTree] = commentMember | callMember | constructorMember | indexMember | namedMember

  lazy val callMember: Parser[MemberTree] = functionSignature ^^ CallMember

  lazy val constructorMember: Parser[MemberTree] = ws("new") ~> functionSignature ^^ ConstructorMember

  lazy val indexMember: Parser[MemberTree] = (ws("[") ~> identifier ~ typeAnnotation <~ ws("]")) ~ typeAnnotation ^^ IndexMember

  lazy val namedMember: Parser[MemberTree] = maybeStaticPropName ~ optionalMarker >> {
    case (name, static, prot, readonly) ~ optional =>
      val f = functionSignature ^^ (FunctionMember(prot, name, optional, _, static, readonly))
      val t = typeAnnotation ^^ (PropertyMember(prot, name, optional, _, static, readonly))
      f | t
  }

  lazy val maybeAbstract: Parser[Boolean] = opt(ws("abstract")).map(_.isDefined)
  lazy val maybeProtected: Parser[Boolean] = opt(ws("protected")).map(_.isDefined)
  lazy val maybePublic: Parser[Boolean] = opt(ws("public")).map(_.isDefined)
  lazy val maybeReadonly: Parser[Boolean] = opt(ws("readonly")).map(_.isDefined)

  lazy val maybeStaticPropName: Parser[(PropertyName, Boolean, Boolean, Boolean)] = {
    val stat = maybeReadonly ~ maybePublic ~ maybeProtected ~ ws("static") ~ propertyName ^^ {
      case readonly ~ pub ~ prot ~ _ ~ prop => staticPropName(prop, prot, readonly)
    }
    val dyn = maybeReadonly ~ maybePublic ~ maybeProtected ~ propertyName ^^ {
      case readonly ~ pub ~ prot ~ prop => nonStaticPropName(prop, prot, readonly)
    }
    stat | dyn
  }

  val staticPropName = (p: PropertyName, prot: Boolean, readonly: Boolean) => (p, true, prot, readonly)
  val nonStaticPropName = (p: PropertyName, prot: Boolean, readonly: Boolean) => (p, false, prot, readonly)

  lazy val identifier = identifierName ^^ Ident

  lazy val typeName = identifierName ^^ TypeName

  lazy val identifierName = accept("IdentifierName", {
    case lexical.Identifier(chars) => chars
    case lexical.Keyword(chars) if chars.forall(Character.isLetter) => chars
  })

  lazy val stringLiteral: Parser[StringLiteral] = stringLit ^^ StringLiteral

  lazy val propertyName: Parser[PropertyName] = identifier | stringLiteral

  private val isCoreTypeName = Set("any", "void", "number", "bool", "boolean", "string", "null", "undefined")

  def typeNameToTypeRef(name: String): BaseTypeRef = if (isCoreTypeName(name)) {
    CoreType(name)
  } else {
    TypeName(name)
  }

  object ArrayType {
    def apply(elem: TypeTree): TypeRefTree = TypeRefTree(TypeName("Array"), List(elem))

    def unapply(typeRef: TypeRefTree): Option[TypeTree] = typeRef match {
      case TypeRefTree(TypeName("Array"), List(elem)) => Some(elem)
      case _ => None
    }
  }
}
