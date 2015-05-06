package org.wicketstuff.foundation.button;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Buttons are convenient tools when you need more traditional actions. To that end, Foundation has many 
 * easy to use button styles that you can customize or override to fit your needs.
 * http://foundation.zurb.com/docs/components/buttons.html
 * @author ilkka
 *
 */
public class FoundationAjaxButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	private IModel<ButtonOptions> optionsModel;

	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 */
	public FoundationAjaxButton(String id) {
		this(id, new ButtonOptions());
	}
	
	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 * @param options Options for the button.
	 */
	public FoundationAjaxButton(String id, ButtonOptions options) {
		super(id);
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 * @param form The form to be submitted.
	 * @param options Options for the button.
	 */
	public FoundationAjaxButton(String id, Form<?> form, ButtonOptions options) {
		super(id, form);
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 * @param model Model for button title.
	 * @param options Options for the button.
	 */
	public FoundationAjaxButton(String id, IModel<String> model, ButtonOptions options) {
		super(id, model);
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 * @param model Model for button title.
	 * @param form The form to be submitted.
	 * @param options Options for the button.
	 */
	public FoundationAjaxButton(String id, IModel<String> model, Form<?> form, ButtonOptions options) {
		this(id, model, form, Model.of(options));
	}

	/**
	 * Create FoundationAjaxButton.
	 * @param id Wicket id.
	 * @param model Model for button title.
	 * @param form The form to be submitted.
	 * @param optionsModel Model providing options for the button.
	 */
	public FoundationAjaxButton(String id, IModel<String> model, Form<?> form, IModel<ButtonOptions> optionsModel) {
		super(id, model, form);
		this.optionsModel = optionsModel;
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	@Override
	public void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		this.replaceComponentTagBody(markupStream, openTag, this.getDefaultModelObjectAsString());
		super.onComponentTagBody(markupStream, openTag);
	}
	
	@Override
	protected void onDetach() {
		this.optionsModel.detach();
		super.onDetach();
	}	
}
