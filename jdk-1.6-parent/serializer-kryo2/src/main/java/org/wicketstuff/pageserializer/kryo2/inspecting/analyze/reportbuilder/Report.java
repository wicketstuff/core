package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder.Report.Row.RowColumnValue;


public class Report
{
	private final List<Row> rows = new ArrayList<Report.Row>();

	public Report()
	{
	}

	public Row newRow()
	{
		Row ret = new Row();
		rows.add(ret);
		return ret;
	}

	public static class Row
	{
		Map<Column, RowColumnValue> values = new HashMap<Column, RowColumnValue>();

		public Row set(Column column, int ident, String value)
		{
			if (value != null)
			{
				values.put(column, new RowColumnValue(ident, value));
			}
			return this;
		}

		public RowColumnValue get(Column c)
		{
			return values.get(c);
		}

		static class RowColumnValue
		{

			private final int ident;
			private final String value;

			public RowColumnValue(int ident, String value)
			{
				this.ident = ident;
				this.value = value;
			}

			public int ident()
			{
				return ident;
			}

			public String value()
			{
				return value;
			}

			public int width(int identLen)
			{
				return value.length() + identLen * ident;
			}
		}

	}

	public static class Export
	{

		private final Column[] columns;
		private final Report report;

		public Export(Report report, Column... columns)
		{
			this.report = report;
			this.columns = columns;
		}

		public String asString()
		{
			Map<Column, Integer> columnWidth = new HashMap<Column, Integer>();
			for (Column c : columns)
			{
				String ident = c.attributes().get(Column.Ident, null);
				int identLen = ident != null ? ident.length() : 0;
				columnWidth.put(c, Math.max(c.name.length(), report.width(c,identLen)));
			}

			StringBuilder sb = new StringBuilder();
			for (Column c : columns)
			{
				append(sb, columnWidth.get(c), 0, c.name, c.attributes());
				sb.append(c.attributes().get(Column.Separator, ","));
			}
			sb.append("\n");
			for (Row r : report.rows)
			{
				for (Column c : columns)
				{
					RowColumnValue rcv = r.get(c);
					String value=rcv!=null ? rcv.value() : null;
					int ident=rcv!=null ? rcv.ident() : 0;
					
					append(sb, columnWidth.get(c), ident, value, c.attributes());
					sb.append(c.attributes().get(Column.Separator, ","));
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		private void append(StringBuilder sb, int width, int ident, String value, IAttributes attributes)
		{
			if (value == null)
				value = "";

			String identValue=attributes.get(Column.Ident,"");
			
			switch (attributes.get(Column.Align.Left))
			{
				case Right :
					fill(sb, width - value.length()-identValue.length()*ident, attributes.get(Column.FillBefore, ' '));
					sb.append(value);
					fill(sb, ident,identValue);
					break;
				case Left :
				default :
					fill(sb, ident,identValue);
					sb.append(value);
					fill(sb, width - value.length()-identValue.length()*ident, attributes.get(Column.FillAfter, ' '));
					break;
			}
		}

		private void fill(StringBuilder sb, int count, String s)
		{
			for (int i = 0; i < count; i++)
			{
				sb.append(s);
			}

		}
		
		private void fill(StringBuilder sb, int count, char c)
		{
			for (int i = 0; i < count; i++)
			{
				sb.append(c);
			}

		}


	}

	protected Integer width(Column c, int identLen)
	{
		int width = 0;
		for (Row row : rows)
		{
			RowColumnValue rcv=row.get(c);
			width = Math.max(width, rcv!=null ? rcv.width(identLen) : 0);
		}
		return width;
	}

	public Export export(Column... columns)
	{
		return new Export(this, columns);
	}


}
