package org.wicketstuff.lambda.ajax.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior} that
 * uses lambdas for event handling.
 * 
 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
 */
public class AjaxFormComponentUpdatingBehavior extends org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> updateHandler;
	private SerializableBiConsumer<AjaxRequestTarget, RuntimeException> errorHandler;

	/**
	 * Construct.
	 * 
	 * @param event
	 *            event to trigger this behavior
	 */
	public AjaxFormComponentUpdatingBehavior(String event) {
		this(event, null);
	}

	/**
	 * Construct.
	 * 
	 * @param event
	 *            event to trigger this behavior
	 * @param updateHandler
	 *            handler to call to process the update
	 */
	public AjaxFormComponentUpdatingBehavior(String event, SerializableConsumer<AjaxRequestTarget> updateHandler) {
		super(event);
		this.updateHandler = updateHandler;
	}

	/**
	 * Sets the handler to call when a component is updated.
	 * 
	 * @param updateHandler
	 *            handler to call to process the update
	 * @return this
	 */
	public AjaxFormComponentUpdatingBehavior updateHandler(SerializableConsumer<AjaxRequestTarget> updateHandler) {
		this.updateHandler = updateHandler;
		return this;
	}

	/**
	 * Sets the handler to call when an error occurs.
	 * 
	 * @param errorHandler
	 *            handler to call to process the error
	 * @return this
	 */
	public AjaxFormComponentUpdatingBehavior errorHandler(
			SerializableBiConsumer<AjaxRequestTarget, RuntimeException> errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

	@Override
	protected final void onUpdate(AjaxRequestTarget target) {
		if (updateHandler != null) {
			updateHandler.accept(target);
		} else {
			throw new WicketRuntimeException("updateHandler not specified");
		}
	}

	@Override
	protected final void onError(AjaxRequestTarget target, RuntimeException e) {
		if (errorHandler != null) {
			errorHandler.accept(target, e);
		} else {
			super.onError(target, e);
		}
	}

}
