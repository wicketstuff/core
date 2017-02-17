package org.wicketstuff.lambda.ajax.markup.html;

import java.util.Optional;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of, and drop in replacement for,
 * {@link org.apache.wicket.ajax.markup.html.AjaxFallbackLink} that uses a
 * lambda for event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.AjaxFallbackLink
 * @param <T>
 *            type of the model for the link
 */
public class AjaxFallbackLink<T> extends org.apache.wicket.ajax.markup.html.AjaxFallbackLink<T> {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<Optional<AjaxRequestTarget>> clickHandler;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public AjaxFallbackLink(String id) {
		this(id, null, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param clickHandler
	 *            handler to call to handle the click
	 */
	public AjaxFallbackLink(String id, SerializableConsumer<Optional<AjaxRequestTarget>> clickHandler) {
		this(id, null, clickHandler);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public AjaxFallbackLink(String id, IModel<T> model) {
		this(id, model, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 * @param clickHandler
	 *            handler to call to handle the click
	 */
	public AjaxFallbackLink(String id, IModel<T> model,
			SerializableConsumer<Optional<AjaxRequestTarget>> clickHandler) {
		super(id, model);
		this.clickHandler = clickHandler;
	}

	/**
	 * Sets the handler to call on click.
	 * 
	 * @param clickHandler
	 *            handler to call to handle the click
	 * @return this
	 */
	public AjaxFallbackLink<T> clickHandler(SerializableConsumer<Optional<AjaxRequestTarget>> clickHandler) {
		this.clickHandler = clickHandler;
		return this;
	}

	@Override
	public final void onClick(Optional<AjaxRequestTarget> target) {
		if (clickHandler != null) {
			clickHandler.accept(target);
		} else {
			throw new WicketRuntimeException("clickHandler not specified");
		}
	}

}
