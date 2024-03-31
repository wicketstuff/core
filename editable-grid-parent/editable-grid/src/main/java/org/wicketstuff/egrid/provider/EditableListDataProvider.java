package org.wicketstuff.egrid.provider;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Example Implementation of IEditableDataProvider with List
 *
 * @author Nadeem Mohammad
 * @see org.wicketstuff.egrid.provider.IEditableDataProvider
 */
public class EditableListDataProvider<T extends Serializable> extends SortableDataProvider<T, String> implements IEditableDataProvider<T, String> {

    @Serial
    private static final long serialVersionUID = 1L;
    private final List<T> list;

    public EditableListDataProvider(final String sortProperty) {
        this(sortProperty, null);
    }

    public EditableListDataProvider(final String sortProperty, final SortOrder sortOrder) {
        this(Collections.emptyList(), sortProperty, sortOrder);
    }

    public EditableListDataProvider(final List<T> list, final String sortProperty) {
        this(list, sortProperty, null);
    }

    public EditableListDataProvider(final List<T> list, final String sortProperty, final SortOrder sortOrder) {
        if (list == null) {
            throw new IllegalArgumentException("argument [list] cannot be null");
        }
        this.list = list;

        var sortOrd = sortOrder != null ? sortOrder : SortOrder.ASCENDING;
        setSort(sortProperty, sortOrd);
    }

    protected List<T> getData() {
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<? extends T> iterator(final long first, final long count) {
        List<T> data = getData();

        data.sort((o1, o2) -> {
            int result;
            var v1 = (Comparable<Object>) PropertyResolver.getValue(getSort().getProperty(), o1);
            var v2 = (Comparable<Object>) PropertyResolver.getValue(getSort().getProperty(), o2);

            if (v1 == null && v2 == null) {
                result = 0;
            } else if (v1 == null) {
                result = 1;
            } else if (v2 == null) {
                result = -1;
            } else {
                result = v1.compareTo(v2);
            }
            return getSort().isAscending() ? result : -result;
        });

        long toIndex = first + count;
        if (toIndex > data.size()) {
            toIndex = data.size();
        }

        return data.subList((int) first, (int) toIndex).listIterator();
    }

    @Override
    public long size() {
        return getData().size();
    }

    @Override
    public IModel<T> model(final T object) {
        return new Model<>(object);
    }

    @Override
    public void add(final T item) {
        list.add(item);
    }

    @Override
    public void remove(final T item) {
        list.remove(item);
    }
}
