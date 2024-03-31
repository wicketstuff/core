package org.wicketstuff.egrid.column.panel;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.convert.IConverter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * An EditablePanel with a DropDownChoice
 *
 * @param <T> the model object type
 * @param <P> the type of the choice items
 * @author Nadeem Mohammad
 * @see org.apache.wicket.markup.html.form.DropDownChoice
 */
public class DropDownPanel<T, P extends Serializable> extends EditablePanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final DropDownChoice<P> dropDownChoice;

    /**
     * Constructor
     *
     * @param id      the component id
     * @param column  the column in which this panel will be placed in
     * @param choices the list of choices
     */
    public DropDownPanel(final String id, final AbstractColumn<T, ?> column, final List<? extends P> choices) {
        super(id);
        dropDownChoice = new DropDownChoice<>("dropDown", choices, newChoiceRenderer()) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected IConverter<?> createConverter(final Class<?> type) {
                return newObjectConverter(type);
            }
        };
        dropDownChoice.setOutputMarkupId(true);
        dropDownChoice.setLabel(column.getDisplayModel());
        add(dropDownChoice);
    }

    /**
     * Override to create a new IChoiceRenderer for the {@code dropDownChoice}.
     *
     * @return a new IChoiceRenderer
     * @see org.apache.wicket.markup.html.form.IChoiceRenderer
     */
    protected IChoiceRenderer<P> newChoiceRenderer() {
        return null;
    }

    /**
     * Override to create a new IConverter for the {@code dropDownChoice}.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<? extends P> newObjectConverter(final Class<?> type) {
        return null;
    }

    @Override
    public FormComponent<?> getEditableComponent() {
        return dropDownChoice;
    }

    /**
     * @return the list of choices of the {@code dropDownChoice}
     */
    public List<? extends P> getChoices() {
        return dropDownChoice.getChoices();
    }
}
