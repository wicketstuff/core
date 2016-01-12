package com.inmethod.grid.column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.datagrid.DataGrid;

/**
 * Adapter that allows using {@link DataTable} columns from wicket extensions in an
 * {@link AbstractGrid}.
 * <p>
 * Note that due to different internal structure of {@link AbstractGrid} and {@link DataTable} this
 * adapter might not work for every {@link IColumn} implementation. Basically if the implementation
 * relies on the parent instance given in {@link ICellPopulator#populateItem(Item, String, IModel)}
 * it will probably not work properly with this adapter, as the parent instance this adapter uses is
 * only temporary.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public class WicketColumnAdapter<M, I, S> extends AbstractColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;
	private final IColumn<I, S> delegate;

	/**
	 * Constructor
	 * 
	 * @param columnId
	 *            column identifier (must be unique within the grid)
	 * @param column
	 *            {@link IColumn} implementation
	 */
	public WicketColumnAdapter(String columnId, IColumn<I, S> column)
	{
		super(columnId, null, null);
		delegate = column;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, IModel<I> rowModel)
	{
		Item<ICellPopulator<I>> item;
		if (getGrid() instanceof DataGrid)
		{
			item = parent.findParent(Item.class);
		}
		else
		{
			// TODO: is this ever invoked? Seems strange. akiraly
			item = new Item("temp", 0, rowModel);
		}
		delegate.populateItem(item, componentId, rowModel);
		Component component = item.get(componentId);
		item.remove(component);
		return component;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newHeader(String componentId)
	{
		return delegate.getHeader(componentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCellCssClass(IModel<I> rowModel, int rowNum)
	{
		if (delegate instanceof IStyledColumn)
		{
			return ((IStyledColumn<I, S>)delegate).getCssClass();
		}
		else
		{
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getSortProperty()
	{
		if (delegate.isSortable())
		{
			return delegate.getSortProperty();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns the specified {@link IColumn} array as list of {@link WicketColumnAdapter}s that can
	 * be given to an {@link AbstractGrid}. The column identifiers are generated.
	 * 
	 * @param columns
	 *            array of {@link IColumn}s
	 * @return list of {@link IGridColumn}s
	 */
	public static <T, M, I, S> List<IGridColumn<M, I, S>> wrapColumns(IColumn<I, S> columns[])
	{
		return wrapColumns(Arrays.asList(columns));
	}


	/**
	 * Returns the specified {@link IColumn} list as list of {@link WicketColumnAdapter}s that can
	 * be given to an {@link AbstractGrid}. The column identifiers are generated.
	 * 
	 * @param columns
	 *            list of {@link IColumn}s
	 * @return list of {@link IGridColumn}s
	 */
	public static <M, I, S> List<IGridColumn<M, I, S>> wrapColumns(List<IColumn<I, S>> columns)
	{
		List<IGridColumn<M, I, S>> result = new ArrayList<IGridColumn<M, I, S>>(columns.size());
		int i = 0;
		for (IColumn<I, S> column : columns)
		{
			result.add(new WicketColumnAdapter<M, I, S>("column" + i++, column));
		}
		return result;
	}
}