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
package org.wicketstuff.pageserializer.common.analyze.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.AttributeBuilder;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Column;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Report;

/**
 * creates an report based on the types
 * the size calculation can have some as we say in german "Ungenauigkeiten"
 * because f.i. an array list can contain an array list, so the sizes of the sub list is added twice 
 * @author mosmann
 *
 */
public class TypeSizeReport implements ISerializedObjectTreeProcessor
{

	private final static Logger LOG = LoggerFactory.getLogger(TypeSizeReport.class);

	static final Column emptyFirst = new Column("",
		new AttributeBuilder()
			.set(Column.Separator, "|")
			.build());
	static final Column label = new Column("Type",
		new AttributeBuilder().set(Column.FillAfter, ' ')
			.set(Column.Separator, "|")
			.build());
	static final Column size = new Column("bytes", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, ' ')
		.set(Column.Separator, "|")
		.build());

	private final IReportOutput reportOutput;
	
	public TypeSizeReport(IReportOutput reportOutput) {
		this.reportOutput = reportOutput;
	}
	
	@Override
	public void process(ISerializedObjectTree tree) {
		
		reportOutput.write(tree, new IReportRenderer() {

			@Override
			public String render(ISerializedObjectTree tree) {
				Map<Class<?>, Counter> map = new HashMap<Class<?>, TypeSizeReport.Counter>();
				process(tree, map);

				List<Map.Entry<Class<?>, Counter>> sorted = new ArrayList<Map.Entry<Class<?>, Counter>>();
				sorted.addAll(map.entrySet());
				Collections.sort(sorted,
						new Comparator<Map.Entry<Class<?>, Counter>>() {
							@Override
							public int compare(Entry<Class<?>, Counter> o1,
									Entry<Class<?>, Counter> o2) {
								int s1 = o1.getValue().size;
								int s2 = o2.getValue().size;
								return s1 == s2 ? 0 : s1 > s2 ? -1 : 1;
							}
						});

				Report report = new Report("TypeSizeReport\n");
				for (Map.Entry<Class<?>, Counter> e : sorted) {
					report.newRow().set(label, 0, e.getKey().getName())
							.set(size, 0, "" + e.getValue().size);
				}
				String result = report.export(emptyFirst, size, label)
						.separateColumnNamesWith('-').tableBorderWith('=')
						.asString();

				return result;
			}
		});
	}

	private void process(ISerializedObjectTree tree, Map<Class<?>, Counter> map)
	{
		Counter counter = getOrCreate(map, tree.type());
		counter.increment(tree.size());

		for (ISerializedObjectTree child : tree.children())
		{
			process(child, map);
		}

	}

	private Counter getOrCreate(Map<Class<?>, Counter> map, Class<? extends Object> type)
	{
		Counter ret = map.get(type);
		if (ret == null)
		{
			ret = new Counter();
			map.put(type, ret);
		}
		return ret;
	}

	static class Counter
	{
		int size;

		void increment(int diff)
		{
			size = size + diff;
		}
	}

}
