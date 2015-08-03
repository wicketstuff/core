package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.tooltip.TooltipBehavior;

public class ComponentKendoTooltipPage extends AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	public ComponentKendoTooltipPage()
	{
		// label 1 //
		final Label multiLineLabel = new Label("multilineLabel", "Hover me to give you a multi line tip!");
		this.add(multiLineLabel);

		// tooltip 1 //
		MultiLineLabel tooltip1 = new MultiLineLabel("dummy1", "I am a tooltip\non several\nlines");
		multiLineLabel.add(new TooltipBehavior(tooltip1));

		// label 2 //
		final Label imageAndTextLabel = new Label("imageAndText", "Hover me to give you a tip with an image!");
		this.add(imageAndTextLabel);

		// tooltip 2//
		ImageAndTextPanel tooltip2 = new ImageAndTextPanel("dummy2");
		imageAndTextLabel.add(new TooltipBehavior(tooltip2, new Options("position", Options.asString("right"))));
	}

	private static class ImageAndTextPanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		public ImageAndTextPanel(String id)
		{
			super(id);
		}
	}
}
