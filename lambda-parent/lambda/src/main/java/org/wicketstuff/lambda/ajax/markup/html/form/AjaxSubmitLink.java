package org.wicketstuff.lambda.ajax.markup.html.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink} that uses
 * lambdas for event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink
 */
public class AjaxSubmitLink extends org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> submitHandler;
	private SerializableConsumer<AjaxRequestTarget> errorHandler;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public AjaxSubmitLink(String id) {
		this(id, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param form
	 */
	public AjaxSubmitLink(String id, Form<?> form) {
		super(id, form);
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param submitHandler
	 *            handler to call to process the submit
	 * @return this
	 */
	public AjaxSubmitLink submitHandler(SerializableConsumer<AjaxRequestTarget> submitHandler) {
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
	public AjaxSubmitLink errorHandler(SerializableConsumer<AjaxRequestTarget> errorHandler) {
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
