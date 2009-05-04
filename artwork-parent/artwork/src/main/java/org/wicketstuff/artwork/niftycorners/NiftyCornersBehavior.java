package org.wicketstuff.artwork.niftycorners;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.internal.HeaderResponse;
import org.apache.wicket.util.string.JavascriptUtils;

public class NiftyCornersBehavior extends AbstractBehavior {

	String tagName = "";

	public NiftyCornersBehavior() {
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
//		response.renderOnLoadJavascript(getNiftyJS(tagName));
	}

	private String getNiftyJS(String tagName) {

		return "Nifty(\"" + tagName + "#" + component.getMarkupId()
				+ "\",\"big\")";
	}

	private ResourceReference getNiftyCornersJSReference() {
		return new ResourceReference(NiftyCornersBehavior.class, "niftycube.js");

	}

	private ResourceReference getNiftyCornersCSSReference() {
		return new ResourceReference(NiftyCornersBehavior.class,
				"niftyCorners.css");

	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		tagName = tag.getName();
	}

	@Override
	public void onRendered(final Component component) {
		// TODO Auto-generated method stub
		super.onRendered(component);
		HeaderResponse headerResponse=new HeaderResponse(){
		
			@Override
			protected Response getRealResponse() {
				// TODO Auto-generated method stub
				return component.getResponse();
			}
		};
		headerResponse.renderOnDomReadyJavascript(getNiftyJS(tagName));
	}
}
