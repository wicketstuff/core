package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class DefaultKendoTooltipPage extends com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip.AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	public DefaultKendoTooltipPage()
	{
		// label 1 //
		final Label label1 = new Label("label1", "Hover me to give you a tip!");
		this.add(label1);
		label1.add(new TooltipBehavior(new Options("position", Options.asString("top"))));

		// label 2 //
		final Label label2 = new Label("label2", "Hover me to give you another tip!");
		this.add(label2);
		label2.add(new TooltipBehavior(Model.of("Another tooltip")));
	}
}
