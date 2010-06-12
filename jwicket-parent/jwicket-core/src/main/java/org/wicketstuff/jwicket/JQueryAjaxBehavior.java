package org.wicketstuff.jwicket;


import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


/**
 * This is the base class for the jQuery integration with wicket.
 */
public abstract class JQueryAjaxBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private final JQueryResourceReference baseLibrary;
	private final JQueryResourceReference[] requiredLibraries;
	private final List<JQueryResourceReference> additionLibraries = new ArrayList<JQueryResourceReference>();
	private List<JQueryCssResourceReference> cssResources;


	public JQueryAjaxBehavior(final JQueryResourceReference baseLibrary) {
		this(baseLibrary, new JQueryJavascriptResourceReference[0]);
	}

	public JQueryAjaxBehavior(
				final JQueryResourceReference baseLibrary,
				final JQueryResourceReference... requiredLibraries) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = requiredLibraries;
	}



	protected String rawOptions = null;
	public void setRawOptions(final String options) {
		this.rawOptions = options;
	}


	protected static class JsBuilder implements Serializable{

		private static final long serialVersionUID = 1L;

		private final StringBuffer buffer;

		public JsBuilder() {
			this.buffer = new StringBuffer();
		}

		public JsBuilder(final Object object) {
			this.buffer = new StringBuffer();
			append(object);
		}



		public void append(final Object object) {
			if (object instanceof JsMap)
				((JsMap)object).toString(buffer, null);
			else if (object instanceof Boolean)
				buffer.append(((Boolean)object).toString());
			else if (object instanceof JsFunction)
				((JsFunction)object).toString(buffer);
			else if (object instanceof String[]) {
				buffer.append("[");
				boolean first = true;
				for (String s : (String[])object) {
					if (!first)
						buffer.append(",");
					buffer.append("'");
					buffer.append(s);
					buffer.append("'");
					first = false;
				}
				buffer.append("]");
			}
			else {
				buffer.append(object.toString());
			}
		}


		public String toScriptTag() {
			//return "\n<script type=\"text/javascript\">\n" + buffer.toString() + "\n</script>\n";
			return "\n" + buffer.toString() + "\n";
		}

		public String toString() {
			return buffer.toString();
		}

		public int length() {
			return buffer.length();
		}
	}




	protected static class JsFunction implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String function;

		public JsFunction(final String function) {
			this.function = function;
		}

		public String getFunction() {
			return function;
		}

		public String toString() {
			return function;
		}

		public void toString(final StringBuffer buffer) {
			buffer.append(function);
		}
	}



	protected static class JsAjaxCallbackFunction extends JsFunction {

		private static final long serialVersionUID = 1L;

		public JsAjaxCallbackFunction(AbstractAjaxBehavior behavior) {
			super("function() { wicketAjaxGet('" + behavior.getCallbackUrl() + "'); }");
		}
	}



	private void addJavascriptReference(IHeaderResponse response, JavascriptResourceReference resource) {
		if (!response.wasRendered(resource)) {
			response.renderJavascriptReference(resource);
			response.markRendered(resource);
		}
	}

	private void addJavascriptReference(IHeaderResponse response, JQueryResourceReference resource) {
		if (!response.wasRendered(resource)) {
			if (resource instanceof org.wicketstuff.jwicket.JQueryJavascriptResourceReference)
				response.renderJavascriptReference(resource);
			else
				response.renderCSSReference(resource);
			response.markRendered(resource);
		}
	}


//	protected int ieVersion = -1;

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		/*
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
		*/

		if (userProvidedResourceReferences.size() == 0) {
			// No user provided Resources, use internal resources
			addJavascriptReference(response, JQueryHeaderContributor.jQueryCoreJs);
			response.renderJavascript("jQuery.noConflict();", "noConflict");

			if (baseLibrary != null)
				addJavascriptReference(response, baseLibrary);
			if (requiredLibraries != null)
				for (JQueryResourceReference requiredLibrary : requiredLibraries)
					addJavascriptReference(response, requiredLibrary);
		}
		else {
			// Userdefined resources, use them but also use the NOT_OVERRIDABLE internal resources
			for (JavascriptResourceReference userLibrary : userProvidedResourceReferences)
				addJavascriptReference(response, userLibrary);

			if (baseLibrary != null && baseLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE)
				addJavascriptReference(response, baseLibrary);
			if (requiredLibraries != null)
				for (JQueryResourceReference requiredLibrary : requiredLibraries)
					if (requiredLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE)
						addJavascriptReference(response, requiredLibrary);
		}

		for (JQueryResourceReference res : additionLibraries)
			addJavascriptReference(response, res);

		if (cssResources != null)
			for (JQueryCssResourceReference res : cssResources)
				addJavascriptReference(response, res);
	}


	private static final List<JavascriptResourceReference> userProvidedResourceReferences = new ArrayList<JavascriptResourceReference>();

	public static final void addUserProvidedResourceReferences(final JavascriptResourceReference...resources) {
		userProvidedResourceReferences.addAll(Arrays.asList(resources));
	}
	public static List<JavascriptResourceReference> getUserProvidedResourceReferences() {
		return userProvidedResourceReferences;
	}


	

	protected final void addUserProvidedResourceReferences(final JQueryResourceReference...resources) {
		for (JQueryResourceReference res : resources)
			additionLibraries.add(res);
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



	protected void addCssResources(final JQueryCssResourceReference ... res) {
		if (res == null || res.length == 0)
			return;
		if (cssResources == null)
			cssResources = new ArrayList<JQueryCssResourceReference>();
		for (JQueryCssResourceReference r : res)
			cssResources.add(r);
	}

}
