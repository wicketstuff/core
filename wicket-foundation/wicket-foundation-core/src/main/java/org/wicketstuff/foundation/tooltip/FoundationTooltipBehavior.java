package org.wicketstuff.foundation.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.foundation.behavior.FoundationJsBehavior;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Tooltips are a quick way to provide extended information on a term or action on a page.
 * http://foundation.zurb.com/docs/components/tooltips.html
 * @author ilkka
 *
 */
public class FoundationTooltipBehavior extends FoundationJsBehavior {

	private static final long serialVersionUID = 1L;
	
	private IModel<String> titleModel;
	private IModel<TooltipOptions> optionsModel;

	/**
	 * Create FoundationTooltipBehavior.
	 * @param title - Title text for the tooltip.
	 */
	public FoundationTooltipBehavior(String title) {
		this(title, new TooltipOptions());
	}
	
	/**
	 * Create FoundationTooltipBehavior.
	 * @param title - Title text for the tooltip.
	 * @param options - Options for the tooltip.
	 */
	public FoundationTooltipBehavior(String title, TooltipOptions options) {
		this(Model.of(title), Model.of(options));
	}

	/**
	 * Create FoundationTooltipBehavior.
	 * @param titleModel - Model for the tooltip title text.
	 * @param optionsModel - Model for the tooltip options.
	 */
	public FoundationTooltipBehavior(IModel<String> titleModel, IModel<TooltipOptions> optionsModel) {
		Args.notNull(titleModel, "titleModel");
		Args.notNull(optionsModel, "optionsModel");
		this.titleModel = titleModel;
		this.optionsModel = optionsModel;
	}	

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		Attribute.addAttribute(tag, "data-tooltip");
		Attribute.addAttribute(tag, "aria-haspopup", true);
		Attribute.addClass(tag, "has-tip");
		Attribute.addAttribute(tag, "title", titleModel.getObject());
		TooltipOptions options = optionsModel.getObject();
		if (options.isDisableForTouch()) {
			Attribute.addDataOptions(tag, "disable_for_touch:true");
		}
		if (options.getPosition() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getPosition().name()));
		}
		if (options.getRadius() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getRadius().name()));
		}
		if (options.getVisibility() != null) {
			String value = "show_on:" + StringUtil.EnumNameToCssClassName(options.getVisibility().name());
			Attribute.addDataOptions(tag, value);
		}
		super.onComponentTag(component, tag);
	}
	
	@Override
	public void detach(Component component) {
		titleModel.detach();
		optionsModel.detach();
		super.detach(component);
	}
}
