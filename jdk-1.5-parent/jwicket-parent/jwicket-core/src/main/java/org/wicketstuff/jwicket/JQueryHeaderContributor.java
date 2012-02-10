package org.wicketstuff.jwicket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


public class JQueryHeaderContributor extends AbstractBehavior {

	private static final long serialVersionUID = 1L;

	public static final JQueryJavascriptResourceReference jQueryCoreJs
		= JQuery.isDebug()
		? new JQueryJavascriptResourceReference(JQuery.class, "jquery-1.4.4.js")
		: new JQueryJavascriptResourceReference(JQuery.class, "jquery-1.4.4.min.js");

	
	private final JQueryJavascriptResourceReference baseLibrary;
	private final JQueryJavascriptResourceReference[] requiredLibraries;


	public JQueryHeaderContributor(final JQueryJavascriptResourceReference baseLibrary) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = new JQueryJavascriptResourceReference[0];
	}

	public JQueryHeaderContributor(
				final JQueryJavascriptResourceReference baseLibrary,
				final JQueryJavascriptResourceReference... requiredLibraries) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = requiredLibraries;
	}

	




	protected void addJavascriptReference(IHeaderResponse response, JavascriptResourceReference resource) {
		if (!response.wasRendered(resource)) {
			response.renderJavascriptReference(resource);
			response.markRendered(resource);
		}
	}

	protected void addJavascriptReference(IHeaderResponse response, JQueryJavascriptResourceReference resource) {
		if (!response.wasRendered(resource)) {
			response.renderJavascriptReference(resource);
			response.markRendered(resource);
		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		if (userProvidedResourceReferences.size() == 0) {
			// No user provided Resources, use internal resources
			addJavascriptReference(response, jQueryCoreJs);
			response.renderJavascript("jQuery.noConflict();", "noConflict");

			if (baseLibrary != null) {
				addJavascriptReference(response, baseLibrary);
			}
			if (requiredLibraries != null)
				for (JQueryJavascriptResourceReference requiredLibrary : requiredLibraries) {
					addJavascriptReference(response, requiredLibrary);
				}
		}
		else {
			// Userdefined resources, use them but also use the NOT_OVERRIDABLE internal resources
			for (JavascriptResourceReference userLibrary : userProvidedResourceReferences) {
				addJavascriptReference(response, userLibrary);
			}

			if (baseLibrary != null && baseLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
				addJavascriptReference(response, baseLibrary);
			}
			if (requiredLibraries != null)
				for (JQueryJavascriptResourceReference requiredLibrary : requiredLibraries)
					if (requiredLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
						addJavascriptReference(response, requiredLibrary);
					}
		}
	}


	private static final List<JavascriptResourceReference> userProvidedResourceReferences = new ArrayList<JavascriptResourceReference>();

	public static final void addUserProvidedResourceReferences(final JavascriptResourceReference...resources) {
		userProvidedResourceReferences.addAll(Arrays.asList(resources));
	}
	public static List<JavascriptResourceReference> getUserProvidedResourceReferences() {
		return userProvidedResourceReferences;
	}



}
