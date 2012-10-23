package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Report.Column;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Report.Row;

public class ReportTest
{
	@Test
	public void createReport() {
		Report.Column a = new Report.Column("A");
		a.attributes().set(Column.Align.Left);
		a.attributes().set(Column.FillBefore, '-');
		a.attributes().set(Column.FillAfter, '.');
		
		Assert.assertEquals(Column.Align.Left, a.attributes().get(Column.Align.Right));
		Assert.assertEquals(Character.valueOf('-'), a.attributes().get(Column.FillBefore,' '));
		Assert.assertEquals(Character.valueOf('.'), a.attributes().get(Column.FillAfter,'-'));
		
		Report.Column b = new Report.Column("B");
		a.attributes().set(Column.Align.Right);
		Report.Column c = new Report.Column("C");
		
		Report report=new Report();
		Row row = report.newRow();
		row.set(a,0,"AAA");
		row.set(b,0,"BB");
		row.set(c,0,"C");
		
		String textReport=report.export(a,c).asText();
		
		Assert.assertEquals("--A,C,\nAAA,C,\n",textReport);
	}
	
	@Test
	public void createCustomReport() {
		
	}

}
