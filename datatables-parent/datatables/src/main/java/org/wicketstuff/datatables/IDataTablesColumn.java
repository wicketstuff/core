package org.wicketstuff.datatables;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

/**
 * A column that can span multiple columns or header rows when rendered by a
 * {@link org.wicketstuff.datatables.columns.SpanHeadersToolbar}.
 */
public interface IDataTablesColumn<T, S> extends IColumn<T, S> {

    /**
     * @return the number of columns the header cell spans, rendered as its {@code colspan}
     *         attribute; a value of 1 or less renders no attribute
     */
    int getColspan();

    /**
     * @return the number of header rows the header cell spans, rendered as its {@code rowspan}
     *         attribute; a value of 1 or less renders no attribute. A column with a rowspan larger
     *         than 1 is left out of the following {@code SpanHeadersToolbar}s that render the rows it covers.
     */
    int getRowspan();
}
