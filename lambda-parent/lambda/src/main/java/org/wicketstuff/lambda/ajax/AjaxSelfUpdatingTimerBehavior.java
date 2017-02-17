package org.wicketstuff.lambda.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior} that uses a
 * lambda for event handling.
 * 
 * @see org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior
 */
public class AjaxSelfUpdatingTimerBehavior extends org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> postProcessHandler;

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 */
	public AjaxSelfUpdatingTimerBehavior(Duration updateInterval) {
		this(updateInterval, null);
	}

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 * @param postProcessHandler
	 *            handler to call to process the AJAX callback
	 */
	public AjaxSelfUpdatingTimerBehavior(Duration updateInterval,
			SerializableConsumer<AjaxRequestTarget> postProcessHandler) {
		super(updateInterval);
		this.postProcessHandler = postProcessHandler;
	}

	/**
	 * Sets the handler to call to process the AJAX callback.
	 * 
	 * @param postProcessHandler handler to call to process the AJAX callback
	 * @return this 
	 */
	public AjaxSelfUpdatingTimerBehavior postProcessHandler(
			SerializableConsumer<AjaxRequestTarget> postProcessHandler) {
		this.postProcessHandler = postProcessHandler;
		return this;
	}

	@Override
	protected final void onPostProcessTarget(AjaxRequestTarget target) {
		if (postProcessHandler != null) {
			postProcessHandler.accept(target);
		} else {
			throw new WicketRuntimeException("postProcessHandler not specified");
		}
	}

}
