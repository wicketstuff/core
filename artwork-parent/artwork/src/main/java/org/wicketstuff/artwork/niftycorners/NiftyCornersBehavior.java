package org.wicketstuff.artwork.niftycorners;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.internal.HeaderResponse;

/**
 * 
 * @author nmwael
 * @see http://www.html.it/articoli/niftycube/index.html
 */
public class NiftyCornersBehavior extends AbstractBehavior {

	String tagName = "";
	List<NiftyOption> niftyOptions = null;

	public NiftyCornersBehavior() {
		this(NiftyOption.normal);
	}

	public NiftyCornersBehavior(NiftyOption... niftyOptions) {
		this.niftyOptions = Arrays.asList(niftyOptions);
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
		// response.renderOnLoadJavascript(getNiftyJS(tagName));
	}

	private String getNiftyJS(String tagName) {
		String niftyOptionsString = "";
		for (NiftyOption niftyOption : this.niftyOptions) {
			if (niftyOptionsString.length() > 0) {
				niftyOptionsString += ", ";
			}
			niftyOptionsString += niftyOption.toString();
		}

		return "Nifty(\"" + tagName + "#" + component.getMarkupId() + "\",\""
				+ niftyOptionsString + "\")";
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
		HeaderResponse headerResponse = new HeaderResponse() {

			@Override
			protected Response getRealResponse() {
				// TODO Auto-generated method stub
				return component.getResponse();
			}
		};
		headerResponse.renderOnDomReadyJavascript(getNiftyJS(tagName));
	}
}
