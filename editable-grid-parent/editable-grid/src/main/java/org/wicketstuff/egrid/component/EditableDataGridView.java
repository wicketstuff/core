package org.wicketstuff.egrid.component;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.wicketstuff.egrid.provider.IEditableDataProvider;

import java.io.Serial;
import java.util.List;

/**
 * Extension of DataGridView with the method {@code refresh}.
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Silas Porth
 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView
 */
public class EditableDataGridView<T, S> extends DataGridView<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * <br>
     * Notice cells are created in the same order as cell populators in the list
     *
     * @param id           component id
     * @param populators   list of ICellPopulators used to populate cells
     * @param dataProvider data provider
     */
    public EditableDataGridView(final String id, final List<? extends ICellPopulator<T>> populators, final IEditableDataProvider<T, S> dataProvider) {
        super(id, populators, dataProvider);
    }

    /**
     * This method can be called if only one row of the grid is to be refreshed,
     * and also supports the functionality of editing multiple rows at once without having to refresh the whole table.
     *
     * @param rowItem Row to be refreshed
     */
    public void refresh(final Item<T> rowItem) {
        rowItem.removeAll();
        populateItem(rowItem);
    }

    /**
     * Returns the editable data provider
     *
     * @return editable data provider
     */
    @Override
    public IEditableDataProvider<T, S> getDataProvider() {
        return (IEditableDataProvider<T, S>) internalGetDataProvider();
    }
}
