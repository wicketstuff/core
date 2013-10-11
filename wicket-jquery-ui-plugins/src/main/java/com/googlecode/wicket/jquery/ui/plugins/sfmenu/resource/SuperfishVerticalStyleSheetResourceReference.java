package com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource;

import org.apache.wicket.request.resource.CssResourceReference;

public class SuperfishVerticalStyleSheetResourceReference extends CssResourceReference
{
	private static final long serialVersionUID = 1L;
	private static final SuperfishVerticalStyleSheetResourceReference INSTANCE = new SuperfishVerticalStyleSheetResourceReference();

	/**
	 * Private constructor
	 */
	private SuperfishVerticalStyleSheetResourceReference()
	{
		super(SuperfishVerticalStyleSheetResourceReference.class, "css/superfish-vertical.css");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static SuperfishVerticalStyleSheetResourceReference get()
	{
		return INSTANCE;
	}
}
