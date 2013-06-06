package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource;

import org.apache.wicket.resource.JQueryPluginResourceReference;

public class FixedHeaderTableJavaScriptResourceReference extends JQueryPluginResourceReference {
	private static final long serialVersionUID = 1L;
	private static final FixedHeaderTableJavaScriptResourceReference INSTANCE = new FixedHeaderTableJavaScriptResourceReference();
	
	public static FixedHeaderTableJavaScriptResourceReference get() {
		return INSTANCE;
	}
	
	private FixedHeaderTableJavaScriptResourceReference() {
		super(FixedHeaderTableJavaScriptResourceReference.class, "jquery.fixedheadertable.min.js");
	}
}
