package org.wicketstuff.lambda.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.AjaxEventBehavior} that uses a lambda for event
 * handling.
 *
 * @see org.apache.wicket.ajax.AjaxEventBehavior
 */
public class AjaxEventBehavior extends org.apache.wicket.ajax.AjaxEventBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> eventHandler;

	/**
	 * Construct.
	 * 
	 * @param event
	 *            the event this behavior will be attached to
	 */
	public AjaxEventBehavior(String event) {
		this(event, null);
	}

	/**
	 * Construct.
	 * 
	 * @param event
	 *            the event this behavior will be attached to
	 * @param eventHandler
	 *            handler to call when the given ajax event fires
	 */
	public AjaxEventBehavior(String event, SerializableConsumer<AjaxRequestTarget> eventHandler) {
		super(event);
		this.eventHandler = eventHandler;
	}

	/**
	 * Sets the handler to call when when the given ajax event fires
	 * 
	 * @param eventHandler
	 *            handler to call when when the given ajax event fires
	 * @return this
	 */
	public AjaxEventBehavior eventHandler(SerializableConsumer<AjaxRequestTarget> eventHandler) {
		this.eventHandler = eventHandler;
		return this;
	}

	@Override
	protected final void onEvent(AjaxRequestTarget target) {
		if (eventHandler != null) {
			eventHandler.accept(target);
		} else {
			throw new WicketRuntimeException("eventHandler not specified");
		}
	}

}
