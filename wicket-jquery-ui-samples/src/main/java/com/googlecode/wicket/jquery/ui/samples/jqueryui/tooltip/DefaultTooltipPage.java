package com.googlecode.wicket.jquery.ui.samples.jqueryui.tooltip;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;

public class DefaultTooltipPage extends AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTooltipPage()
	{
		Options options = new Options();
		options.set("position", "{ my: 'center top+3', at: 'center bottom' }");
//		options.set("track",true); //used to track the mouse

		this.add(new TooltipBehavior(options));
	}
}
