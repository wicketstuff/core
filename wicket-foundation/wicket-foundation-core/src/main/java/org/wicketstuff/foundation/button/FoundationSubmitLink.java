package org.wicketstuff.foundation.button;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
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
public class FoundationSubmitLink extends SubmitLink {

	private static final long serialVersionUID = 1L;

	private IModel<ButtonOptions> optionsModel;

	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 */
	public FoundationSubmitLink(String id) {
		this(id, new ButtonOptions());
	}
	
	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 * @param options - Options for the link.
	 */
	public FoundationSubmitLink(String id, ButtonOptions options) {
		super(id);
		Args.notNull(options, "options");
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 * @param form - Form to be submitted by the link.
	 * @param options - Options for the link.
	 */
	public FoundationSubmitLink(String id, Form<?> form, ButtonOptions options) {
		super(id, form);
		Args.notNull(options, "options");
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 * @param model - Model for the link.
	 * @param options - Options for the link.
	 */
	public FoundationSubmitLink(String id, IModel<?> model, ButtonOptions options) {
		super(id, model);
		Args.notNull(options, "options");
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 * @param model - Model for the link.
	 * @param form - Form to be submitted by the link.
	 * @param options - Options for the link.
	 */
	public FoundationSubmitLink(String id, IModel<?> model, Form<?> form, ButtonOptions options) {
		this(id, model, form, Model.of(options));
	}

	/**
	 * Create FoundationSubmitLink.
	 * @param id - Wicket id.
	 * @param model - Model for the link.
	 * @param form - Form to be submitted by the link.
	 * @param optionsModel - Model for the link options.
	 */
	public FoundationSubmitLink(String id, IModel<?> model, Form<?> form, IModel<ButtonOptions> optionsModel) {
		super(id, model, form);
		Args.notNull(optionsModel, "optionsModel");
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
