package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Column that allows a row in grid to be selected. The column cell contains a checkbox which
 * selects and deselects the row. When row selection state is changed, the entire row is updated
 * using Ajax.
 * <p>
 * If the grid is in single selection mode, the column header will remain empty. If the column is in
 * multi selection mode, the column header will contain a checkbox which selects all displayed rows
 * (i.e. the rows on current page for {@link DataGrid} and visible rows in {@link TreeGrid}). When
 * the header checkbox is deselected, it deselects all rows (on all pages). This is to ensure that
 * when user deselects one page, no invisible rows are left selected.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public class CheckBoxColumn<M, I, S> extends AbstractColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new checkbox column.
	 * 
	 * @param columnId
	 *            column id (must be unique within the grid)
	 */
	public CheckBoxColumn(String columnId)
	{
		super(columnId, null);
		setResizable(false);
		setInitialSize(30);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, IModel<I> rowModel)
	{
		return new BodyCheckBoxPanel(componentId, rowModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newHeader(String componentId)
	{
		return new HeadPanel(componentId);
	}

	private void processTag(ComponentTag tag, IModel<I> model)
	{
		if (!isCheckBoxEnabled(model))
		{

			tag.put("disabled", "disabled");

		}
		else if (getGrid() instanceof TreeGrid &&
			((TreeGrid<?, ?, S>)getGrid()).isAutoSelectChildren())
		{

			TreeGrid<?, ?, S> grid = (TreeGrid<?, ?, S>)getGrid();
			Object parent = grid.getTree().getParentNode(model.getObject());
			if (parent != null && grid.getTreeState().isNodeSelected(parent))
			{
				tag.put("disabled", "disabled");
			}
		}
	}

	protected boolean isCheckBoxEnabled(IModel<I> model)
	{
		return true;
	}

	protected boolean isCheckBoxVisible(IModel<I> model)
	{
		return true;
	}

	/**
	 * Panel with checkbox that selects/deselects given row
	 * 
	 * @author Matej Knopp
	 */
	private class BodyCheckBoxPanel extends Panel
	{

		private static final long serialVersionUID = 1L;

		private BodyCheckBoxPanel(String id, final IModel<I> model)
		{
			super(id, model);

			WebMarkupContainer checkbox = new WebMarkupContainer("checkbox")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);

					if (getGrid().isItemSelected(model))
					{
						tag.put("checked", "checked");
					}

					IModel<String> tooltipModel = getRowTooltipModel(model);
					if (tooltipModel != null)
					{
						Object object = tooltipModel.getObject();
						if (object != null)
						{
							tag.put("title", object.toString());
						}
					}

					processTag(tag, model);
				}

				@Override
				public boolean isVisible()
				{
					return isCheckBoxVisible(model);
				}
			};
			checkbox.setOutputMarkupId(true);
			add(checkbox);

			checkbox.add(new AjaxFormSubmitBehavior(getGrid().getForm(), "onclick")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target)
				{

				}

				@Override
				protected void onError(AjaxRequestTarget target)
				{

				}

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					// preserve the entered values in form components
					Form<?> form = getForm();
					form.visitFormComponentsPostOrder(new IVisitor<FormComponent<?>, Void>()
					{
						public void component(FormComponent<?> formComponent, IVisit<Void> visit)
						{
							if (formComponent.isVisibleInHierarchy())
							{
								formComponent.inputChanged();
							}
						}
					});

					boolean selected = getGrid().isItemSelected(model);
					getGrid().selectItem(model, !selected);
					getGrid().update();
				}

				@Override
				protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
				{
					super.updateAjaxAttributes(attributes);

					AjaxCallListener listener = new AjaxCallListener();
					listener.onBeforeSend("var cb = Wicket.$(attrs.c); cb.checked=!cb.checked");
					attributes.getAjaxCallListeners().add(listener);
				}
			});
		}

	}

	/**
	 * Panel that optionally displays checkbox for selecting all visible items / clearing selection
	 * of all item.
	 * 
	 * @author Matej Knopp
	 */
	private class HeadPanel extends Panel
	{

		private static final long serialVersionUID = 1L;

		private HeadPanel(String id)
		{
			super(id);

			add(new HeadCheckBoxPanel("checkbox")
			{

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible()
				{
					return getGrid().isAllowSelectMultiple();
				}
			});

			// this is here to output a span with &nbsp; (so that the column
			// takes the proper
			// height)
			// and also for displaying the tooltip
			add(new WebMarkupContainer("space")
			{

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible()
				{
					return !getGrid().isAllowSelectMultiple();
				}

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					if (getHeaderTooltipModel() != null)
					{
						Object object = getHeaderTooltipModel().getObject();
						if (object != null)
						{
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
	private class HeadCheckBoxPanel extends Panel
	{

		private static final long serialVersionUID = 1L;

		private HeadCheckBoxPanel(String id)
		{
			super(id);

			WebMarkupContainer checkbox = new WebMarkupContainer("checkbox")
			{

				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					if (getHeaderTooltipModel() != null)
					{
						Object object = getHeaderTooltipModel().getObject();
						if (object != null)
						{
							tag.put("title", object.toString());
						}
					}
				}
			};
			add(checkbox);

			checkbox.add(new AjaxFormSubmitBehavior(getGrid().getForm(), "onclick")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target)
				{
				}

				@Override
				protected void onError(AjaxRequestTarget target)
				{

				}

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					// preserve the entered values in form components
					Form<?> form = getForm();
					form.visitFormComponentsPostOrder(new IVisitor<FormComponent<?>, Void>()
					{
						public void component(FormComponent<?> formComponent, IVisit<Void> visit)
						{
							if (formComponent.isVisibleInHierarchy())
							{
								formComponent.inputChanged();
							}
						}
					});

					boolean checked = getRequest().getRequestParameters()
						.getParameterValue("checked")
						.toBoolean();
					if (checked)
						getGrid().selectAllVisibleItems();
					else
						getGrid().resetSelectedItems();
					getGrid().update();
				}

				@Override
				protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
				{
					super.updateAjaxAttributes(attributes);

					CharSequence checkedParameter = "return {'checked': Wicket.$(attrs.c).checked}";
					attributes.getDynamicExtraParameters().add(checkedParameter);

					AjaxCallListener listener = new AjaxCallListener();
					listener.onBeforeSend("var cb = Wicket.$(attrs.c); cb.checked=!cb.checked");
					attributes.getAjaxCallListeners().add(listener);
				}
			});
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCellCssClass(IModel<I> rowModel, int rowNum)
	{
		return "imxt-select";
	}

	@Override
	public String getHeaderCssClass()
	{
		return "imxt-select";
	}

	/**
	 * Overriding this method allows to specify a tooltip for checkbox in each row.
	 * 
	 * @param itemModel
	 *            model for item in given row
	 * @return tooltip model or <code>null</code>
	 */
	protected IModel<String> getRowTooltipModel(IModel<I> itemModel)
	{
		return null;
	}

}
