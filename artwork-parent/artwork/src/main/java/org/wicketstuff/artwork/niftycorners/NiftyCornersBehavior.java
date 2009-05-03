package org.wicketstuff.artwork.niftycorners;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class NiftyCornersBehavior extends AbstractBehavior {

	String tagType = "";

	public NiftyCornersBehavior(String tagType) {
		this.tagType = tagType;
	}

	/** The target component. */
	private Component component;

	@Override
	public void bind(Component component) {
		super.bind(component);
		this.component = component;
		component.setOutputMarkupId(true);

	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(getNiftyCornersJSReference());
		response.renderCSSReference(getNiftyCornersCSSReference());
		response.renderOnLoadJavascript(getNiftyJS());
	}

	private String getNiftyJS() {

		return "Nifty(\""+tagType+"#" + component.getMarkupId() + "\",\"big\")";
	}

	private ResourceReference getNiftyCornersJSReference() {
		return new ResourceReference(NiftyCornersBehavior.class, "niftycube.js");

	}
	private ResourceReference getNiftyCornersCSSReference() {
		return new ResourceReference(NiftyCornersBehavior.class, "niftyCorners.css");

	}

}
