package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource;

import org.apache.wicket.request.resource.CssResourceReference;

public class FixedHeaderTableStyleSheetResourceReference extends CssResourceReference {
	private static final long serialVersionUID = 1L;
	private static final FixedHeaderTableStyleSheetResourceReference INSTANCE = new FixedHeaderTableStyleSheetResourceReference();
	
	public static FixedHeaderTableStyleSheetResourceReference get() {
		return INSTANCE;
	}
	
	private FixedHeaderTableStyleSheetResourceReference() {
		super(FixedHeaderTableStyleSheetResourceReference.class, "jquery.fixedheadertable.css");
	}
}
