package org.wicketstuff.egrid.provider;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.repeater.data.IDataProvider;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public interface IEditableDataProvider<T, S> extends IDataProvider<T>, ISortStateLocator<S>
{
	void add(T item);
	void remove(T item);
}
