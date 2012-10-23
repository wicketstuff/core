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

		public Row set(Column column, int indent, String value)
		{
			if (value != null)
			{
				values.put(column, new RowColumnValue(indent, value));
			}
			return this;
		}

		public RowColumnValue get(Column c)
		{
			return values.get(c);
		}

		static class RowColumnValue
		{

			private final int indent;
			private final String value;

			public RowColumnValue(int indent, String value)
			{
				this.indent = indent;
				this.value = value;
			}

			public int indent()
			{
				return indent;
			}

			public String value()
			{
				return value;
			}

			public int width(int indentLen)
			{
				return value.length() + indentLen * indent;
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
				String indent = c.attributes().get(Column.Indent, null);
				int indentLen = indent != null ? indent.length() : 0;
				columnWidth.put(c, Math.max(c.name.length(), report.width(c,indentLen)));
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
					int indent=rcv!=null ? rcv.indent() : 0;
					
					append(sb, columnWidth.get(c), indent, value, c.attributes());
					sb.append(c.attributes().get(Column.Separator, ","));
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		private void append(StringBuilder sb, int width, int indent, String value, IAttributes attributes)
		{
			if (value == null)
				value = "";

			String indentValue=attributes.get(Column.Indent,"");
			
			switch (attributes.get(Column.Align.Left))
			{
				case Right :
					fill(sb, width - value.length()-indentValue.length()*indent, attributes.get(Column.FillBefore, ' '));
					sb.append(value);
					fill(sb, indent,indentValue);
					break;
				case Left :
				default :
					fill(sb, indent,indentValue);
					sb.append(value);
					fill(sb, width - value.length()-indentValue.length()*indent, attributes.get(Column.FillAfter, ' '));
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

	protected Integer width(Column c, int indentLen)
	{
		int width = 0;
		for (Row row : rows)
		{
			RowColumnValue rcv=row.get(c);
			width = Math.max(width, rcv!=null ? rcv.width(indentLen) : 0);
		}
		return width;
	}

	public Export export(Column... columns)
	{
		return new Export(this, columns);
	}


}
