package org.wicketstuff.datatables.options;

import java.io.Serializable;

/**
 * The definition of a column.
 *
 * @see <a href="https://datatables.net/reference/option/columns">DataTables columns</a>
 */
public class Column implements Serializable {

    private final String data;
    private final boolean orderable;

    public Column(final String data) {
        this(data, true);
    }

    public Column(final String data, final boolean orderable) {
        this.data = data;
        this.orderable = orderable;
    }

    public String getData() {
        return data;
    }

    public boolean isOrderable() {
        return orderable;
    }
}
