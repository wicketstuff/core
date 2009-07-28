package org.wicketstuff.jwicket.ui;


import org.apache.wicket.Request;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.jwicket.JQueryEmbeddedAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.SpecialKey;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryEmbeddedAjaxBehavior {

	private static final long serialVersionUID = 1L;

	public AbstractJqueryUiEmbeddedBehavior(final JQueryJavascriptResourceReference... requiredLibraries) {
		super(new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery-ui-core-1.7.2.min.js"), requiredLibraries);
	}

	
	
	protected SpecialKey[] getSpecialKeys(final Request request) {
		String rawKeys = request.getParameter("keys");
		SpecialKey[] specialKeys;
		if (rawKeys != null && rawKeys.length() > 0) {
			String[] strings = Strings.split(rawKeys, ',');
			specialKeys = new SpecialKey[strings.length];
			for (int i=0; i<strings.length; i++)
				specialKeys[i] = SpecialKey.getSpecialKey(strings[i]);
		}
		else
			specialKeys = new SpecialKey[0];

		return specialKeys;
	}

}
