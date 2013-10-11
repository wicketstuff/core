package com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource;

import org.apache.wicket.resource.JQueryPluginResourceReference;

public class SuperfishJavaScriptResourceReference extends JQueryPluginResourceReference
{
	private static final long serialVersionUID = 1L;
	private static final SuperfishJavaScriptResourceReference INSTANCE = new SuperfishJavaScriptResourceReference();

	/**
	 * Private constructor
	 */
	private SuperfishJavaScriptResourceReference()
	{
		super(SuperfishJavaScriptResourceReference.class, "js/superfish.js");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static SuperfishJavaScriptResourceReference get()
	{
		return INSTANCE;
	}

}
