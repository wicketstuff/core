package org.wicketstuff.datatables.options;

import java.io.Serializable;

/**
 * The definition of a column.
 *
 * @see <a href="https://datatables.net/reference/option/columns">DataTables columns</a>
 */
public class Column implements Serializable {

    private final String data;

    public Column(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
