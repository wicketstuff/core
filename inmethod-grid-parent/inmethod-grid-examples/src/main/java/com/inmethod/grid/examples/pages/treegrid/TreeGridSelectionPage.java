package com.inmethod.grid.examples.pages.treegrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.CheckBoxColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.examples.pages.BaseExamplePage;
import com.inmethod.grid.examples.tree.TreeBean;
import com.inmethod.grid.examples.tree.TreeModelFactory;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Page with {@link TreeGrid} that allows user to select items.
 * 
 * @author Matej Knopp
 */
public class TreeGridSelectionPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	private Label selectionLabel;

	/**
	 * Constructor.
	 */
	public TreeGridSelectionPage()
	{
		List<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode, String>> columns =
				new ArrayList<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode, String>>();

		columns.add(new CheckBoxColumn<DefaultTreeModel, DefaultMutableTreeNode, String>("checkBox"));
		columns.add(new PropertyTreeColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 1"), "userObject.property1"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 2"), "userObject.property2"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 3"), "userObject.property3"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 4"), "userObject.property4"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 5"), "userObject.property5"));
		columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
			Model.of("Property 6"), "userObject.property6"));

		DefaultTreeModel model = TreeModelFactory.createTreeModel();
		final TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid = new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String>(
			"grid", model, columns)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onItemSelectionChanged(IModel<DefaultMutableTreeNode> item, boolean newValue)
			{
				super.onItemSelectionChanged(item, newValue);

				// refresh the selection label when an item gets selected/deselected.
				AjaxRequestTarget target = getRequestCycle().find(AjaxRequestTarget.class);
				target.add(selectionLabel);
			}
		};

		grid.setAllowSelectMultiple(true);
		grid.setClickRowToSelect(true);
		grid.setContentHeight(23, SizeUnit.EM);

		// expand the root node
		grid.getTreeState().expandNode(model.getRoot());

		add(grid);

		IModel<String> selectedItemsModel = new Model<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return selectedItemsAsString(grid);
			}
		};
		add(selectionLabel = new Label("currentSelection", selectedItemsModel));
		selectionLabel.setOutputMarkupId(true);

		addOptionLinks(grid);
	}

	private String selectedItemsAsString(TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid)
	{
		StringBuilder res = new StringBuilder();
		Collection<IModel<DefaultMutableTreeNode>> selected = grid.getSelectedItems();
		for (IModel<DefaultMutableTreeNode> model : selected)
		{
			DefaultMutableTreeNode node = model.getObject();
			TreeBean bean = (TreeBean)node.getUserObject();
			if (res.length() > 0)
			{
				res.append(", ");
			}
			res.append(bean.getProperty1());
		}
		return res.toString();
	}

	private void addOptionLinks(final TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid)
	{

		add(new Link<Void>("selectMultipleOn")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setAllowSelectMultiple(true);
			}

			@Override
			public boolean isEnabled()
			{
				return !grid.isAllowSelectMultiple();
			}
		});

		add(new Link<Void>("selectMultipleOff")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setAllowSelectMultiple(false);
			}

			@Override
			public boolean isEnabled()
			{
				return grid.isAllowSelectMultiple();
			}
		});

	}

}
