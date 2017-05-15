package com.googlecode.wicket.jquery.ui.samples.resource;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.core.resource.JQueryUrlResourceReference;

/**
 * Provides the {@link ResourceReference} for the kendo-all javascript library (CDN).
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoAllResourceReference extends JQueryUrlResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final KendoAllResourceReference INSTANCE = new KendoAllResourceReference();

	/**
	 * Private constructor
	 */
	private KendoAllResourceReference()
	{
		super("http://kendo.cdn.telerik.com/2017.2.504/js/kendo.all.min.js");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static KendoAllResourceReference get()
	{
		return INSTANCE;
	}
}
