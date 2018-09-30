import Project, {Node, ScriptTarget, printNode, ts} from "ts-simple-ast";
import {addProperties} from "./props";

const project = new Project({
  tsConfigFilePath: "tsconfig.json",
  addFilesFromTsConfig: false,

  compilerOptions: {
    target: ScriptTarget.ES3
  }
});

const sourceFile = project.addExistingSourceFile("tests/jQuery.d.ts");

function process(n: Node<ts.Node>) {
  var ret: any = {};

  ret.kind = n.getKindName();
  ret.start = n.getStart(false);
  ret.width = n.getWidth();
  ret.comments = n.getLeadingCommentRanges().concat(n.getTrailingCommentRanges()).map(c => c.getText());
  ret.childCount = n.getChildCount();

  addProperties(n, ret);

  const kids = n.getChildren().map(process);
  if(kids.length != 0) {
    ret.children = kids;
  }
  return ret;
}

const ret = sourceFile.getChildren().map((n, i, c) => process(n));
console.info(JSON.stringify(ret, null, 2));
