package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.wicketstuff.egrid.column.panel.EditablePanel;

/**
 * Interface that should be the base for all editable columns because it supplies each column with a form component.
 *
 * @author Nadeem Mohammad
 */
public interface IEditableColumn {
    /**
     * This method creates an editable cell panel that contains a form component
     * that can be used when editing a row.
     *
     * @param componentId the id of the editable cell panel
     * @return an Panel containing an input
     * @see org.wicketstuff.egrid.column.panel.EditablePanel
     */
    EditablePanel createEditablePanel(String componentId);

    /**
     * Implementations of this method grant access to the default editorComponent of each column.
     * It should be called within the createEditablePanel implementation
     * and supplied with the editable component of the created panel.
     *
     * @param editorComponent the component that is displayed if the row is in edit mode
     */
    void addBehaviors(FormComponent<?> editorComponent);
}
