package org.wicketstuff.lambda.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.AbstractAjaxTimerBehavior} that uses a lambda
 * for event handling.
 * 
 * @see org.apache.wicket.ajax.AbstractAjaxTimerBehavior
 */
public class AbstractAjaxTimerBehavior extends org.apache.wicket.ajax.AbstractAjaxTimerBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> timerHandler;

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 */
	public AbstractAjaxTimerBehavior(Duration updateInterval) {
		this(updateInterval, null);
	}

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 * @param timerHandler
	 *            handler to call when the timer fires
	 * 
	 */
	public AbstractAjaxTimerBehavior(Duration updateInterval, SerializableConsumer<AjaxRequestTarget> timerHandler) {
		super(updateInterval);
		this.timerHandler = timerHandler;
	}

	/**
	 * Sets the handler to call when the timer fires.
	 * 
	 * @param timerHandler
	 *            handler to call when the timer fires
	 * @return this
	 */
	public AbstractAjaxTimerBehavior timerHandler(SerializableConsumer<AjaxRequestTarget> timerHandler) {
		this.timerHandler = timerHandler;
		return this;
	}

	@Override
	protected final void onTimer(AjaxRequestTarget target) {
		if (timerHandler != null) {
			timerHandler.accept(target);
		} else {
			throw new WicketRuntimeException("timerHandler not specified");
		}
	}

}
