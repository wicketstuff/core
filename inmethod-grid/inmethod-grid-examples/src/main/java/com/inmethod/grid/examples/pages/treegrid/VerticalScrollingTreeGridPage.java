package com.inmethod.grid.examples.pages.treegrid;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.examples.pages.BaseExamplePage;
import com.inmethod.grid.examples.tree.TreeModelFactory;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Table with {@link TreeGrid} with vertical scrollbar.
 * 
 * @author Matej Knopp
 */
public class VerticalScrollingTreeGridPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public VerticalScrollingTreeGridPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		columns.add(new PropertyTreeColumn(new Model("Property 1"), "userObject.property1"));
		columns.add(new PropertyColumn(new Model("Property 2"), "userObject.property2"));
		columns.add(new PropertyColumn(new Model("Property 3"), "userObject.property3"));
		columns.add(new PropertyColumn(new Model("Property 4"), "userObject.property4"));
		columns.add(new PropertyColumn(new Model("Property 5"), "userObject.property5"));
		columns.add(new PropertyColumn(new Model("Property 6"), "userObject.property6"));
		
		TreeModel model = TreeModelFactory.createTreeModel();
		TreeGrid grid = new TreeGrid("grid", model, columns);
		
		grid.setContentHeight(23, SizeUnit.EM);
		
		// expand the root node
		grid.getTreeState().expandAll();
		
		add(grid);
	}

}
