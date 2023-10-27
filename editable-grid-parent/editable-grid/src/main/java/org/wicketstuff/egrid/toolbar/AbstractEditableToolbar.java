package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.egrid.component.EditableDataTable;

import java.io.Serial;

/**
 * A base class for data table toolbars
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
 * @author Nadeem Mohammad
 */
public class AbstractEditableToolbar extends Panel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final EditableDataTable<?, ?> table;

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     * @param model model
     */
    public AbstractEditableToolbar(final EditableDataTable<?, ?> table, final IModel<?> model) {
        super(table.newToolbarId(), model);
        this.table = table;
    }

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public AbstractEditableToolbar(final EditableDataTable<?, ?> table) {
        this(table, null);
    }

    /**
     * @return DataTable this toolbar is attached to
     */
    protected EditableDataTable<?, ?> getTable() {
        return table;
    }
}
