package org.wicketstuff.egrid.column;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

import java.util.List;

/**
 * @author Nadeem Mohammad
 */
public interface IColumnProvider<T, S> {
    List<? extends IColumn<T, S>> getColumns();
}
