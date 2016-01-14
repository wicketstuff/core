package org.wicketstuff.datatables.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.wicketstuff.datatables.IDataTablesColumn;

/**
 *
 */
public class SpanColumn<T, S> extends AbstractColumn<T, S> implements IDataTablesColumn<T, S> {

    public SpanColumn(IModel<String> displayModel, S sortProperty) {
        super(displayModel, sortProperty);
    }

    @Override
    public int getColspan() {
        return 0;
    }

    @Override
    public int getRowspan() {
        return 0;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new Label(componentId, rowModel));
    }
}
