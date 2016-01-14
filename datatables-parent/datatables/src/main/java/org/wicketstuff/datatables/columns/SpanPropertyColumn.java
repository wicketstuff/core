package org.wicketstuff.datatables.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.wicketstuff.datatables.IDataTablesColumn;

/**
 *
 */
public class SpanPropertyColumn<T, S> extends PropertyColumn<T, S> implements IDataTablesColumn<T, S> {

    public SpanPropertyColumn(IModel<String> displayModel, S sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    @Override
    public int getColspan() {
        return 0;
    }

    @Override
    public int getRowspan() {
        return 0;
    }
}
