# scala-js-typescript

A TypeScript-to-Scala.js converter. 
Designed for parsing http://definitelytyped.org, powers http://definitelyscala.com.

## Running

Right now the whole thing is a giant Scala Play Framework app that expects certain directories.
You can run the freshly-cloned repository with `SBT`, but there won't be any projects.

To run for your own TypeScript definitions ("foo") rename your `index.d.ts` to "foo.ts", and place it in `./data/typescript`.
Then start the app with `sbt run`, and open [http://localhost:9000](). Select `Project List`, then your project.
You'll see options to parse, build, and publish your project. Publishing won't work.

In order to create the SBT project, you'll need to clone `git@github.com:DefinitelyScala/scala-js-template.git` into `./util`.

More documentation coming soon. Feel free to email me if you run into trouble.

## License

The code is licensed under [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
