package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IRenderable;
import com.inmethod.grid.column.editable.EditablePropertyColumn;

/**
 * Convenience implementation of {@link IGridColumn}. This class should be used as base for
 * lightweight columns.
 * <p>
 * Contains getters and setters for most properties and takes care of the header component. The only
 * method necessary to implement is {@link #newCell(IModel)}.
 * <p>
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @see EditablePropertyColumn
 * @author Matej Knopp
 */
public abstract class AbstractLightWeightColumn<M, I, S> extends AbstractColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates instance with specified column id, header model and sort property.
	 * 
	 * @param columnId
	 *            column identifier - must be unique within the grid
	 * @param headerModel
	 *            model for column title
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public AbstractLightWeightColumn(String columnId, IModel<String> headerModel,
		S sortProperty)
	{
		super(columnId, headerModel, sortProperty);
	}

	/**
	 * Creates instance with specified column id and header model
	 * 
	 * @param columnId
	 *            column identifier - must be unique within the grid
	 * @param headerModel
	 *            model for column title
	 */

	public AbstractLightWeightColumn(String columnId, IModel<String> headerModel)
	{
		super(columnId, headerModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLightWeight(IModel<I> rowModel)
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IRenderable<I> newCell(IModel<I> rowModel);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, IModel<I> rowModel)
	{
		return null;
	}
}
