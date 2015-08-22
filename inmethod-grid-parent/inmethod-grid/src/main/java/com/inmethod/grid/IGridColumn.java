package com.inmethod.grid;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Represents a column in a {@link DataGrid} or a {@link TreeGrid}.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public interface IGridColumn<M, I, S> extends IDetachable
{

	/**
	 * Returns the column id. Each column must have a unique Id. The only allowed characters in a
	 * column id are alphanumeric characters, dash, dot and underscore.
	 * 
	 * @return column identifier
	 */
	public String getId();

	/**
	 * Creates a new component for column header.
	 * 
	 * @param componentId
	 *            required id that the component must have
	 * @return Component representing a column header
	 */
	Component newHeader(String componentId);

	/**
	 * Returns whether this column is uses a component for cell in given row (not lightweight) or an
	 * {@link IRenderable} implementation. Generally, if the cell is non-interactive (label only),
	 * it's better to use an {@link IRenderable} implementation, as it has zero memory footprint
	 * 
	 * @param rowModel
	 *            model for given row
	 * @return <code>true</code> if the cell is lightweight, <code>false</code> otherwise
	 */
	public boolean isLightWeight(IModel<I> rowModel);

	/**
	 * Creates a new cell component. This method is called for rows that are not lightweight (
	 * {@link #isLightWeight(IModel)} returns false ).
	 * 
	 * @param parent
	 *            Parent component. This is passed in only for convenience, the method
	 *            implementation is <em>not</em> supposed to add the newly created component to the
	 *            parent.
	 * @param componentId
	 *            required id of newly created components
	 * @param rowModel
	 *            model for given row
	 * @return new cell component
	 */
	Component newCell(final WebMarkupContainer parent, final String componentId,
		final IModel<I> rowModel);

	/**
	 * Creates a new {@link IRenderable} instance that is used to render to render the output of
	 * cell for given row. This method is called for rows that are lightweight (
	 * {@link #isLightWeight(IModel)} returns <code>true</code> ).
	 * 
	 * @param rowModel
	 *            model for given row
	 * @return {@link IRenderable} instance
	 */
	IRenderable<I> newCell(final IModel<I> rowModel);

	/**
	 * Result of this method determines whether the column is sortable and in case it is, also
	 * determines the sort property. If the column is sortable and user sorts by it, the sort
	 * property can then be obtained through {@link IGridSortState}.
	 * 
	 * @see IGridSortState.ISortStateColumn#getPropertyName()
	 * @return sort property or <code>null</code> if the column is not sortable
	 */
	public S getSortProperty();

	/**
	 * Returns whether user will be able to resize this column. If the column is resizable,
	 * {@link #getSizeUnit()} must return {@link SizeUnit#PX}, otherwise an
	 * {@link IllegalStateException} will be thrown.
	 * 
	 * @return <code>true</code> if the column is resizable, <code>false</code> otherwise.
	 */
	public boolean isResizable();

	/**
	 * Returns the initial size of column. The unit is determined by {@link #getSizeUnit()}.
	 * 
	 * @return initial column size
	 */
	public int getInitialSize();

	/**
	 * Returns the unit in which sizes are specified. If the column is resizable, this method must
	 * always return {@link SizeUnit#PX}.
	 * 
	 * @return size unit
	 */
	public SizeUnit getSizeUnit();

	/**
	 * Returns the minimal size of resizable column. Return values not greater than zero will be
	 * ignored.
	 * 
	 * @return minimal size
	 */
	public int getMinSize();

	/**
	 * Returns the maximal size of resizable column. Return values not greater than zero will be
	 * ignored.
	 * 
	 * @return maximal size
	 */
	public int getMaxSize();

	/**
	 * Returns whether user will be allowed to reorder this column (i.e. change column position
	 * relative to other columns).
	 * 
	 * @return <code>true</code> if the column is reorderable, <code>false</code> otherwise
	 */
	public boolean isReorderable();

	/**
	 * Returns the CSS class for this column header. The class is applied to the appropriate
	 * &lt;th&gt; element in the grid.
	 * 
	 * @return CSS class for this column header or <code>null</code>
	 */
	public String getHeaderCssClass();

	/**
	 * Returns the cell specified by rowModel. The class is applied to the appropriate &lt;td&gt;
	 * element in the grid.
	 * 
	 * @param rowModel
	 *            model for given row
	 * @param rowNum
	 *            index of row for {@link DataGrid}, -1 for {@link TreeGrid}
	 * @return cell style class or <code>null</code>
	 */
	public String getCellCssClass(final IModel<I> rowModel, final int rowNum);

	/**
	 * Returns the spanning value for cell specified by rowModel. The cell can span over certain
	 * number of adjacent cells. The value determines how many cell will this column take. Values
	 * less than 2 mean that no extra spanning will be done.
	 * 
	 * @param rowModel
	 *            model for given row
	 * @return colspan value
	 */
	public int getColSpan(IModel<I> rowModel);

	/**
	 * Invoked before the first render of the grid. Column that need grid reference can implement
	 * this method and store the reference.
	 * 
	 * @param grid
	 *            grid that contains this column
	 */
	public void setGrid(AbstractGrid<M, I, S> grid);

	/**
	 * Determines the behavior when there is more text in cell than it fits in it. If the method
	 * returns <code>true</code>, the text will be wrapped and row height increased. If the method
	 * returns <code>false</code>, the remaining part of text will be hidden.
	 * 
	 * @return whether this colulmn's text should wrap or not
	 */
	public boolean getWrapText();

	/**
	 * Allows to override default behavior when a row is clicked. Depending on grid settings the
	 * default behavior can select the item. If this method returns <code>true</code> the default
	 * behavior will be suppressed.
	 * 
	 * @param rowModel
	 *            Model for clicked row
	 * @return <code>true</code> if the default behavior when row is clicked should be supressed,
	 *         <code>false</code> otherwise.
	 */
	public boolean cellClicked(IModel<I> rowModel);
}
