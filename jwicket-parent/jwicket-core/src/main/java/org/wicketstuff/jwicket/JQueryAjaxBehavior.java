package org.wicketstuff.jwicket;


import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


/**
 * This is the base class for the jQuery integration with wicket.
 */
public abstract class JQueryAjaxBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private final JavascriptResourceReference baseLibrary;
	private final JavascriptResourceReference[] requiredLibraries;

	public static final String getVersion() {
		return "0.4.0";
	}


	protected JQueryAjaxBehavior(
				final JavascriptResourceReference baseLibrary,
				final JavascriptResourceReference[] requiredLibraries) {
		super();
		this.baseLibrary = baseLibrary;
		this.requiredLibraries = requiredLibraries;
	}



	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		addJavascriptReference(response, new JavascriptResourceReference(JQueryAjaxBehavior.class, "jquery-1.3.2.min.js"));

		response.renderJavascript("jQuery.noConflict();", "noConflict");

		if (baseLibrary != null)
			addJavascriptReference(response, baseLibrary);
		for (JavascriptResourceReference requiredLibrary : requiredLibraries)
			addJavascriptReference(response, requiredLibrary);
	}



	protected void addJavascriptReference(IHeaderResponse response, JavascriptResourceReference resource) {
		response.renderJavascriptReference(resource);
	}



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




	protected static class JsMap extends HashMap<String, Object> implements Serializable {

		private static final long serialVersionUID = 1L;

		public JsMap() {
			super();
		}


		public String toString(final String rawOptions) {
			if (isEmpty()) {
				return "";
			}
			StringBuffer buffer = new StringBuffer();
			toString(buffer, rawOptions);
			return buffer.toString();
		}

		public void toString(final StringBuffer buffer, final String rawOptions) {
			boolean first = true;
			for (Iterator<String> iter = keySet().iterator(); iter.hasNext();) {
				if (!first)
					buffer.append(",");
				else
					first = false;

				String key = iter.next();
				Object value = get(key);

				buffer.append(key);
				buffer.append(":");
				if (value instanceof CharSequence) {
					buffer.append("'");
					buffer.append(value);
					buffer.append("'");
				}
				else if (value instanceof JsOption[]) {
					boolean firstOption = true;
					buffer.append("{");
					for (JsOption o : ((JsOption[])value)) {
						if (!firstOption)
							buffer.append(",");
						else
							firstOption = false;
						buffer.append(o);
					}
					buffer.append("}");
				}
				else if (value instanceof JsOption) {
					buffer.append("{");
					buffer.append((JsOption)value);
					buffer.append("}");
				}
				else if (value instanceof Object[]) {
					boolean firstOption = true;
					buffer.append("[");
					for (Object o : ((Object[])value)) {
						if (!firstOption)
							buffer.append(",");
						else
							firstOption = false;
						if (o instanceof CharSequence) {
							buffer.append("'");
							buffer.append(o);
							buffer.append("'");
						}
						else
							buffer.append(o);
					}
					buffer.append("]");
				}
				else
					buffer.append(value);
			}
			if (rawOptions != null) {
				buffer.append(",");
				buffer.append(rawOptions);
			}
		}

		public void put(final String key, JsOption...options) {
			super.put(key, options);
		}

		public void put(final String key, Object...options) {
			super.put(key, options);
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



	protected static class JsOption implements Serializable {

		private static final long serialVersionUID = 1L;
		private final String name;
		private final String value;

		public JsOption(final String name, final String value) {
			this.name = name;
			this.value = value;
		}

		public JsOption(final String name, final int value) {
			this.name = name;
			this.value = String.valueOf(value);
		}

		public String toString() {
			return name + ":" + value;
		}
	}
}
