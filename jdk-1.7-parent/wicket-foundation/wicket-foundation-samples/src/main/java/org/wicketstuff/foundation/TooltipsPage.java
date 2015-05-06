package org.wicketstuff.foundation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.tooltip.FoundationTooltipBehavior;
import org.wicketstuff.foundation.tooltip.TooltipOptions;
import org.wicketstuff.foundation.tooltip.TooltipPosition;
import org.wicketstuff.foundation.tooltip.TooltipVisibility;

public class TooltipsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public TooltipsPage(PageParameters params) {
		super(params);
		
		Label basic = new Label("basic", Model.of("Hover on desktop or touch me on mobile!"));
		basic.add(new FoundationTooltipBehavior("Tooltips are awesome, you should totally use them!"));
		add(basic);
		
		Label advanced = new Label("advanced", Model.of("Hover on desktop or touch me on mobile!"));
		TooltipOptions options = new TooltipOptions(TooltipPosition.TIP_TOP).setRadius(ButtonRadius.ROUND);
		advanced.add(new FoundationTooltipBehavior("Tooltips are awesome, you should totally use them!", options));
		add(advanced);
		
		Label visibility = new Label("visibility", Model.of("This one shows tooltip only on large displays!"));
		TooltipOptions visibilityOptions = new TooltipOptions(TooltipVisibility.LARGE);
		visibility.add(new FoundationTooltipBehavior("Tooltips are awesome, you should totally use them!", visibilityOptions));
		add(visibility);
		
		Label disableForTouch = new Label("disableForTouch", Model.of("This one's tooltip is disabled for touch devices!"));
		TooltipOptions touchOptions = new TooltipOptions(true);
		disableForTouch.add(new FoundationTooltipBehavior("Tooltips are awesome, you should totally use them!", touchOptions));
		add(disableForTouch);
	}
}
