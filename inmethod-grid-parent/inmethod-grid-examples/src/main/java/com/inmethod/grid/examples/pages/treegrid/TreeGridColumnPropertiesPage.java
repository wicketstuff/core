package com.inmethod.grid.examples.pages.treegrid;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.examples.pages.BaseExamplePage;
import com.inmethod.grid.examples.tree.TreeModelFactory;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Page with {@link TreeGrid} that shows various column properties.
 * 
 * @author Matej Knopp
 */
public class TreeGridColumnPropertiesPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * constructor.
	 */
	public TreeGridColumnPropertiesPage()
	{
		List<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode, String>> columns =
				new ArrayList<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode, String>>();

		columns.add(new PropertyTreeColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 1"), "userObject.property1")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getColSpan(IModel<DefaultMutableTreeNode> rowModel)
			{
				TreeNode node = rowModel.getObject();
				if (node.isLeaf())
				{
					return 1;
				}
				else
				{
					return 6;
				}
			}
		}.setReorderable(false).setInitialSize(200).setMinSize(100).setMaxSize(250));

		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 2"), "userObject.property2")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getCellCssClass(IModel<DefaultMutableTreeNode> rowModel, int rowNum)
			{
				return "property2";
			}
		}.setResizable(false));

		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 3"), "userObject.property3")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getCellCssClass(IModel<DefaultMutableTreeNode> rowModel, int rowNum)
			{
				return "property3";
			}
		}.setHeaderTooltipModel(Model.of("Property 3 column")));

		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 4"), "userObject.property4").setWrapText(true));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 5"), "userObject.property5"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 6"), "userObject.property6"));

		DefaultTreeModel model = TreeModelFactory.createTreeModel();
		TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid =
				new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String>(
			"grid", model, columns);

		// expand the root node
		grid.getTreeState().expandNode(model.getRoot());
		grid.setContentHeight(23, SizeUnit.EM);

		add(grid);
	}

}
