package org.wicketstuff.lambda.ajax.markup.html.form;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;

/**
 * Subclass of, and drop in replacement for,
 * {@link org.apache.wicket.ajax.markup.html.form.AjaxCheckBox} that uses
 * lambdas for event handling.
 * 
 * @see org.apache.wicket.ajax.markup.html.form.AjaxCheckBox
 */
public class AjaxCheckBox extends org.apache.wicket.ajax.markup.html.form.AjaxCheckBox {

	private static final long serialVersionUID = 1L;

	private SerializableConsumer<AjaxRequestTarget> updateHandler;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public AjaxCheckBox(String id) {
		this(id, null, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param updateHandler
	 *            handler to call to process the update
	 */
	public AjaxCheckBox(String id, SerializableConsumer<AjaxRequestTarget> updateHandler) {
		this(id, null, updateHandler);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public AjaxCheckBox(String id, IModel<Boolean> model) {
		this(id, model, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 * @param updateHandler
	 *            handler to call to process the update
	 */
	public AjaxCheckBox(String id, IModel<Boolean> model, SerializableConsumer<AjaxRequestTarget> updateHandler) {
		super(id, model);
		this.updateHandler = updateHandler;
	}

	/**
	 * Sets the handler to call on update.
	 * 
	 * @param updateHandler
	 *            handler to call to process the update
	 * @return this
	 */
	public AjaxCheckBox updateHandler(SerializableConsumer<AjaxRequestTarget> updateHandler) {
		this.updateHandler = updateHandler;
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

}
