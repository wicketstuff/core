package org.wicketstuff.jwicket;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.ClientInfo;


/**
 * This is the base class for the jQuery integration with wicket.
 */
public class JQuery extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private final JavascriptResourceReference baseLibrary;
	private final JavascriptResourceReference[] requiredLibraries;

	public static final String getVersion() {
		return "0.4.1";
	}


	public JQuery(final JavascriptResourceReference baseLibrary) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = new JavascriptResourceReference[0];
	}

	public JQuery(
				final JavascriptResourceReference baseLibrary,
				final JavascriptResourceReference... requiredLibraries) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = requiredLibraries;
	}


	protected void addJavascriptReference(IHeaderResponse response, JavascriptResourceReference resource) {
		response.renderJavascriptReference(resource);
	}


	protected int ieVersion = -1;

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		if (ieVersion < 0) {
			ClientInfo ci = WebSession.get().getClientInfo();
			if (ci instanceof WebClientInfo) {
				WebClientInfo wci = (WebClientInfo)ci;
				ClientProperties cp = wci.getProperties();
				if (cp.isBrowserInternetExplorer()) {
					ieVersion = cp.getBrowserVersionMajor();
				}
				else
					ieVersion = 0;
			}
		}

System.out.println("ieVersion = " + ieVersion);

		if (userProvidedResourceReferences.size() == 0) {
			addJavascriptReference(response, new JavascriptResourceReference(JQuery.class, "jquery-1.3.2.min.js"));

			response.renderJavascript("jQuery.noConflict();", "noConflict");

			if (baseLibrary != null)
				addJavascriptReference(response, baseLibrary);
			for (JavascriptResourceReference requiredLibrary : requiredLibraries)
				addJavascriptReference(response, requiredLibrary);
		}
		else
			for (JavascriptResourceReference userLibrary : userProvidedResourceReferences)
				addJavascriptReference(response, userLibrary);
	}


	private static final List<JavascriptResourceReference> userProvidedResourceReferences = new ArrayList<JavascriptResourceReference>();

	public static final void addUserProvidedResourceReferences(final JavascriptResourceReference ressources) {
		userProvidedResourceReferences.addAll(Arrays.asList(ressources));
	}
	public static List<JavascriptResourceReference> getUserProvidedResourceReferences() {
		return userProvidedResourceReferences;
	}




	@Override
	protected void respond(AjaxRequestTarget target) { }



	/**
	 * for debugging only
	 */
	protected void printParameters(final PrintStream stream, final Map<String, String[]> parameterMap) {
		for (String key : parameterMap.keySet()) {
			String valuesString = "";
			for (String value : parameterMap.get(key)) {
				if (valuesString.length() > 0)
					valuesString += ", ";
				valuesString += value;
			}
			stream.println("\t" + key + " = " + valuesString);
		}
	}

	protected void printParameters(final Map<String, String[]> parameterMap) {
		printParameters(System.out, parameterMap);
	}

}
