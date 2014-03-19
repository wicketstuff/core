/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.common.analyze.report.d3js;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.util.io.Streams;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.report.IReportRenderer;

public class D3DataFileRenderer implements IReportRenderer {

	static final String TEMPLATE=readTemplateFromResource(D3DataFileRenderer.class,"d3js-partition-template.html"); 
	
	@Override
	public String render(ISerializedObjectTree tree) {
		JSonBuilder sb = new JSonBuilder(new StringBuilder());

		sb.start();
		sb.textAttr("name", "all");
		sb.openArray("children");
		render(sb, tree);
		sb.closeArray();
		sb.end(true);

		return TEMPLATE.replace("${DATA}", sb.toString());
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
			return end(false);
		}
		
		JSonBuilder end(boolean lastOne) {
			indent--;
			if (lastOne) {
				indent().raw("}\n"); 
			} else {
				indent().raw("},\n");
			}
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
	
	private static String readTemplateFromResource(Class<?> clazz, String resourceName) {
		InputStream resourceAsStream = clazz.getResourceAsStream(resourceName);
		try {
			return Streams.readString(resourceAsStream, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				resourceAsStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
