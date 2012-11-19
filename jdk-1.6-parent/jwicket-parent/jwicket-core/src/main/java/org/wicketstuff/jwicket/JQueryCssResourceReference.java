package org.wicketstuff.jwicket;


public class JQueryCssResourceReference extends JQueryResourceReference {

	private static final long serialVersionUID = 1L;


	public JQueryCssResourceReference(final Class<?> scope, final String name) {
		this(scope, name, JQueryResourceReferenceType.OVERRIDABLE);
	}

	public JQueryCssResourceReference(final Class<?> scope, final String name, final JQueryResourceReferenceType type) {
		super(scope, name, type);
	}

}
