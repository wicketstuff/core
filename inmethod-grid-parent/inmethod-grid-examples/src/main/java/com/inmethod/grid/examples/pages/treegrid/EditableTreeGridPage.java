package com.inmethod.grid.examples.pages.treegrid;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.editable.EditablePropertyColumn;
import com.inmethod.grid.column.editable.EditablePropertyTreeColumn;
import com.inmethod.grid.column.editable.SubmitCancelColumn;
import com.inmethod.grid.examples.pages.BaseExamplePage;
import com.inmethod.grid.examples.tree.TreeModelFactory;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Page with {@link TreeGrid} that allows editing selected items.
 * 
 * @author Matej Knopp
 */
public class EditableTreeGridPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EditableTreeGridPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		columns.add(new SubmitCancelColumn("edit", new Model("Edit")));
		columns.add(new EditablePropertyTreeColumn(new Model("Property 1"), "userObject.property1"));
		columns.add(new EditablePropertyColumn(new Model("Property 2"), "userObject.property2"));
		columns.add(new EditablePropertyColumn(new Model("Property 3"), "userObject.property3"));
		columns.add(new EditablePropertyColumn(new Model("Property 4"), "userObject.property4"));
		columns.add(new EditablePropertyColumn(new Model("Property 5"), "userObject.property5"));
		columns.add(new EditablePropertyColumn(new Model("Property 6"), "userObject.property6"));		
		
		TreeModel model = TreeModelFactory.createTreeModel();
		TreeGrid grid = new TreeGrid("grid", model, columns);
		
		grid.setContentHeight(23, SizeUnit.EM);
		grid.setAllowSelectMultiple(true);
		grid.setClickRowToSelect(true);
		grid.setClickRowToDeselect(false);
		grid.setSelectToEdit(true);
		
		// expand the root node
		grid.getTreeState().expandNode((TreeNode) model.getRoot());
		
		add(grid);
	}

}
