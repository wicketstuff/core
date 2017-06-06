package org.wicketstuff.lambda.ajax.markup.html;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of, and drop in replacement for,
 * {@link org.apache.wicket.ajax.markup.html.IndicatingAjaxButton} that uses
 * lambdas for event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.IndicatingAjaxButton
 * @param <T>
 *            type of the model for the link
 */
public class IndicatingAjaxButton extends org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> submitHandler;
	private SerializableConsumer<AjaxRequestTarget> errorHandler;

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public IndicatingAjaxButton(String id) {
		this(id, null, null);
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 * @param form
	 */
	public IndicatingAjaxButton(String id, Form<?> form) {
		this(id, null, form);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 */
	public IndicatingAjaxButton(String id, IModel<String> model) {
		this(id, model, null);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param form
	 */
	public IndicatingAjaxButton(String id, IModel<String> model, Form<?> form) {
		super(id, model, form);
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param submitHandler
	 *            handler to call to process the submit
	 * @return this
	 */
	public IndicatingAjaxButton submitHandler(SerializableConsumer<AjaxRequestTarget> submitHandler) {
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
	public IndicatingAjaxButton errorHandler(SerializableConsumer<AjaxRequestTarget> errorHandler) {
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
