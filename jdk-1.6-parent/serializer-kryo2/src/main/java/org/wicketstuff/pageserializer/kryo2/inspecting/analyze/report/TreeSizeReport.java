package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Reports.Ident;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder.AttributeBuilder;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder.Column;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder.Report;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder.Report.Row;

public class TreeSizeReport implements ISerializedObjectTreeProcessor
{
	static final Column emptyFirst = new Column("",
		new AttributeBuilder()
			.set(Column.Separator, "|")
			.build());
	static final Column label = new Column("Type",
		new AttributeBuilder().set(Column.FillAfter, '.')
			.set(Column.Separator, "...")
			.set(Column.Indent, "  ")
			.build());
	static final Column percent = new Column("", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, '.')
		.set(Column.Separator, "%|.")
		.build());
	static final Column sum = new Column("sum", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, '.')
		.set(Column.Separator, "|.")
		.build());
	static final Column local = new Column("local", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, '.')
		.set(Column.Separator, "|.")
		.build());
	static final Column child = new Column("child", new AttributeBuilder().set(Column.Align.Right)
		.set(Column.FillBefore, '.')
		.set(Column.Separator, "|")
		.build());

	private final static Logger LOG = LoggerFactory.getLogger(TreeSizeReport.class);

	@Override
	public void process(ISerializedObjectTree tree)
	{
//		Ident ident = new Ident(2, ' ');
//		int labelColumnSize = labelColumnSize(tree, ident, 0, 0);

//		StringBuilder sb = new StringBuilder();
//		process(tree, sb, ident, 0, tree.size() + tree.childSize(), labelColumnSize);

		if (LOG.isDebugEnabled()) {
			Report report = new Report("\n");
			process(tree, report, 0, tree.size() + tree.childSize());
			String result=report.export(emptyFirst, label, percent, sum, local, child)
				.separateColumnNamesWith('-')
				.tableBorderWith('=')
				.asString();

			LOG.debug(result);
		}
	}

	private void process(ISerializedObjectTree tree, Report report, int indent, int allSize)
	{
		Row row = report.newRow();
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

	private void process(ISerializedObjectTree tree, StringBuilder sb, Ident ident, int level,
		int allSize, int labelColumnSize)
	{
		Reports.label(sb, new Ident(ident.size() * level, ident.value()), label(tree),
			labelColumnSize, '.');
		Reports.rightColumn(sb, 4, '.', "" + ((tree.size() + tree.childSize()) * 100 / allSize),
			'<');
		sb.append("%.|");
		Reports.rightColumn(sb, 8, '.', "" + (tree.size() + tree.childSize()), '<');
		sb.append("|");
		Reports.rightColumn(sb, 8, '.', "" + tree.size(), '<');
		sb.append("|");
		Reports.rightColumn(sb, 8, '.', "" + tree.childSize(), '<');
		sb.append("\n");

		for (ISerializedObjectTree child : preProcess(tree.children()))
		{
			process(child, sb, ident, level + 1, allSize, labelColumnSize);
		}
	}

	protected List<? extends ISerializedObjectTree> preProcess(
		List<? extends ISerializedObjectTree> children)
	{
		return children;
	}

	private int labelColumnSize(ISerializedObjectTree tree, Ident ident, int level,
		int parentColumnSize)
	{
		int columnSize = label(tree).length() + level * ident.size();
		for (ISerializedObjectTree child : tree.children())
		{
			columnSize = labelColumnSize(child, ident, level + 1, columnSize);
		}
		return Math.max(columnSize, parentColumnSize);
	}

	private String label(ISerializedObjectTree tree)
	{
		return (tree.type().isAnonymousClass() ? tree.type().getSuperclass().getName()
			: tree.type().getName()) +
			(tree.label() != null ? "(" + tree.label() + ")" : "");
	}

}
