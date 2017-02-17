package org.wicketstuff.lambda.ajax.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior}
 * that uses lambdas for event handling.
 *
 * @see org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior
 */
public class AjaxFormChoiceComponentUpdatingBehavior
		extends org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> updateHandler;
	private SerializableBiConsumer<AjaxRequestTarget, RuntimeException> errorHandler;

	/**
	 * Construct.
	 */
	public AjaxFormChoiceComponentUpdatingBehavior() {
		this(null);
	}

	/**
	 * Construct.
	 * 
	 * @param updateHandler
	 *            handler to call when the choice component value is updated
	 */
	public AjaxFormChoiceComponentUpdatingBehavior(SerializableConsumer<AjaxRequestTarget> updateHandler) {
		super();
		this.updateHandler = updateHandler;
	}

	/**
	 * Sets the handler to call when the choice component is updated.
	 * 
	 * @param updateHandler
	 *            handler to call when the choice component is updated
	 * @return this
	 */
	public AjaxFormChoiceComponentUpdatingBehavior updateHandler(
			SerializableConsumer<AjaxRequestTarget> updateHandler) {
		this.updateHandler = updateHandler;
		return this;
	}

	/**
	 * Sets the handler to call when there is an error updating the choice component.
	 * 
	 * @param errorHandler
	 *            handler to call when there is an error updating the choice component
	 * @return this
	 */
	public AjaxFormChoiceComponentUpdatingBehavior errorHandler(
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
