# wicket15-tree

A copy of the classes of the old pre- Wicket-6.0.0 Tree component in wicket-extensions
(`Tree`, `LinkTree`, `LabelTree`, `TreeTable` and friends in the
`org.apache.wicket.extensions.markup.html.tree` package).

This module used to live in its own repository, https://github.com/wicketstuff/wicket1.5-tree,
which was last released as `org.wicketstuff:wicket15-tree:9.0.0`. It is now part of Wicket Stuff
core and is released with it, so its version follows the Wicket Stuff core version:

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicket15-tree</artifactId>
	<version>${wicketstuff.version}</version>
</dependency>
```

Run `Start` from `src/test/java` to try the TreeTable example on http://localhost:8080/.
