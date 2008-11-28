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
 * @author Matej Knopp
 */
public class WicketColumnAdapter extends AbstractColumn {

	private static final long serialVersionUID = 1L;
	private final IColumn delegate;

	/**
	 * Constructor
	 * 
	 * @param columnId
	 *            column identifier (must be unique within the grid)
	 * @param column
	 *            {@link IColumn} implementation
	 */
	public WicketColumnAdapter(String columnId, IColumn column) {
		super(columnId, null, null);
		this.delegate = column;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, IModel rowModel) {
		Item item;
		if (getGrid() instanceof DataGrid) {
			item = (Item) parent.findParent(Item.class);
		} else {
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
	public Component newHeader(String componentId) {
		return delegate.getHeader(componentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCellCssClass(IModel rowModel, int rowNum) {
		if (delegate instanceof IStyledColumn) {
			return ((IStyledColumn) delegate).getCssClass();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSortProperty() {
		if (delegate.isSortable()) {
			return delegate.getSortProperty();
		} else {
			return null;
		}
	}

	/**
	 * Returns the specified {@link IColumn} array as list of {@link WicketColumnAdapter}s that
	 * can be given to an {@link AbstractGrid}. The column identifiers are generated.
	 * @param columns
	 * 		array of {@link IColumn}s 
	 * @return
	 * 		list of {@link IGridColumn}s
	 */
	public static List<IGridColumn> wrapColumns(IColumn columns[]) {
		return wrapColumns(Arrays.asList(columns));
	}

	
	/**
	 * Returns the specified {@link IColumn} list as list of {@link WicketColumnAdapter}s that
	 * can be given to an {@link AbstractGrid}. The column identifiers are generated.
	 * @param columns
	 * 		list of {@link IColumn}s 
	 * @return
	 * 		list of {@link IGridColumn}s
	 */
	public static List<IGridColumn> wrapColumns(List<IColumn> columns) {
		List<IGridColumn> result = new ArrayList<IGridColumn>(columns.size());
		int i = 0;
		for (IColumn column : columns) {
			result.add(new WicketColumnAdapter("column" + i++, column));
		}
		return result;
	}
}