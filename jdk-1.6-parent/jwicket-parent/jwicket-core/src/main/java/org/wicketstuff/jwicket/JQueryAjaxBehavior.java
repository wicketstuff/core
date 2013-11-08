package org.wicketstuff.jwicket;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

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
		this(baseLibrary, new JQueryResourceReference[0]);
	}

	public JQueryAjaxBehavior(final JQueryResourceReference baseLibrary,
	    final JQueryResourceReference... requiredLibraries) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = requiredLibraries;
	}

	protected String rawOptions = null;

	public void setRawOptions(final String options) {
		this.rawOptions = options;
	}

	protected static class JsBuilder implements Serializable {

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
			if (object instanceof JsMap) {
				((JsMap) object).toString(this.buffer, null);
			} else if (object instanceof Boolean) {
				this.buffer.append(((Boolean) object).toString());
			} else if (object instanceof JsFunction) {
				((JsFunction) object).toString(this.buffer);
			} else if (object instanceof String[]) {
				this.buffer.append("[");
				boolean first = true;
				for (String s : (String[]) object) {
					if (!first) {
						this.buffer.append(",");
					}
					this.buffer.append("'");
					this.buffer.append(s);
					this.buffer.append("'");
					first = false;
				}
				this.buffer.append("]");
			} else {
				this.buffer.append(object.toString());
			}
		}

		public String toScriptTag() {
			// return "\n<script type=\"text/javascript\">\n" + buffer.toString() + "\n</script>\n";
			return "\n" + this.buffer.toString() + "\n";
		}

		@Override
		public String toString() {
			return this.buffer.toString();
		}

		public int length() {
			return this.buffer.length();
		}
	}

	public static class JsFunction implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String function;

		public JsFunction(final String function) {
			this.function = function;
		}

		public String getFunction() {
			return this.function;
		}

		@Override
		public String toString() {
			return this.function;
		}

		public void toString(final StringBuffer buffer) {
			buffer.append(this.function);
		}
	}

	protected static class JsAjaxCallbackFunction extends JsFunction {

		private static final long serialVersionUID = 1L;

		public JsAjaxCallbackFunction(AbstractAjaxBehavior behavior) {
			super("function() { Wicket.Ajax.get('" + behavior.getCallbackUrl() + "'); }");
		}
	}

	private void addJavascriptReference(IHeaderResponse response, JavaScriptResourceReference resource) {
		if (!response.wasRendered(resource)) {
			response.render(JavaScriptReferenceHeaderItem.forReference(resource));
			response.markRendered(resource);
		}
	}

	private void addJavascriptReference(IHeaderResponse response, JQueryResourceReference resource) {
		if (!response.wasRendered(resource)) {
            if (resource instanceof JQueryCssResourceReference) {
                response.render(CssReferenceHeaderItem.forReference(resource));
			} else  {
                if (resource.hasId()) {
                    response.render(JavaScriptReferenceHeaderItem.forReference(resource, resource.getId()));
                } else {
                    response.render(JavaScriptReferenceHeaderItem.forReference(resource));
                }
			}
			response.markRendered(resource);
		}
	}


    private void addCssResourceReference(IHeaderResponse response, JQueryCssResourceReference resource) {
        if (!response.wasRendered(resource)) {
            response.render(CssReferenceHeaderItem.forReference(resource));
            response.markRendered(resource);
        }
    }


    // protected int ieVersion = -1;

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		if (userProvidedResourceReferences.size() == 0) {
			if (this.baseLibrary != null) {
				addJavascriptReference(response, this.baseLibrary);
			}
			if (this.requiredLibraries != null) {
				for (JQueryResourceReference requiredLibrary : this.requiredLibraries) {
					addJavascriptReference(response, requiredLibrary);
				}
			}
		} else {
			// Userdefined resources, use them but also use the NOT_OVERRIDABLE internal resources
			for (JavaScriptResourceReference userLibrary : userProvidedResourceReferences) {
				addJavascriptReference(response, userLibrary);
			}

			if (this.baseLibrary != null && this.baseLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
				addJavascriptReference(response, this.baseLibrary);
			}
			if (this.requiredLibraries != null) {
				for (JQueryResourceReference requiredLibrary : this.requiredLibraries) {
					if (requiredLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
						addJavascriptReference(response, requiredLibrary);
					}
				}
			}
		}

		for (JQueryResourceReference res : this.additionLibraries) {
			addJavascriptReference(response, res);
		}

		if (this.cssResources != null) {
			for (JQueryCssResourceReference res : this.cssResources) {
                addCssResourceReference(response, res);
			}
		}
	}

    private static final List<JavaScriptResourceReference> userProvidedResourceReferences = new ArrayList<JavaScriptResourceReference>();

	public static void addUserProvidedResourceReferences(final JavaScriptResourceReference... resources) {
		userProvidedResourceReferences.addAll(Arrays.asList(resources));
	}

	public static List<JavaScriptResourceReference> getUserProvidedResourceReferences() {
		return userProvidedResourceReferences;
	}

	protected final void addUserProvidedResourceReferences(final JQueryResourceReference... resources) {
		Collections.addAll(this.additionLibraries, resources);
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
	}

	/**
	 * for debugging only
	 */
	protected void printParameters(final PrintStream stream, final Map<String, String[]> parameterMap) {
		for (String key : parameterMap.keySet()) {
			String valuesString = "";
			for (String value : parameterMap.get(key)) {
				if (valuesString.length() > 0) {
					valuesString += ", ";
				}
				valuesString += value;
			}
			stream.println("\t" + key + " = " + valuesString);
		}
	}

	protected void printParameters(final Map<String, String[]> parameterMap) {
		printParameters(System.out, parameterMap);
	}

	protected void addCssResources(final JQueryCssResourceReference... res) {
		if (res == null || res.length == 0) {
			return;
		}
		if (this.cssResources == null) {
			this.cssResources = new ArrayList<JQueryCssResourceReference>();
		}
		Collections.addAll(this.cssResources, res);
	}

}
