package org.wicketstuff.datatables;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

/**
 *
 */
public interface IDataTablesColumn<T, S> extends IColumn<T, S> {

    int getColspan();

    int getRowspan();
}
