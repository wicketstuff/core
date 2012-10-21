package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Reports.Ident;

public class TreeSizeReport implements ISerializedObjectTreeProcessor
{

	private final static Logger LOG = LoggerFactory.getLogger(TreeSizeReport.class);

	@Override
	public void process(ISerializedObjectTree tree)
	{
		Ident ident = new Ident(2, ' ');
		int labelColumnSize = labelColumnSize(tree, ident, 0, 0);

		StringBuilder sb = new StringBuilder();
		sb.append("\n-----------------------------\n");
		process(tree, sb, ident, 0, tree.size() + tree.childSize(), labelColumnSize);
		LOG.debug(sb.toString());
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
