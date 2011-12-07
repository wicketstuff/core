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

	protected String tagName = "";
	protected List<NiftyOption> niftyOptions = null;
	protected String decendantsOfSelector = null;
	protected boolean grabTag = false;
	protected boolean singleComponent;

	/**
	 * Select by component tag (for example for a specific div)
	 */
	public NiftyCornersBehavior() {
		this(NiftyOption.normal);
	}

	/**
	 * for example "div#content h2" or "ul.news li" (dependent on idSelector ) ,
	 * you just provide h2 or a
	 * 
	 * You would typically add this behavior to the page or a component that
	 * contains other components
	 * 
	 * * @param decendentsOfSelector * @param grabTag should it automatically
	 * grab the tag? Use false if it's a page * @param singleComponent * @param
	 * niftyOptions
	 */
	public NiftyCornersBehavior(String decendentsOfSelector, boolean grabTag,
			boolean singleComponent, NiftyOption... niftyOptions) {
		this.niftyOptions = Arrays.asList(niftyOptions);
		this.decendantsOfSelector=decendentsOfSelector;
		this.grabTag = grabTag;
		this.singleComponent = singleComponent;

	}

	/**
	 * Select by component tag (for example for a specific div) Use for only
	 * applying nifty to a single component
	 * 
	 * @param niftyOptions
	 */
	public NiftyCornersBehavior(NiftyOption... niftyOptions) {
		this(null, true, true, niftyOptions);

	}

	/**
	 * One for all constructor :) Internal use only
	 * 
	 * 
	 * /** The target component.
	 */
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
		if (!grabTag && !singleComponent) {
			if (decendantsOfSelector != null) {
				response.renderOnLoadJavascript(getNiftyJS("",
						decendantsOfSelector, false, false));
			} else {
				response.renderOnLoadJavascript(getNiftyJS("",
						decendantsOfSelector, true, false));
			}
		}
	}

	private String getNiftyJS(String selectorString) {
		return getNiftyJS(selectorString, "", true, true);
	}

	private String getNiftyJS(String selectorString, String descendents,
			boolean shouldSeperate, boolean grabComponentTag) {
		String niftyOptionsString = "";
		for (NiftyOption niftyOption : this.niftyOptions) {
			if (niftyOptionsString.length() > 0) {
				niftyOptionsString += " ";
			}
			niftyOptionsString += niftyOption.toString();
		}

		String returnString = "Nifty(\"" + selectorString;
		if (shouldSeperate) {
			returnString += "#";
		}
		if (grabComponentTag) {
			returnString += component.getMarkupId() +  descendents
					+ "\",\"" + niftyOptionsString + "\")";
		} else {
			returnString += descendents + "\",\""
					+ niftyOptionsString + "\")";
		}
		return returnString;
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
		super.onRendered(component);
		if (grabTag) {
			HeaderResponse headerResponse = new HeaderResponse() {

				@Override
				protected Response getRealResponse() {
					// TODO Auto-generated method stub
					return component.getResponse();
				}
			};
			if (decendantsOfSelector == null) {
				headerResponse.renderOnLoadJavascript(getNiftyJS(tagName));
			} else {
				headerResponse.renderOnLoadJavascript(getNiftyJS(tagName,
						decendantsOfSelector, true,true));

			}
		}
	}
}
