package org.wicketstuff.minis.behavior.tooltipsy;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * 
 * This behavior adds tooltips for component(s)
 * @author Ilkka Seppälä https://github.com/iluwatar
 *
 */
public class TooltipsyBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	
	private Component hostComponent;

	private TooltipsyOptions options = new TooltipsyOptions();

	private String overrideSelector = "";
	
	public TooltipsyBehavior() {
	}
	
	public TooltipsyBehavior(TooltipsyOptions options) {
		this.options = new TooltipsyOptions(options);
	}

	public TooltipsyOptions getOptions() {
		return new TooltipsyOptions(options);
	}

	public void setOptions(TooltipsyOptions options) {
		this.options = new TooltipsyOptions(options);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(this.getClass(), "tooltipsy.min.js")));
		if (getHostComponent() != null) {
			String selector = "#" + getHostComponent().getMarkupId();
			if (!overrideSelector.equals("")) {
				selector = overrideSelector;
			}
			String javascript = "$('" + selector + "').tooltipsy(" + options.getParameterString() + ");";
			response.render(OnLoadHeaderItem.forScript(javascript));
		}
	}

	public Component getHostComponent() {
		return hostComponent;
	}

	public void setHostComponent(Component hostComponent) {
		this.hostComponent = hostComponent;
	}
	
	@Override
	public void bind(Component component) {
		this.hostComponent = component;
	}
	
	@Override
	public void unbind(Component component) {
		this.hostComponent = null;
	}

	public String getOverrideSelector() {
		return overrideSelector;
	}

	public void setOverrideSelector(String overrideSelector) {
		this.overrideSelector = overrideSelector;
	}
	
}
