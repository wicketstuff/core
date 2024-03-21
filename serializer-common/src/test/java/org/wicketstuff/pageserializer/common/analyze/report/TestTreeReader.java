/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson jeremy@thomersonfamily.com
 * 	2012 Michael Mosmann michael@mosmann.de
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
package org.wicketstuff.pageserializer.common.analyze.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.util.ListModel;
import org.junit.jupiter.api.Test;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.components.ListViewPage;

public class TestTreeReader
{
	@Test
	public void lineParser()
	{
		String textLine = "org.wicketstuff.pageserializer.common.components.ListViewPage(0)         |   141";
		TreeReader.Line line = new TreeReader.Line(textLine);
		assertEquals(0, line.indent);
		assertEquals(141, line.size);
		assertEquals(ListViewPage.class, line.type);
		assertEquals("0", line.label);

		textLine = "  java.lang.Integer                                                |     9";
		line = new TreeReader.Line(textLine);
		assertEquals(1, line.indent);
		assertEquals(9, line.size);
		assertEquals(Integer.class, line.type);
		assertEquals(null, line.label);

		textLine = "  [Ljava.lang.Object;                                              |     2";
		line = new TreeReader.Line(textLine);
		assertEquals(1, line.indent);
		assertEquals(2, line.size);
		assertEquals(Object[].class, line.type);
		assertEquals(null, line.label);

		textLine = "    org.apache.wicket.markup.html.list.ListView(list)              |   114";
		line = new TreeReader.Line(textLine);
		assertEquals(2, line.indent);
		assertEquals(114, line.size);
		assertEquals(ListView.class, line.type);
		assertEquals("list", line.label);
	}

	@Test
	public void lineLines() throws IOException
	{
		String lines = "org.wicketstuff.pageserializer.common.components.ListViewPage(0)         |   141\n"
			+ "  java.lang.Integer                                                |     9\n"
			+ "  [Ljava.lang.Object;                                              |     2\n"
			+ "    org.apache.wicket.markup.html.list.ListView(list)              |   114\n"
			+ "      org.apache.wicket.model.util.ListModel                       |     2\n"
			+ "        java.util.Arrays$ArrayList                                 |     3\n"
			+ "          java.lang.String                                         |     4\n"
			+ "          java.lang.String                                         |     4\n"
			+ "          java.lang.String                                         |     4\n"
			+ "      java.lang.Integer                                            |     1\n"
			+ "  org.apache.wicket.request.mapper.parameter.PageParameters        |     3";

		ISerializedObjectTree sample = TreeReader.fromString(lines);

		assertEquals("0", sample.label());
		assertEquals(141, sample.size());
		assertEquals(146, sample.childSize());

		assertEquals(ListModel.class, sample.children()
			.get(1)
			.children()
			.get(0)
			.children()
			.get(0)
			.type());
	}
}
