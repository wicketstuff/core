package org.wicketstuff.lambda.ajax.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.form.OnChangeAjaxBehavior} that uses lambdas
 * for event handling.
 * 
 * @see org.apache.wicket.ajax.form.OnChangeAjaxBehavior
 */
public class OnChangeAjaxBehavior extends org.apache.wicket.ajax.form.OnChangeAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> updateHandler;
	private SerializableBiConsumer<AjaxRequestTarget, RuntimeException> errorHandler;

	/**
	 * Constructor.
	 */
	public OnChangeAjaxBehavior() {
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param updateHandler
	 *            handler to call to process the update
	 */
	public OnChangeAjaxBehavior(SerializableConsumer<AjaxRequestTarget> updateHandler) {
		super();
		this.updateHandler = updateHandler;
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param updateHandler
	 *            handler to call to process the update
	 * @return this
	 */
	public OnChangeAjaxBehavior updateHandler(SerializableConsumer<AjaxRequestTarget> updateHandler) {
		this.updateHandler = updateHandler;
		return this;
	}

	/**
	 * Sets the handler to call on error.
	 * 
	 * @param errorHandler
	 *            handler to call to process the error
	 * @return this
	 */
	public OnChangeAjaxBehavior errorHandler(SerializableBiConsumer<AjaxRequestTarget, RuntimeException> errorHandler) {
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
