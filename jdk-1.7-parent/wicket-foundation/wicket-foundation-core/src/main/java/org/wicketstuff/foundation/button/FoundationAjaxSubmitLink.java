package org.wicketstuff.foundation.button;

import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * Buttons are convenient tools when you need more traditional actions. To that end, Foundation has many 
 * easy to use button styles that you can customize or override to fit your needs.
 * http://foundation.zurb.com/docs/components/buttons.html
 * @author ilkka
 *
 */
public class FoundationAjaxSubmitLink extends AjaxSubmitLink {

	private static final long serialVersionUID = 1L;

	private IModel<ButtonOptions> optionsModel;

	/**
	 * Create FoundationAjaxSubmitLink.
	 * @param id - Wicket id.
	 */
	public FoundationAjaxSubmitLink(String id) {
		this(id, new ButtonOptions());
	}
	
	/**
	 * Create FoundationAjaxSubmitLink.
	 * @param id - Wicket id.
	 * @param options - Options for the link.
	 */
	public FoundationAjaxSubmitLink(String id, ButtonOptions options) {
		this(id, Model.of(options));
	}

	/**
	 * Create FoundationAjaxSubmitLink.
	 * @param id - Wicket id.
	 * @param optionsModel - Model providing options for the link.
	 */
	public FoundationAjaxSubmitLink(String id, IModel<ButtonOptions> optionsModel) {
		super(id);
		Args.notNull(optionsModel, "optionsModel");
		this.optionsModel = optionsModel;
		add(new FoundationButtonBehavior(this.optionsModel));
	}
	
	/**
	 * Create FoundationAjaxSubmitLink.
	 * @param id - Wicket id.
	 * @param form - Form to be submitted by the link.
	 * @param options - Options for the link.
	 */
	public FoundationAjaxSubmitLink(String id, Form<?> form, ButtonOptions options) {
		this(id, form, Model.of(options));
	}

	/**
	 * Create FoundationAjaxSubmitLink.
	 * @param id - Wicket id.
	 * @param form - Form to be submitted by the link.
	 * @param optionsModel - Model providing options for the link.
	 */
	public FoundationAjaxSubmitLink(String id, Form<?> form, IModel<ButtonOptions> optionsModel) {
		super(id, form);
		Args.notNull(optionsModel, "optionsModel");
		this.optionsModel = optionsModel;
		add(new FoundationButtonBehavior(this.optionsModel));
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
