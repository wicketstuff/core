package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip;

import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class DefaultKendoTooltipPage extends com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip.AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	public DefaultKendoTooltipPage()
	{
		final Label label = new Label("label", "Hover me to give you a tip!");
		this.add(label);
		label.add(new TooltipBehavior(Model.of("Some tooltip")));
	}
}
