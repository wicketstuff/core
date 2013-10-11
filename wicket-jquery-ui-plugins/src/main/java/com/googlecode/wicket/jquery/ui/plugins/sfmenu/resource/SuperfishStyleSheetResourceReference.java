package com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource;

import org.apache.wicket.request.resource.CssResourceReference;

public class SuperfishStyleSheetResourceReference extends CssResourceReference
{
	private static final long serialVersionUID = 1L;
	private static final SuperfishStyleSheetResourceReference INSTANCE = new SuperfishStyleSheetResourceReference();

	/**
	 * Private constructor
	 */
	private SuperfishStyleSheetResourceReference()
	{
		super(SuperfishStyleSheetResourceReference.class, "css/superfish.css");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static SuperfishStyleSheetResourceReference get()
	{
		return INSTANCE;
	}
}
