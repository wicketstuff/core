package org.wicketstuff.jwicket;


import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


public class JQueryJavascriptResourceReference extends JavascriptResourceReference {

	private static final long serialVersionUID = 1L;


	public JQueryJavascriptResourceReference(final Class<?> scope, final String name) {
		this(scope, name, JQueryJavascriptResourceReferenceType.OVERRIDABLE);
	}

	public JQueryJavascriptResourceReference(final Class<?> scope, final String name, final JQueryJavascriptResourceReferenceType type) {
		super(scope, name);
		this.type = type;
	}

	private final JQueryJavascriptResourceReferenceType type;

	public JQueryJavascriptResourceReferenceType getType() {
		return this.type;
	}
}
