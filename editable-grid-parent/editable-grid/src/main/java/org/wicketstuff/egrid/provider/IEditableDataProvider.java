package org.wicketstuff.egrid.provider;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;

/**
 * Interface used to provide data for an EditableDataTable.
 * It supports both sorting and modification of the underlying data.
 *
 * @param <T> type of the data
 * @param <S> type of the sorting parameter
 * @author Nadeem Mohammad
 * @see org.apache.wicket.markup.repeater.data.IDataProvider
 */
public interface IEditableDataProvider<T, S> extends ISortableDataProvider<T, S> {
    /**
     * Method for adding new items to the data provider.
     *
     * @param item item of type T that needs to be added
     */
    void add(T item);

    /**
     * Method for deleting items in the data provider.
     *
     * @param item item of type T that needs to be deleted
     */
    void remove(T item);

    /**
     * This optional method can be implemented to update an item.
     * Sometimes it is needed, for example, when the data is retrieved from a database.
     *
     * @param item item of type T that need to be updated
     */
    default void update(T item) {
        //noop
    }
}
