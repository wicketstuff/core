package com.googlecode.wicket.jquery.core.resource;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.DynamicJQueryResourceReference;

/**
 * Provides the {@link ResourceReference} for the {@code jquery-migrate-3.0.0.js} javascript library (CDN).<br>
 * <code><pre>
 * public class MyApplication extends WebApplication
 * {
 * 	public void init()
 * 	{
 * 		super.init();
 * 
 * 		this.getJavaScriptLibrarySettings().setJQueryReference(JQueryMigrateResourceReference.get());
 * 	}
 * }
 * 
 * </pre></code>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryMigrateResourceReference extends JQueryUrlResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final JQueryMigrateResourceReference INSTANCE = new JQueryMigrateResourceReference();

	/**
	 * Private constructor
	 */
	private JQueryMigrateResourceReference()
	{
		super("https://code.jquery.com/jquery-migrate-3.0.0.js");
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

	@Override
	public List<HeaderItem> getDependencies()
	{
		return Arrays.asList(JavaScriptHeaderItem.forReference(DynamicJQueryResourceReference.getV3()));
	}
}
