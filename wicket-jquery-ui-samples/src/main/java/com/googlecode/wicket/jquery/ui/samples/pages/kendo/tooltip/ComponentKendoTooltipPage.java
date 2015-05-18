package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip;

import com.googlecode.wicket.jquery.core.Options;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;
import org.apache.wicket.markup.html.panel.Panel;

public class ComponentKendoTooltipPage extends AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	public ComponentKendoTooltipPage()
	{
		MultiLineLabel tooltip = new MultiLineLabel("dummy", "I am a tooltip\non several\nlines");
		final Label multiLineLabel = new Label("multilineLabel", "Hover me to give you a multi line tip!");
		this.add(multiLineLabel);
		multiLineLabel.add(new TooltipBehavior(tooltip));

		ImageAndTextPanel tooltip2 = new ImageAndTextPanel("imageAndText");
		final Label imageAndTextLabel = new Label("imageAndText", "Hover me to give you a tip with an image!");
		this.add(imageAndTextLabel);
		imageAndTextLabel.add(new TooltipBehavior(tooltip2) {
			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				getOptions().set("position", Options.asString("right"));
			}
		});
	}

	private static class ImageAndTextPanel extends Panel
	{
		public ImageAndTextPanel(String id)
		{
			super(id);
		}
	}
}
