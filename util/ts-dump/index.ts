import Project, {Node, ScriptTarget, printNode, ts} from "ts-simple-ast";
import {isCyclic} from "./cycles";
import {getProperties} from "./props";

const project = new Project({
  tsConfigFilePath: "tsconfig.json",
  addFilesFromTsConfig: false,

  compilerOptions: {
    target: ScriptTarget.ES3
  }
});

const sourceFile = project.addExistingSourceFile("tests/jQuery.d.ts");

function process(n: Node<ts.Node>) {
  const ret = getProperties(n);
  includeChildren(n, ret);

  return ret;
}

function includeChildren(n: Node<ts.Node>, ret: any) {
  switch(ret.kind) {
    case "":
    case "?":
      break;
    default:
      const kids = n.getChildren().map(process);
      if(kids.length != 0) {
        ret.children = kids;
      }
  }
}

const ret = sourceFile.getChildren().map((n, i, c) => process(n));
ret.forEach(isCyclic);
console.info(JSON.stringify(ret, null, 2));
