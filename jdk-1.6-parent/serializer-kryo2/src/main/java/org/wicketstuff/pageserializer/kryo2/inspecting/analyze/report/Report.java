package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Report.Column.Align;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Report.Column.TypedAttribute;

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

	static class TypedAttributes
	{
		Map<Class<? extends Enum>, Enum> attributes = new HashMap<Class<? extends Enum>, Enum>();
		Map<TypedAttribute, Object> typedAttributes = new HashMap<Report.Column.TypedAttribute, Object>();

		public void set(Enum<?> attribute)
		{
			attributes.put(attribute.getClass(), attribute);
		}

		protected <T extends Enum<T>> T get(T defaultValue)
		{
			T ret = (T)attributes.get(defaultValue.getClass());
			return ret != null ? ret : defaultValue;
		}

		public <T> void set(TypedAttribute<T> type, T value)
		{
			typedAttributes.put(type, value);
		}

		protected <T> T get(TypedAttribute<T> type, T defaultValue)
		{
			T ret = (T)typedAttributes.get(type);
			return ret != null ? ret : defaultValue;
		}
	}

	public static class Row
	{
		Map<Column, RowColumnValue> values = new HashMap<Report.Column, RowColumnValue>();

		public void set(Column column, int ident, String value)
		{
			values.put(column, new RowColumnValue(ident, value));
		}

		public int width(Column c)
		{
			RowColumnValue cv = values.get(c);
			return cv != null ? cv.value().length() : 0;
		}
		
		public String value(Column c) {
			RowColumnValue cv = values.get(c);
			return cv != null ? cv.value() : null;
		}

		public int ident(Column c) {
			RowColumnValue cv = values.get(c);
			return cv != null ? cv.ident() : 0;
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
		}
	}

	public static class Column
	{
		Map<Class<? extends Enum>, Enum> attributes = new HashMap<Class<? extends Enum>, Enum>();
		TypedAttributes typedAttributeSet = new TypedAttributes();

		private final String name;

		public Column(String name)
		{
			this.name = name;
		}

		public TypedAttributes attributes()
		{
			return typedAttributeSet;
		}

		public static enum Align {
			Left, Right
		}

		public static interface TypedAttribute<T>
		{

		}

		static class IntegerType implements TypedAttribute<Integer>
		{

		}

		static class CharType implements TypedAttribute<Character>
		{

		}

		public static final TypedAttribute<Character> FillBefore = new CharType();
		public static final TypedAttribute<Character> FillAfter = new CharType();
		public static final TypedAttribute<Character> Separator = new CharType();
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

		public String asText()
		{
			Map<Column, Integer> columnWidth = new HashMap<Report.Column, Integer>();
			for (Column c : columns)
			{
				columnWidth.put(c, Math.max(c.name.length(), report.width(c)));
			}

			StringBuilder sb = new StringBuilder();
			for (Column c : columns)
			{
				append(sb, columnWidth.get(c), c.name, c.attributes());
				sb.append(c.attributes().get(Column.Separator, ','));
			}
			sb.append("\n");
			for (Row r : report.rows) {
				for (Column c : columns)
				{
					append(sb,columnWidth.get(c), r.value(c),c.attributes());
					sb.append(c.attributes().get(Column.Separator, ','));
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		private void append(StringBuilder sb, int width, String value, TypedAttributes attributes)
		{
			if (value==null) value="";
			
			switch (attributes.get(Align.Left)) {
				case Right:
					fill(sb,width-value.length(),attributes.get(Column.FillBefore, ' '));
					sb.append(value);
					break;
				case Left:
				default:
					sb.append(value);
					fill(sb,width-value.length(),attributes.get(Column.FillAfter, ' '));
					break;
			}
		}

		private void fill(StringBuilder sb, int count, char c)
		{
			for (int i=0;i<count;i++) {
				sb.append(c);
			}
			
		}


	}

	protected Integer width(Column c)
	{
		int width = 0;
		for (Row row : rows)
		{
			width = Math.max(width, row.width(c));
		}
		return width;
	}

	public Export export(Column... columns)
	{
		return new Export(this, columns);
	}


}
