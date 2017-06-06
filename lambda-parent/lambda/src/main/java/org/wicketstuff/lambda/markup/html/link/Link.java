package org.wicketstuff.lambda.markup.html.link;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.markup.html.link.Link} that uses a lambda for event
 * handling.
 * 
 * @see org.apache.wicket.markup.html.link.Link
 */
public class Link<T> extends org.apache.wicket.markup.html.link.Link<T> {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<Link<T>> clickHandler;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public Link(String id) {
		this(id, null, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param clickHandler
	 *            handler to call on click
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public Link(String id, SerializableConsumer<Link<T>> clickHandler) {
		this(id, null, clickHandler);
	}

	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public Link(String id, IModel<T> model) {
		this(id, model, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param model
	 * @param clickHandler
	 *            handler to call to process the click
	 */
	public Link(String id, IModel<T> model, SerializableConsumer<Link<T>> clickHandler) {
		super(id, model);
		this.clickHandler = clickHandler;
	}

	/**
	 * Sets the handler to call on click.
	 * 
	 * @param clickHandler
	 *            handler to call to process the click
	 * @return this
	 */
	public Link<T> clickHandler(SerializableConsumer<Link<T>> clickHandler) {
		this.clickHandler = clickHandler;
		return this;
	}

	@Override
	public final void onClick() {
		if (clickHandler != null) {
			clickHandler.accept(this);
		} else {
			throw new WicketRuntimeException("clickHandler not specified");
		}
	}

}
