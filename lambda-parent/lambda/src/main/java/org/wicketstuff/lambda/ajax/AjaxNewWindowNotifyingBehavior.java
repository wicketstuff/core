package org.wicketstuff.lambda.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.AjaxNewWindowNotifyingBehavior} that uses a
 * lambda for event handling.
 *
 * @see org.apache.wicket.ajax.AjaxNewWindowNotifyingBehavior
 */
public class AjaxNewWindowNotifyingBehavior extends org.apache.wicket.ajax.AjaxNewWindowNotifyingBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> newWindowHandler;

	/**
	 * Constructor.
	 */
	public AjaxNewWindowNotifyingBehavior() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param newWindowHandler
	 *            handler to call when the new window is detected
	 */
	public AjaxNewWindowNotifyingBehavior(SerializableConsumer<AjaxRequestTarget> newWindowHandler) {
		this(null, newWindowHandler);
	}

	/**
	 * Constructor.
	 *
	 * @param windowName
	 *            the custom name to use for the page's window
	 */
	public AjaxNewWindowNotifyingBehavior(String windowName) {
		this(windowName, null);
	}

	/**
	 * Constructor.
	 *
	 * @param windowName
	 *            the custom name to use for the page's window
	 * @param newWindowHandler
	 *            handler to call when the new window is detected
	 */
	public AjaxNewWindowNotifyingBehavior(String windowName, SerializableConsumer<AjaxRequestTarget> newWindowHandler) {
		super(windowName);
		this.newWindowHandler = newWindowHandler;
	}

	/**
	 * Sets the handler to call when the new window is detected.
	 * 
	 * @param newWindowHandler
	 *            handler to call when the new window is detected
	 * @return this
	 */
	public AjaxNewWindowNotifyingBehavior newWindowHandler(SerializableConsumer<AjaxRequestTarget> newWindowHandler) {
		this.newWindowHandler = newWindowHandler;
		return this;
	}

	@Override
	protected final void onNewWindow(AjaxRequestTarget target) {
		if (newWindowHandler != null) {
			newWindowHandler.accept(target);
		} else {
			throw new WicketRuntimeException("newWindowHandler not specified");
		}
	}

}
