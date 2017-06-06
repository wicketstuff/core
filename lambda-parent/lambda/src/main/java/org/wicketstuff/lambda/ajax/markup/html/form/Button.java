package org.wicketstuff.lambda.ajax.markup.html.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.ajax.markup.html.form.Button} that uses lambdas for
 * event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.form.Button
 */
public class Button extends org.apache.wicket.markup.html.form.Button {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<Button> submitHandler;
	private SerializableConsumer<Button> errorHandler;

	/**
	 * Constructor without a model. Buttons without models leave the markup
	 * attribute &quot;value&quot;. Provide a model if you want to set the
	 * button's label dynamically.
	 * 
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public Button(String id) {
		super(id);
	}

	/**
	 * Constructor taking an model for rendering the 'label' of the button (the
	 * value attribute of the input/button tag). Use a
	 * {@link org.apache.wicket.model.StringResourceModel} for a localized
	 * value.
	 * 
	 * @param id
	 *            Component id
	 * @param model
	 *            The model property is used to set the &quot;value&quot;
	 *            attribute. It will thus be the label of the button that shows
	 *            up for end users. If you want the attribute to keep it's
	 *            markup attribute value, don't provide a model, or let it
	 *            return an empty string.
	 */
	public Button(String id, IModel<String> model) {
		super(id, model);
	}

	/**
	 * Sets the handler to call on submit.
	 * 
	 * @param submitHandler
	 *            handler to call to process the submit
	 * @return this
	 */
	public Button submitHandler(SerializableConsumer<Button> submitHandler) {
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
	public Button errorHandler(SerializableConsumer<Button> errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

	@Override
	public final void onSubmit() {
		if (submitHandler != null) {
			submitHandler.accept(this);
		} else {
			throw new WicketRuntimeException("submitHandler not specified");
		}
	}

	@Override
	public final void onError() {
		if (errorHandler != null) {
			errorHandler.accept(this);
		}
	}

}
