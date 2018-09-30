import {Node, ts} from "ts-simple-ast";

function getProperties(n: Node<ts.Node>) {
  var ret: any = {};

  ret.kind = n.getKindName();
  ret.start = n.getStart(false);
  ret.width = n.getWidth();
  ret.comments = n.getLeadingCommentRanges().concat(n.getTrailingCommentRanges()).map(c => c.getText());
  ret.childCount = n.getChildCount();

  switch (ret.kind) {
    case "SyntaxList":
      processSyntax(n as Node<ts.SyntaxList>, ret);
      break;
    case "ModuleDeclaration":
      processModule(n as Node<ts.ModuleDeclaration>, ret);
      break;
    default:
      ret.todo = ret.kind;
  }

  return ret;
}

function processSyntax(n: Node<ts.SyntaxList>, ret: any) {
  ret.file = n.getSourceFile().getBaseName();
}

function processModule(n: Node<ts.ModuleDeclaration>, ret: any) {
  ret.name = n.compilerNode.name.getText;
}

export {getProperties};
