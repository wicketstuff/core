package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IRenderable;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.common.AbstractGrid;

/**
 * Convenience implementation of {@link IGridColumn}. This class should be used as base for
 * non-lightweight columns.
 * <p>
 * Contains getters and setters for most properties and takes care of the header component. The only
 * method necessary to implement is {@link #newCell(WebMarkupContainer, String, IModel)}.
 * <p>
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @see AbstractLightWeightColumn
 * @author Matej Knopp
 */
public abstract class AbstractColumn<M, I, S> implements IGridColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;

	private final String columnId;
	private final IModel<String> headerModel;
	private IModel<String> headerTooltipModel;
	private final S sortProperty;

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
	public AbstractColumn(String columnId, IModel<String> headerModel, S sortProperty)
	{
		this.columnId = columnId;
		this.headerModel = headerModel;
		this.sortProperty = sortProperty;
	}

	/**
	 * Creates instance with specified column id and header model
	 * 
	 * @param columnId
	 *            column identifier - must be unique within the grid
	 * @param headerModel
	 *            model for column title
	 */
	public AbstractColumn(String columnId, IModel<String> headerModel)
	{
		this(columnId, headerModel, null);
	}

	/**
	 * Sets the model for header tooltip.
	 * 
	 * @param headerTooltipModel
	 *            model for header tooltip
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setHeaderTooltipModel(IModel<String> headerTooltipModel)
	{
		this.headerTooltipModel = headerTooltipModel;
		return this;
	}

	/**
	 * Returns the model for header tooltip
	 * 
	 * @return model for header tooltip
	 */
	public IModel<String> getHeaderTooltipModel()
	{
		return headerTooltipModel;
	}

	/**
	 * Returns the model for header (caption)
	 * 
	 * @return header model
	 */
	public IModel<String> getHeaderModel()
	{
		return headerModel;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCellCssClass(IModel<I> rowModel, int rowNum)
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getColSpan(IModel<I> rowModel)
	{
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getHeaderCssClass()
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getId()
	{
		return columnId;
	}

	private int initialSize = 150;

	/**
	 * Sets the initial size for this column. The unit depends on {@link #setSizeUnit(SizeUnit)}. By
	 * default the unit is pixels.
	 * 
	 * @param initialSize
	 *            initial size of the column
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setInitialSize(int initialSize)
	{
		this.initialSize = initialSize;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInitialSize()
	{
		return initialSize;
	}

	private SizeUnit sizeUnit = SizeUnit.PX;

	/**
	 * Sets the size unit for this column. The default size unit is {@link SizeUnit#PX}.
	 * 
	 * @param sizeUnit
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setSizeUnit(SizeUnit sizeUnit)
	{
		this.sizeUnit = sizeUnit;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public SizeUnit getSizeUnit()
	{
		return sizeUnit;
	}

	private int maxSize = 0;

	/**
	 * Sets the maximal size of this column. Only relevant if the column is resizable and the column
	 * unit is {@link SizeUnit#PX} (pixels).
	 * 
	 * @param maxSize
	 *            maximal column size
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMaxSize()
	{
		return maxSize;
	}

	private int minSize = 0;

	/**
	 * Sets the minimal size of this column. Only relevant if the column is resizable and the column
	 * unit is {@link SizeUnit#PX} (pixels).
	 * 
	 * @param minSize
	 *            minimal column size
	 * @return <code>this</code> (useful for method chaining)
	 */

	public AbstractColumn<M, I, S> setMinSize(int minSize)
	{
		this.minSize = minSize;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMinSize()
	{
		return minSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public S getSortProperty()
	{
		return sortProperty;
	}

	private boolean reorderable = true;

	/**
	 * Sets whether the user will be able to drag and reorder this column (true by default).
	 * 
	 * @param reorderable
	 *            <code>true</code> if the column will be reorderable, <code>false</code> otherwise
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setReorderable(boolean reorderable)
	{
		this.reorderable = reorderable;
		return this;
	}

	/**
	 * #see {@link IGridColumn#isReorderable()}
	 */
	public boolean isReorderable()
	{
		return reorderable;
	}

	private boolean resizable = true;

	/**
	 * Sets whether the user will be able to resize this column (true by default). In order for the
	 * column to be resizable, the size unit must be {@link SizeUnit#PX}.
	 * 
	 * @see #setSizeUnit(SizeUnit)
	 * @param resizable
	 *            <code>true</code> if the column will be resizable, <code>false</code> otherwise
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setResizable(boolean resizable)
	{
		this.resizable = resizable;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isResizable()
	{
		return resizable;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isLightWeight(IModel<I> rowModel)
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract Component newCell(WebMarkupContainer parent, String componentId,
		IModel<I> rowModel);

	/**
	 * {@inheritDoc}
	 */
	public IRenderable<I> newCell(IModel<I> rowModel)
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Component newHeader(String componentId)
	{
		return new Label(componentId, getHeaderModel())
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
	}

	/**
	 * {@inheritDoc}
	 */
	public void detach()
	{

	}

	private AbstractGrid<M, I, S> grid = null;

	/**
	 * {@inheritDoc}
	 */
	public void setGrid(AbstractGrid<M, I, S> grid)
	{
		if (this.grid != null && this.grid != grid)
		{
			throw new IllegalStateException(
				"One column instance can not be used with multiple grid instances.");
		}
		this.grid = grid;
	}

	/**
	 * Returns the grid instance associated with this column.
	 * 
	 * @return grid instance
	 */
	public AbstractGrid<M, I, S> getGrid()
	{
		return grid;
	}

	private boolean wrapText = false;

	/**
	 * Sets whether the text in column will be wrapped when it is too long to fit the column (
	 * <code>false</code> by default).
	 * 
	 * @param wrapText
	 *            <code>true<code> if the text will be wrapped, <code>false</code> otherwise.
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractColumn<M, I, S> setWrapText(boolean wrapText)
	{
		this.wrapText = wrapText;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getWrapText()
	{
		return wrapText;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean cellClicked(IModel<I> rowModel)
	{
		return false;
	}
}
