package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.CancelEventIfNoAjaxDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Column that allows a row in grid to be selected. The column cell contains a
 * checkbox which selects and deselects the row. When row selection state is
 * changed, the entire row is updated using Ajax.
 * <p>
 * If the grid is in single selection mode, the column header will remain empty.
 * If the column is in multi selection mode, the column header will contain a
 * checkbox which selects all displayed rows (i.e. the rows on current page for
 * {@link DataGrid} and visible rows in {@link TreeGrid}). When the header
 * checkbox is deselected, it deselects all rows (on all pages). This is to
 * ensure that when user deselects one page, no invisible rows are left
 * selected.
 * 
 * @author Matej Knopp
 */
public class CheckBoxColumn extends AbstractColumn {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new checkbox column.
	 * 
	 * @param columnId
	 *            column id (must be unique within the grid)
	 */
	public CheckBoxColumn(String columnId) {
		super(columnId, null);
		setResizable(false);
		setInitialSize(30);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId,
			IModel rowModel) {
		return new BodyCheckBoxPanel(componentId, rowModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newHeader(String componentId) {
		return new HeadPanel(componentId);
	}

	private void processTag(ComponentTag tag, IModel model) {
		if (!isCheckBoxEnabled(model)) {

			tag.put("disabled", "disabled");

		} else if (getGrid() instanceof TreeGrid				
				&& ((TreeGrid) getGrid()).isAutoSelectChildren()) {
			
			TreeGrid grid = (TreeGrid) getGrid();
			Object parent = grid.getTree().getParentNode(model.getObject());
			if (parent != null && grid.getTreeState().isNodeSelected(parent)) {
				tag.put("disabled", "disabled");
			}
		}
	}

	protected boolean isCheckBoxEnabled(IModel model) {
		return true;
	}

	protected boolean isCheckBoxVisible(IModel model) {
		return true;
	}

	/**
	 * Panel with checkbox that selects/deselects given row
	 * 
	 * @author Matej Knopp
	 */
	private class BodyCheckBoxPanel extends Panel {

		private static final long serialVersionUID = 1L;

		private BodyCheckBoxPanel(String id, final IModel model) {
			super(id, model);

			WebMarkupContainer checkbox = new WebMarkupContainer("checkbox") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);

					if (getGrid().isItemSelected(model)) {
						tag.put("checked", "checked");
					}

					IModel tooltipModel = getRowTooltipModel(model);
					if (tooltipModel != null) {
						Object object = tooltipModel.getObject();
						if (object != null) {
							tag.put("title", object.toString());
						}
					}

					processTag(tag, model);
				}

				@Override
				public boolean isVisible() {
					return isCheckBoxVisible(model);
				}
			};
			checkbox.setOutputMarkupId(true);
			add(checkbox);

			checkbox.add(new AjaxFormSubmitBehavior(getGrid().getForm(),
					"onclick") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target) {

				}

				@Override
				protected void onError(AjaxRequestTarget target) {

				}

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					// preserve the entered values in form components
					Form form = getForm();
					form
							.visitFormComponentsPostOrder(new FormComponent.AbstractVisitor() {
								public void onFormComponent(
										final FormComponent formComponent) {
									if (formComponent.isVisibleInHierarchy()) {
										formComponent.inputChanged();
									}
								}
							});

					boolean selected = getGrid().isItemSelected(model);
					getGrid().selectItem(model, !selected);
					getGrid().update();
				}

				@Override
				protected CharSequence getPreconditionScript() {
					return "window.setTimeout(function(){this.checked=!this.checked}.bind(this),0);"
							+ super.getPreconditionScript();
				}

				@Override
				protected IAjaxCallDecorator getAjaxCallDecorator() {
					return new CancelEventIfNoAjaxDecorator();
				}
			});
		}

	}

	/**
	 * Panel that optionally displays checkbox for selecting all visible items /
	 * clearing selection of all item.
	 * 
	 * @author Matej Knopp
	 */
	private class HeadPanel extends Panel {

		private static final long serialVersionUID = 1L;

		private HeadPanel(String id) {
			super(id);

			add(new HeadCheckBoxPanel("checkbox") {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible() {
					return getGrid().isAllowSelectMultiple();
				}
			});

			// this is here to output a span with &nbsp; (so that the column
			// takes the proper
			// height)
			// and also for displaying the tooltip
			add(new WebMarkupContainer("space") {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible() {
					return !getGrid().isAllowSelectMultiple();
				}

				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);
					if (getHeaderTooltipModel() != null) {
						Object object = getHeaderTooltipModel().getObject();
						if (object != null) {
							tag.put("title", object.toString());
						}
					}
				}
			});
		}
	};

	/**
	 * The actual panel with checkbox in column header
	 * 
	 * @author Matej Knopp
	 */
	private class HeadCheckBoxPanel extends Panel {

		private static final long serialVersionUID = 1L;

		private HeadCheckBoxPanel(String id) {
			super(id);

			WebMarkupContainer checkbox = new WebMarkupContainer("checkbox") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);
					if (getHeaderTooltipModel() != null) {
						Object object = getHeaderTooltipModel().getObject();
						if (object != null) {
							tag.put("title", object.toString());
						}
					}
				}
			};
			add(checkbox);

			checkbox.add(new AjaxFormSubmitBehavior(getGrid().getForm(),
					"onclick") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target) {
				}

				@Override
				protected void onError(AjaxRequestTarget target) {

				}

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					// preserve the entered values in form components
					Form form = getForm();
					form
							.visitFormComponentsPostOrder(new FormComponent.AbstractVisitor() {
								public void onFormComponent(
										final FormComponent formComponent) {
									if (formComponent.isVisibleInHierarchy()) {
										formComponent.inputChanged();
									}
								}
							});

					boolean checked = Strings.toBoolean(getRequest()
							.getParameter("checked"));
					if (checked)
						getGrid().selectAllVisibleItems();
					else
						getGrid().resetSelectedItems();
					getGrid().update();
				}

				@Override
				public CharSequence getCallbackUrl() {
					return super.getCallbackUrl() + "&checked='+this.checked+'";
				}

				@Override
				protected CharSequence getPreconditionScript() {
					return "window.setTimeout(function(){this.checked=!this.checked}.bind(this),0);"
							+ super.getPreconditionScript();
				}

				@Override
				protected IAjaxCallDecorator getAjaxCallDecorator() {
					return new CancelEventIfNoAjaxDecorator();
				}
			});
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCellCssClass(IModel rowModel, int rowNum) {
		return "imxt-select";
	}

	@Override
	public String getHeaderCssClass() {
		return "imxt-select";
	}

	/**
	 * Overriding this method allows to specify a tooltip for checkbox in each
	 * row.
	 * 
	 * @param itemModel
	 *            model for item in given row
	 * @return tooltip model or <code>null</code>
	 */
	protected IModel getRowTooltipModel(IModel itemModel) {
		return null;
	}

}
