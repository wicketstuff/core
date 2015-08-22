package org.wicketstuff.foundation.button;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * Buttons are convenient tools when you need more traditional actions. To that end, Foundation has many 
 * easy to use button styles that you can customize or override to fit your needs.
 * http://foundation.zurb.com/docs/components/buttons.html
 * @author ilkka
 *
 * @param <T> The type of the model object.
 */
public abstract class FoundationAjaxLink<T> extends AjaxLink<T> {

	private static final long serialVersionUID = 1L;

	private IModel<ButtonOptions> optionsModel;
	
	/**
	 * Create FoundationAjaxLink.
	 * @param id Wicket id.
	 */
	public FoundationAjaxLink(String id) {
		this(id, new ButtonOptions());
	}

	/**
	 * Create FoundationAjaxLink.
	 * @param id Wicket id.
	 * @param options Options for the link.
	 */
	public FoundationAjaxLink(String id, ButtonOptions options) {
		super(id);
		Args.notNull(options, "options");
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationAjaxLink.
	 * @param id Wicket id.
	 * @param model Model for the link.
	 */
	public FoundationAjaxLink(String id, IModel<T> model) {
		this(id, model, new ButtonOptions());
	}

	/**
	 * Create FoundationAjaxLink.
	 * @param id Wicket id.
	 * @param model Model for the link.
	 * @param options Options for the link.
	 */
	public FoundationAjaxLink(String id, IModel<T> model, ButtonOptions options) {
		this(id, model, Model.of(options));
	}

	/**
	 * Create FoundationAjaxLink.
	 * @param id Wicket id.
	 * @param model Model for the link.
	 * @param optionsModel Model providing options for the link.
	 */
	public FoundationAjaxLink(String id, IModel<T> model, IModel<ButtonOptions> optionsModel) {
		super(id, model);
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
