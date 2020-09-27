package com.googlecode.wicket.kendo.ui.resource;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.core.resource.JQueryUrlResourceReference;

/**
 * Provides the {@link ResourceReference} for the kendo-all javascript library (CDN).<br>
 * Excerpt: Kendo UI commercial licenses may be obtained at<br>
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete<br>
 * If you do not own a commercial license, this file shall be governed by the trial license terms.<br>
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
		super("http://kendo.cdn.telerik.com/2020.3.915/js/kendo.all.min.js");
	}

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static JQueryUrlResourceReference get()
	{
		return INSTANCE;
	}
}
