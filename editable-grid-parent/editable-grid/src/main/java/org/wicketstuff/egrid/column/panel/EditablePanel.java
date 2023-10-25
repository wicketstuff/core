package org.wicketstuff.egrid.column.panel;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

import java.io.Serial;

/**
 * A panel that contains a form component used for editing the data of rows.
 *
 * @author Nadeem Mohammad
 */
public abstract class EditablePanel extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    public EditablePanel(final String id) {
        super(id);
    }

    /**
     * @return the form component of an editable cell panel
     */
    public abstract FormComponent<?> getEditableComponent();
}
