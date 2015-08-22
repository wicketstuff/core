package org.wicketstuff.foundation.button;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Button;
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
public class FoundationButton extends Button {

	private static final long serialVersionUID = 1L;
	
	private IModel<ButtonOptions> optionsModel;

	/**
	 * Create FoundationButton.
	 * @param id - Wicket id.
	 */
	public FoundationButton(String id) {
		this(id, new ButtonOptions());
	}

	/**
	 * Create FoundationButton.
	 * @param id - Wicket id.
	 * @param options - Options for the button.
	 */
	public FoundationButton(String id, ButtonOptions options) {
		super(id);
		Args.notNull(options, "options");
		optionsModel = Model.of(options);
		add(new FoundationButtonBehavior(optionsModel));
	}
	
	/**
	 * Create FoundationButton.
	 * @param id - Wicket id.
	 * @param model - Model for the title.
	 */
	public FoundationButton(String id, IModel<String> model) {
		super(id, model);
		optionsModel = Model.of(new ButtonOptions());
		add(new FoundationButtonBehavior(optionsModel));
	}

	/**
	 * Create FoundationButton.
	 * @param id - Wicket id.
	 * @param model - Model for the title.
	 * @param options - Options for the button.
	 */
	public FoundationButton(String id, IModel<String> model, ButtonOptions options) {
		this(id, model, Model.of(options));
	}
	
	/**
	 * Create FoundationButton.
	 * @param id - Wicket id.
	 * @param model - Model for the title.
	 * @param optionsModel - Model for the button options.
	 */
	public FoundationButton(String id, IModel<String> model, IModel<ButtonOptions> optionsModel) {
		super(id, model);
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
