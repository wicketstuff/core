package com.inmethod.grid.examples.pages.treegrid;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;
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
public class TreeGridColumnPropertiesPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * constructor.
	 */
	public TreeGridColumnPropertiesPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();

		columns.add(
			new PropertyTreeColumn(new Model("Property 1"), "userObject.property1") {			
				private static final long serialVersionUID = 1L;	
				@Override
				public int getColSpan(IModel rowModel) {
					TreeNode node = (TreeNode) rowModel.getObject();
					if (node.isLeaf()) {
						return 1;
					} else {
						return 6;
					}
				}
			}
			.setReorderable(false)
			.setInitialSize(200)
			.setMinSize(100)
			.setMaxSize(250)
		);
		
		columns.add(
			new PropertyColumn(new Model("Property 2"), "userObject.property2") {
				private static final long serialVersionUID = 1L;
				@Override
				public String getCellCssClass(IModel rowModel, int rowNum) {
					return "property2";
				}			
			}
			.setResizable(false)
		);
		
		columns.add(
			new PropertyColumn(new Model("Property 3"), "userObject.property3") {
				private static final long serialVersionUID = 1L;
				@Override
				public String getCellCssClass(IModel rowModel, int rowNum) {
					return "property3";
				}
			}
			.setHeaderTooltipModel(new Model("Property 3 column"))
		);
		
		columns.add(
			new PropertyColumn(new Model("Property 4"), "userObject.property4")
				.setWrapText(true)
		);
		columns.add(new PropertyColumn(new Model("Property 5"), "userObject.property5"));
		columns.add(new PropertyColumn(new Model("Property 6"), "userObject.property6"));

		TreeModel model = TreeModelFactory.createTreeModel();
		TreeGrid grid = new TreeGrid("grid", model, columns);

		// expand the root node
		grid.getTreeState().expandNode((TreeNode) model.getRoot());
		grid.setContentHeight(23, SizeUnit.EM);

		add(grid);
	}

}
