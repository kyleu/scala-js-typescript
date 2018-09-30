import {Node, ts} from "ts-simple-ast";

function addProperties(n: Node<ts.Node>, ret: any) {
  switch (ret.kind) {
    case "SyntaxList":
      processSyntax(n as Node<ts.SyntaxList>, ret);
      break;
    default:
      ret.todo = ret.kind;
  }
}

function processSyntax(n: Node<ts.SyntaxList>, ret: any) {
  ret.file = n.getSourceFile().getBaseName();
}

function processModule(n: Node<ts.ModuleDeclaration>, ret: any) {
  ret.name = n.compilerNode.name
}

export {addProperties};
