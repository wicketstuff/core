package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable;

import org.apache.wicket.Application;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings.IFixedHeaderTableLibrarySettings;

public class FixedHeaderTableBehavior extends JQueryBehavior {
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "fixedHeaderTable";

	public FixedHeaderTableBehavior(String selector) {
		this(selector, new Options());
	}

	public FixedHeaderTableBehavior(String selector, Options options) {
		super(selector, METHOD, options);
		initReferences();
	}
	
	private void initReferences() {
		IFixedHeaderTableLibrarySettings settings = this.getLibrarySettings();
		
		if (settings != null && settings.getFixedHeaderTableStyleSheetReference() != null) {
			this.add(settings.getFixedHeaderTableStyleSheetReference());
		} else {
			this.add(FixedHeaderTableStyleSheetResourceReference.get());
		}

		if (settings != null && settings.getFixedHeaderTableJavaScriptReference() != null) {
			this.add(settings.getFixedHeaderTableJavaScriptReference());
		} else {
			this.add(FixedHeaderTableJavaScriptResourceReference.get());
		}
	}

	private IFixedHeaderTableLibrarySettings getLibrarySettings() {
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IFixedHeaderTableLibrarySettings)) {
			return (IFixedHeaderTableLibrarySettings)Application.get().getJavaScriptLibrarySettings();
		}
		return null;
	}
}
