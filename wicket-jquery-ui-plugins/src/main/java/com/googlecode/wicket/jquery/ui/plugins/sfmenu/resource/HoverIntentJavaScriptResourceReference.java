package com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource;

import org.apache.wicket.resource.JQueryPluginResourceReference;

public class HoverIntentJavaScriptResourceReference extends JQueryPluginResourceReference
{
	private static final long serialVersionUID = 1L;
	private static final HoverIntentJavaScriptResourceReference INSTANCE = new HoverIntentJavaScriptResourceReference();

	/**
	 * Private constructor
	 */
	private HoverIntentJavaScriptResourceReference()
	{
		super(HoverIntentJavaScriptResourceReference.class, "js/hoverIntent.js");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static HoverIntentJavaScriptResourceReference get()
	{
		return INSTANCE;
	}

}
