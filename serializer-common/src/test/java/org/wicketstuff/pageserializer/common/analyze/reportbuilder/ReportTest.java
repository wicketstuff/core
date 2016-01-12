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
package org.wicketstuff.pageserializer.common.analyze.reportbuilder;

import org.junit.Assert;
import org.junit.Test;
import org.wicketstuff.pageserializer.common.analyze.reportbuilder.Report.Row;

public class ReportTest
{
	@Test
	public void createReport()
	{
		Column a = new Column("A", new AttributeBuilder().set(Column.Align.Right)
			.set(Column.FillBefore, '-')
			.set(Column.FillAfter, '.')
			.build());

		Assert.assertEquals(Column.Align.Right, a.attributes().get(Column.Align.Left));
		Assert.assertEquals(Character.valueOf('-'), a.attributes().get(Column.FillBefore, ' '));
		Assert.assertEquals(Character.valueOf('.'), a.attributes().get(Column.FillAfter, '-'));

		Column b = new Column("B",new AttributeBuilder().set(Column.Align.Right).build());
		Column c = new Column("C");

		Report report = new Report();
		Row row = report.newRow();
		row.set(a, 0, "AAA");
		row.set(b, 0, "BB");
		row.set(c, 0, "C");

		String textReport = report.export(a, c).asString();

		Assert.assertEquals("--A,C,\nAAA,C,\n", textReport);
	}

	@Test
	public void createCustomReport()
	{
		Column emptyStart=new Column("",new AttributeBuilder().set(Column.Separator, "|").build());
		Column first=new Column("First",new AttributeBuilder().set(Column.Separator, "|").build());
		Column number=new Column("Number",new AttributeBuilder().set(Column.Align.Right).set(Column.Separator, "|").build());

		Report report = new Report();
		Row row=report.newRow();
		row.set(first, 0, "baker").set(number, 0, "12,00");
		row=report.newRow();
		row.set(first, 0, "banker").set(number, 0, "117,00");
		row=report.newRow();
		row.set(first, 0, "pilot").set(number, 0, "1,12");
		
		String textReport = report.export(emptyStart, first, number).asString();

		Assert.assertEquals("|First |Number|\n|baker | 12,00|\n|banker|117,00|\n|pilot |  1,12|\n", textReport);
	}

	@Test
	public void createCustomReportWithIndent()
	{
		Column emptyStart=new Column("",new AttributeBuilder().set(Column.Separator, "|").build());
		Column first=new Column("Comp",new AttributeBuilder().set(Column.Separator, "..|").set(Column.Indent,"--").set(Column.FillAfter,'.').build());
		Column number=new Column("Calls",new AttributeBuilder().set(Column.Align.Right).set(Column.Separator, "|").build());

		Report report = new Report();
		Row row=report.newRow();
		row.set(first, 0, "Main").set(number, 0, "100");
		row=report.newRow();
		row.set(first, 1, "Login").set(number, 0, "80");
		row=report.newRow();
		row.set(first, 2, "Help").set(number, 0, "10");
		row=report.newRow();
		row.set(first, 1, "Start").set(number, 0, "20");
		
		String textReport = report.export(emptyStart, first, number).separateColumnNamesWith('-').asString();

		Assert.assertEquals("|Comp......|Calls|\n------------------\n|Main......|  100|\n|--Login...|   80|\n|----Help..|   10|\n|--Start...|   20|\n", textReport);
	}
}
