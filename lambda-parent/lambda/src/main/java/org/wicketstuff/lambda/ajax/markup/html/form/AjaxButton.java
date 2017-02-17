package org.wicketstuff.lambda.ajax.markup.html.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of, and drop in replacement for,
 * {@link org.apache.wicket.ajax.markup.html.form.AjaxButton} that uses lambdas
 * for event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.form.AjaxButton
 */
public class AjaxButton extends org.apache.wicket.ajax.markup.html.form.AjaxButton {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> submitHandler;
	private SerializableConsumer<AjaxRequestTarget> errorHandler;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public AjaxButton(String id) {
		this(id, null, null);
	}

	/**
	 * 
	 * Construct.
	 * 
	 * @param id
	 * @param form
	 */
	public AjaxButton(String id, Form<?> form) {
		this(id, null, form);
	}

	/**
	 * 
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 */
	public AjaxButton(String id, IModel<String> model) {
		this(id, model, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 * @param form
	 */
	public AjaxButton(String id, IModel<String> model, Form<?> form) {
		super(id, model, form);
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param submitHandler
	 *            handler to call to process the submit
	 * @return this
	 */
	public AjaxButton submitHandler(SerializableConsumer<AjaxRequestTarget> submitHandler) {
		this.submitHandler = submitHandler;
		return this;
	}

	/**
	 * Sets the handler to call on error.
	 * 
	 * @param errorHandler
	 *            handler to call to process the error
	 * @return this
	 */
	public AjaxButton errorHandler(SerializableConsumer<AjaxRequestTarget> errorHandler) {
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
