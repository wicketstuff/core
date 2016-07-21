package com.inmethod.grid.column.editable;

import java.io.Serializable;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.inmethod.grid.column.tree.PropertyTreeColumn;

/**
 * Tree column that allows to edit the property when item is selected.
 * 
 * @author Matej Knopp
 */
public class EditablePropertyTreeColumn<T extends TreeModel & Serializable, I extends TreeNode & Serializable, P, S>
	extends PropertyTreeColumn<T, I, P, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public EditablePropertyTreeColumn(String columnId, IModel<String> headerModel,
		String propertyExpression, S sortProperty)
	{
		super(columnId, headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public EditablePropertyTreeColumn(String columnId, IModel<String> headerModel,
		String propertyExpression)
	{
		super(columnId, headerModel, propertyExpression);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public EditablePropertyTreeColumn(IModel<String> headerModel, String propertyExpression,
		S sortProperty)
	{
		super(headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public EditablePropertyTreeColumn(IModel<String> headerModel, String propertyExpression)
	{
		super(headerModel, propertyExpression);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component newNodeComponent(String id, IModel<I> model)
	{
		return new NodePanel(id, model);
	}

	/**
	 * Panel that either shows a {@link Label} or a {@link TextFieldPanel} depending on item
	 * selection state.
	 * 
	 * @author Matej Knopp
	 * 
	 */
	private class NodePanel extends Panel
	{

		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 * 
		 * @param id
		 * @param rowModel
		 */
		public NodePanel(String id, final IModel<I> rowModel)
		{
			super(id);

			add(new Label("label", new PropertyModel<I>(rowModel, getPropertyExpression()))
			{

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible()
				{
					return !getTreeGrid().isItemEdited(rowModel);
				}

			});

			EditableCellPanel<T, I, P, S> panel = newCellPanel("panel", rowModel,
				getFieldModel(rowModel));
			addValidators(panel.getEditComponent());
			add(panel);
		}

	};

	protected void addValidators(FormComponent<P> component)
	{

	}

	protected IModel<P> getFieldModel(IModel<I> rowModel)
	{
		return new PropertyModel<P>(rowModel, getPropertyExpression());
	}

	protected EditableCellPanel<T, I, P, S> newCellPanel(String componentId, IModel<I> rowModel,
		IModel<P> cellModel)
	{
		return new TextFieldPanel<T, I, P, S>(componentId, cellModel, rowModel, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCellCssClass(IModel<I> rowModel, int rowNum)
	{
		if (isClickToEdit())
		{
			if (getGrid().isItemEdited(rowModel))
			{
				return "imxt-want-prelight imxt-edited-cell";
			}
			else
			{
				return "imxt-want-prelight";
			}
		}
		else
		{
			if (getGrid().isItemEdited(rowModel))
			{
				return "imxt-edited-cell";
			}
			else
			{
				return "";
			}
		}
	}

	@Override
	public boolean cellClicked(IModel<I> rowModel)
	{
		if (!isClickToEdit() || getGrid().isClickRowToSelect() && getGrid().isSelectToEdit())
		{
			return false;
		}
		else
		{
			getGrid().setItemEdit(rowModel, true);
			getGrid().update();
			return true;
		}
	}

	protected boolean isClickToEdit()
	{
		return true;
	}
}
