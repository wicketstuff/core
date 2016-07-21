package org.wicketstuff.foundation.alert;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Alert boxes are elements you can use to communicate success, warnings, failure or just information.
 * http://foundation.zurb.com/docs/components/alert_boxes.html
 * @author ilkka
 *
 */
public class FoundationAlert extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;
	
	private IModel<AlertOptions> optionsModel;

	/**
	 * Create FoundationAlert component.
	 * @param id - Wicket id.
	 * @param titleModel - Model for getting the alert text.
	 */
	public FoundationAlert(String id, IModel<String> titleModel) {
		this(id, titleModel, new AlertOptions());
	}

	/**
	 * Create FoundationAlert component.
	 * @param id - Wicket id.
	 * @param titleModel - Model for getting the alert text.
	 * @param options - Options for the component.
	 */
	public FoundationAlert(String id, IModel<String> titleModel, AlertOptions options) {
		this(id, titleModel, Model.of(options));
	}
	
	/**
	 * Create FoundationAlert component.
	 * @param id - Wicket id.
	 * @param titleModel - Model for getting the alert text.
	 * @param optionsModel - Model for getting the component options.
	 */
	public FoundationAlert(String id, IModel<String> titleModel, IModel<AlertOptions> optionsModel) {
		super(id);
		add(new Label("body", titleModel));
		this.optionsModel = optionsModel;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		Attribute.addAttribute(tag, "data-alert");
		Attribute.setClass(tag, "alert-box");
		AlertOptions options = optionsModel.getObject();
		if (options.getColor() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getColor().name()));
		}
		if (options.getRadius() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getRadius().name()));
		}
		super.onComponentTag(tag);
	}
	
	@Override
	protected void onDetach() {
		optionsModel.detach();
		super.onDetach();
	}
}
