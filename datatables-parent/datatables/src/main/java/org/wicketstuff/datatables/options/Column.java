package org.wicketstuff.datatables.options;

/**
 * The definition of a column.
 *
 * @see <a href="https://datatables.net/reference/option/columns">DataTables columns</a>
 */
public class Column {

    private final String data;

    public Column(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
