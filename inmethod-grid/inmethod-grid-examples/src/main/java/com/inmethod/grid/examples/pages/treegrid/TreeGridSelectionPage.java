package com.inmethod.grid.examples.pages.treegrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

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
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.examples.pages.BaseExamplePage;
import com.inmethod.grid.examples.tree.TreeBean;
import com.inmethod.grid.examples.tree.TreeModelFactory;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Page with {@link TreeGrid} that allows user to select items.
 * 
 * @author Matej Knopp
 */
public class TreeGridSelectionPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	private Label selectionLabel;
	
	/**
	 * Constructor.
	 */
	public TreeGridSelectionPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		columns.add(new CheckBoxColumn("checkBox"));
		columns.add(new PropertyTreeColumn(new Model("Property 1"), "userObject.property1"));
		columns.add(new PropertyColumn(new Model("Property 2"), "userObject.property2"));
		columns.add(new PropertyColumn(new Model("Property 3"), "userObject.property3"));
		columns.add(new PropertyColumn(new Model("Property 4"), "userObject.property4"));
		columns.add(new PropertyColumn(new Model("Property 5"), "userObject.property5"));
		columns.add(new PropertyColumn(new Model("Property 6"), "userObject.property6"));
		
		TreeModel model = TreeModelFactory.createTreeModel();
		final TreeGrid grid = new TreeGrid("grid", model, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onItemSelectionChanged(IModel item, boolean newValue) {
				super.onItemSelectionChanged(item, newValue);
				
				// refresh the selection label when an item gets selected/deselected.
				AjaxRequestTarget target = AjaxRequestTarget.get();
				target.addComponent(selectionLabel);
			}
		};
		
		grid.setAllowSelectMultiple(true);
		grid.setClickRowToSelect(true);
		grid.setContentHeight(23, SizeUnit.EM);
		
		// expand the root node
		grid.getTreeState().expandNode((TreeNode) model.getRoot());
		
		add(grid);
		
		IModel selectedItemsModel = new Model() {
			private static final long serialVersionUID = 1L;

			@Override
			public Serializable getObject() {
				return selectedItemsAsString(grid);
			}
		};
		add(selectionLabel = new Label("currentSelection", selectedItemsModel));
		selectionLabel.setOutputMarkupId(true);
		
		addOptionLinks(grid);
	}

	private String selectedItemsAsString(AbstractGrid grid) {
		StringBuilder res = new StringBuilder();
		Collection<IModel> selected = grid.getSelectedItems();
		for (IModel model : selected) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getObject();
			TreeBean bean = (TreeBean)node.getUserObject();
			if (res.length() > 0) {
				res.append(", ");
			}
			res.append(bean.getProperty1());
		}
		return res.toString();		
	}
	
	private void addOptionLinks(final TreeGrid grid) {	
		
		add(new Link("selectMultipleOn") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				grid.setAllowSelectMultiple(true);
			}
			@Override
			public boolean isEnabled() {
				return !grid.isAllowSelectMultiple();
			}
		});
		
		add(new Link("selectMultipleOff") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				grid.setAllowSelectMultiple(false);
			}
			@Override
			public boolean isEnabled() {
				return grid.isAllowSelectMultiple();
			}
		});		

	}
	
}
