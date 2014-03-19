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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.AttributeBuilder;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Column;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Report;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Report.Row;

/**
 * renders a tree into a report with sizes
 * @author mosmann
 *
 */
public class TreeSizeReport implements ISerializedObjectTreeProcessor
{
	static final Column emptyFirst = new Column("",
		new AttributeBuilder()
			.set(Column.Separator, "|")
			.build());
	static final Column id = new Column("#",
		new AttributeBuilder().set(Column.Align.Right).set(Column.FillBefore, ' ')
			.set(Column.Separator, "| ")
			.build());
	static final Column label = new Column("Type",
		new AttributeBuilder().set(Column.FillAfter, ' ')
			.set(Column.Separator, "|")
			.set(Column.Indent, "  ")
			.build());
	static final Column percent = new Column("", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, ' ')
		.set(Column.Separator, "%| ")
		.build());
	static final Column sum = new Column("sum", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, ' ')
		.set(Column.Separator, "| ")
		.build());
	static final Column local = new Column("local", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, ' ')
		.set(Column.Separator, "| ")
		.build());
	static final Column child = new Column("child", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, ' ')
		.set(Column.Separator, "|")
		.build());

	private final static Logger LOG = LoggerFactory.getLogger(TreeSizeReport.class);
	
	private final IReportOutput reportOutput;

	public TreeSizeReport(IReportOutput reportOutput) {
		this.reportOutput = reportOutput;
	}
	
	@Override
	public void process(ISerializedObjectTree tree) {
		reportOutput.write(tree, new IReportRenderer() {

			@Override
			public String render(ISerializedObjectTree tree) {

				Report report = new Report("TreeSizeReport\n");
				process(tree, report, 0, tree.size() + tree.childSize());
				String result = report
						.export(emptyFirst, id, percent, sum, local,
								child, label).separateColumnNamesWith('-')
						.tableBorderWith('=').asString();

				return result;
			}
		});
	}

	private void process(ISerializedObjectTree tree, Report report, int indent, int allSize)
	{
		Row row = report.newRow();
		row.set(id, 0, "" + tree.id());
		row.set(label, indent, label(tree));
		row.set(percent, 0, "" + ((tree.size() + tree.childSize()) * 100 / allSize));
		row.set(sum, 0, "" + (tree.size() + tree.childSize()));
		row.set(local, 0, "" + tree.size());
		row.set(child, 0, "" + tree.childSize());

		for (ISerializedObjectTree child : preProcess(tree.children()))
		{
			process(child, report, indent + 1, allSize);
		}
	}

	protected List<? extends ISerializedObjectTree> preProcess(
		List<? extends ISerializedObjectTree> children)
	{
		return children;
	}

	private String label(ISerializedObjectTree tree)
	{
		return (tree.type().isAnonymousClass() ? tree.type().getSuperclass().getName()
			: tree.type().getName()) +
			(tree.label() != null ? "(" + tree.label() + ")" : "");
	}

}
