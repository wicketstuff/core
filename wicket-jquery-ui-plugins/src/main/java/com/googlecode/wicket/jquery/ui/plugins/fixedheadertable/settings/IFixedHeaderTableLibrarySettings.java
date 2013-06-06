package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

public interface IFixedHeaderTableLibrarySettings extends IJavaScriptLibrarySettings {
	CssResourceReference getFixedHeaderTableStyleSheetReference();
	JavaScriptResourceReference getFixedHeaderTableJavaScriptReference();
}
