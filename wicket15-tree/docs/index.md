---
title: Wicket 1.5 Tree
category: ui
description: The Tree and TreeTable components from before Wicket 6, kept for applications that still use them.
artifactId: wicket15-tree
tags: tree treetable legacy
---

A copy of the tree components that were part of wicket-extensions before Wicket 6.0.0, kept for applications that still use them. The classes keep their original package, `org.apache.wicket.extensions.markup.html.tree`, so existing imports keep working.

For new code, use the tree components in Wicket itself: `NestedTree` and `TableTree` in wicket-extensions (`org.apache.wicket.extensions.markup.html.repeater.tree`).

## Components

All trees take a Swing `javax.swing.tree.TreeModel`, either directly or as an `IModel<? extends TreeModel>`.

* `Tree`: a complete tree in which each item consists of a junction link, an icon and a label.
* `LabelTree`: a simple tree that renders each node with a label.
* `LinkTree`: a `LabelTree` with a link on each node, so users can select individual nodes. Override `onNodeLinkClicked(Object node, BaseTree tree, AjaxRequestTarget target)` to react to a click.
* `BaseTree`: the base for custom trees; implement `newNodeComponent(String id, IModel<Object> model)`.
* `TreeTable` (in the `table` subpackage): a grid with a tree, divided into columns. One of the columns has to be a `PropertyTreeColumn`; other columns can be, for example, `PropertyRenderableColumn`s. `TreeTable` is marked deprecated in favour of `TableTree`.

The trees can use three kinds of links, set with `setLinkType(LinkType)`: `LinkType.REGULAR` (always refreshes the whole page, works without JavaScript), `LinkType.AJAX` (partial updates, needs JavaScript) and `LinkType.AJAX_FALLBACK` (partial updates, acts like a regular link without JavaScript).

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicket15-tree</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

This module used to live in its own repository, [wicketstuff/wicket1.5-tree](https://github.com/wicketstuff/wicket1.5-tree), which was last released as `org.wicketstuff:wicket15-tree:9.0.0`. It is now part of Wicket Stuff core, and its version follows the Wicket Stuff core version.

## Example

A `TreeTable` showing a family tree, from the module's tests:

```java
FamilyTreeNode family = new FamilyTreeNode(opa);
FamilyTreeNode jannekeNode = family.addChild(janneke);
jannekeNode.addChild(pino);

TreeModel treeModel = new DefaultTreeModel(family);
IColumn[] columns = new IColumn[] {
		new PropertyTreeColumn<>(new ColumnLocation(Alignment.LEFT, 100, Unit.PERCENT), "Titel", "self.name"),
		new PropertyRenderableColumn<>(new ColumnLocation(Alignment.LEFT, 100, Unit.PERCENT), "Titel",
				"self.name") };
add(new TreeTable("tree", treeModel, columns));
```

```html
<div wicket:id="tree"></div>
```

## Tests and example application

The [tests](https://github.com/wicketstuff/core/tree/master/wicket15-tree/src/test/java/org/apache/wicket/extensions/markup/html/tree) render `Tree`, `LabelTree`, `LinkTree` and `TreeTable`, and expand, collapse and select nodes using Ajax. Run [`Start`](https://github.com/wicketstuff/core/blob/master/wicket15-tree/src/test/java/Start.java) from `src/test/java` to try the `TreeTable` example on <http://localhost:8080/>.

Source: [`wicket15-tree`](https://github.com/wicketstuff/core/tree/master/wicket15-tree)
