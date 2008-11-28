package com.inmethod.grid.treegrid;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGridRow;
import com.inmethod.grid.common.AttachPrelightBehavior;

/**
 * Body of {@link TreeGrid}. Contains the rows for tree nodes.
 * 
 * @author Matej Knopp
 */
public abstract class TreeGridBody extends AbstractTree {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param model
	 */
	public TreeGridBody(String id, IModel model) {
		super(id, model);
		setRenderBodyOnly(true);
	}

	@Override
	protected void populateTreeItem(final WebMarkupContainer item, int level) {
		AbstractGridRow row = new AbstractTreeGridRow("item", item.getDefaultModel(), level) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<IGridColumn> getActiveColumns() {
				return TreeGridBody.this.getActiveColumns();
			}

			@Override
			protected int getRowNumber() {
				return -1;
			}
		};
		item.add(row);
		item.add(new AbstractBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag) {
				CharSequence klass = "imxt-want-prelight imxt-grid-row";
				if (getTreeState().isNodeSelected(item.getDefaultModelObject())) {
					klass = klass + " imxt-selected";
				}
				tag.put("class", klass);
			}

		});
		item.add(new AttachPrelightBehavior());
		rowPopulated(item);
	}

	@Override
	protected void addComponent(AjaxRequestTarget target, Component component) {
		if (component == this) {
			// can't refresh this component directly because of setRenderBodyOnly(true) that's set
			// in
			// constructor
			target.addComponent(findParent(TreeGrid.class));
		} else {
			super.addComponent(target, component);
		}
	}

	/**
	 * @see org.apache.wicket.markup.html.tree.AbstractTree#isForceRebuildOnSelectionChange()
	 */
	protected boolean isForceRebuildOnSelectionChange() {
		return false;
	}

	boolean isNodeExpanded2(Object object)
	{
		return super.isNodeExpanded(object);
	}
	
	protected abstract Collection<IGridColumn> getActiveColumns();

	protected abstract void rowPopulated(WebMarkupContainer item);
}
