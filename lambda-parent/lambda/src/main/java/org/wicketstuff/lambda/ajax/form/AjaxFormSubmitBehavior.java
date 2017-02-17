package org.wicketstuff.lambda.ajax.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.form.AjaxFormSubmitBehavior} that uses lambdas
 * for event handling.
 * 
 * @see org.apache.wicket.ajax.form.AjaxFormSubmitBehavior
 */
public class AjaxFormSubmitBehavior extends org.apache.wicket.ajax.form.AjaxFormSubmitBehavior {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> submitHandler;
	private SerializableConsumer<AjaxRequestTarget> errorHandler;

	/**
	 * Constructor. This constructor can only be used when the component this
	 * behavior is attached to is inside a form.
	 * 
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public AjaxFormSubmitBehavior(String event) {
		this(event, null);
	}

	/**
	 * Constructor. This constructor can only be used when the component this
	 * behavior is attached to is inside a form.
	 * 
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 * @param updateHandler
	 *            handler to call to handle the submit
	 */
	public AjaxFormSubmitBehavior(String event, SerializableConsumer<AjaxRequestTarget> submitHandler) {
		super(event);
		this.submitHandler = submitHandler;
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param submitHandler
	 *            handler to call to handle the submit
	 * @return this
	 */
	public AjaxFormSubmitBehavior submitHandler(SerializableConsumer<AjaxRequestTarget> submitHandler) {
		this.submitHandler = submitHandler;
		return this;
	}

	/**
	 * Sets the handler to call on error.
	 * 
	 * @param errorHandler
	 *            handler to call to handle the error
	 * @return this
	 */
	public AjaxFormSubmitBehavior errorHandler(SerializableConsumer<AjaxRequestTarget> errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

	@Override
	protected final void onSubmit(AjaxRequestTarget target) {
		if (submitHandler != null) {
			submitHandler.accept(target);
		} else {
			throw new WicketRuntimeException("submitHandler not specified");
		}
	}

	@Override
	protected final void onError(AjaxRequestTarget target) {
		if (errorHandler != null) {
			errorHandler.accept(target);
		}
	}

}
