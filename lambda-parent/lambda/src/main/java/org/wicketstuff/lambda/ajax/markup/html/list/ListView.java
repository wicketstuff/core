package org.wicketstuff.lambda.ajax.markup.html.list;

import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.markup.html.list.ListView} that uses lambdas for
 * event handling.
 * 
 * @see org.apache.wicket.markup.html.list.ListView
 */
public class ListView<T> extends org.apache.wicket.markup.html.list.ListView<T> {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<ListItem<T>> populateItemHandler;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public ListView(String id) {
		this(id, null, null);
	}

	/**
	 * @param id
	 *            See Component
	 * @param list
	 *            List to cast to Serializable
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public ListView(String id, List<T> list) {
		this(id, Model.ofList(list), null);
	}

	/**
	 * @param id
	 *            component id
	 * @param model
	 *            model containing a list of
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public ListView(String id, IModel<? extends List<T>> model) {
		this(id, model, null);
	}

	/**
	 * @param id
	 *            component id
	 * @param model
	 *            model containing a list of
	 * @param populateItemHandler
	 *            handler to call to populate the list view
	 */
	public ListView(String id, IModel<? extends List<T>> model, SerializableConsumer<ListItem<T>> populateItemHandler) {
		super(id, model);
		this.populateItemHandler = populateItemHandler;
	}

	/**
	 * Sets the handler to call to populate the list view
	 * 
	 * @param populateItemHandler
	 *            handler to call to populate the list view
	 * @return thi
	 */
	public ListView<T> populateItemHandler(SerializableConsumer<ListItem<T>> populateItemHandler) {
		this.populateItemHandler = populateItemHandler;
		return this;
	}

	@Override
	protected final void populateItem(ListItem<T> item) {
		if (populateItemHandler != null) {
			populateItemHandler.accept(item);
		} else {
			throw new WicketRuntimeException("populateItemHandler not specified");
		}
	}

}
