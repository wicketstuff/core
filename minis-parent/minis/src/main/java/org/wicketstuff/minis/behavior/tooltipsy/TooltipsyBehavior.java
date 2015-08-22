package org.wicketstuff.minis.behavior.tooltipsy;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.lang.Args;

/**
 * 
 * This behavior adds tooltips for component(s)
 * @author Ilkka Seppälä https://github.com/iluwatar
 *
 */
public class TooltipsyBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private static final String TOOLTIPSY_JS_FILENAME = "tooltipsy.js";
	
	private TooltipsyOptions options = new TooltipsyOptions();

	private String overrideSelector = "";
	
	public TooltipsyBehavior() {
	}
	
	public TooltipsyBehavior(TooltipsyOptions options) {
		this.options = new TooltipsyOptions(options);
	}

	public TooltipsyOptions getOptions() {
		return this.options;
	}

	public void setOptions(final TooltipsyOptions options) {
		this.options = new TooltipsyOptions(options);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(this.getClass(), TOOLTIPSY_JS_FILENAME)));
		if (component != null) {
			String selector = "#" + component.getMarkupId();
			if (!overrideSelector.equals("")) {
				selector = overrideSelector;
			}
			String javascript = "$('" + selector + "').tooltipsy(" + options.getParameterString() + ");";
			response.render(OnLoadHeaderItem.forScript(javascript));
		}
	}

	public String getOverrideSelector() {
		return overrideSelector;
	}

	public void setOverrideSelector(String overrideSelector) {
		Args.notNull(overrideSelector, "overrideSelector");
		this.overrideSelector = overrideSelector;
	}
	
}
