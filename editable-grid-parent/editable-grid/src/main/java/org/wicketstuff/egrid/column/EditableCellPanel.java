package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

import java.io.Serial;

/**
 * @author Nadeem Mohammad
 */
public abstract class EditableCellPanel extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    public EditableCellPanel(final String id) {
        super(id);
    }

    public abstract FormComponent<?> getEditableComponent();
}
