package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.d3js;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.IReportRenderer;

public class D3DataFileRenderer implements IReportRenderer {

	@Override
	public String render(ISerializedObjectTree tree) {
		JSonBuilder sb = new JSonBuilder(new StringBuilder());

		sb.start();
		sb.textAttr("name", "all");
		sb.openArray("children");
		render(sb, tree);
		sb.closeArray();
		sb.end();

		return sb.toString();
	}

	private void render(JSonBuilder sb, ISerializedObjectTree tree) {
		sb.start();
		sb.textAttr("name", label(tree));
		sb.attr("size", "" + (tree.size() + tree.childSize()));
		if (!tree.children().isEmpty()) {
			sb.openArray("children");
			for (ISerializedObjectTree child : tree.children()) {
				render(sb, child);
			}
			sb.closeArray();
		}
		sb.end();
	}

	private String label(ISerializedObjectTree tree) {
		return (tree.type().isAnonymousClass()
				? tree.type().getSuperclass().getName()
				: tree.type().getName()) + (tree.label() != null
				? "(" + tree.label() + ")"
				: "");
	}

	static class JSonBuilder {

		private final StringBuilder _sb;
		int indent = 0;

		JSonBuilder(StringBuilder sb) {
			_sb = sb;
		}

		JSonBuilder openArray(String name) {
			return indent().label(name).colon().raw("[\n");
		}

		JSonBuilder closeArray() {
			return indent().raw("]\n");
		}

		JSonBuilder start() {
			indent().raw("{\n");
			indent++;
			return this;
		}

		JSonBuilder end() {
			indent--;
			indent().raw("},\n");
			return this;
		}

		JSonBuilder textAttr(String name, String value) {
			return indent().label(name).colon().label(value).raw(",\n");
		}

		JSonBuilder attr(String name, String value) {
			return indent().label(name).colon().raw(value).raw(",\n");
		}

		JSonBuilder colon() {
			_sb.append(": ");
			return this;
		}

		JSonBuilder raw(String text) {
			_sb.append(text);
			return this;
		}

		JSonBuilder label(String name) {
			_sb.append("\"").append(name).append("\"");
			return this;
		}

		JSonBuilder indent() {
			for (int i = 0; i < indent; i++) {
				_sb.append("  ");
			}
			return this;
		}

		@Override
		public String toString() {
			return _sb.toString();
		}
	}
}
