package org.wicketstuff.jquery.core.resource;

import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.apache.wicket.util.lang.Generics;

/**
 * Provides the {@link ResourceReference} for the {@code jquery-migrate-3.0.0.js} javascript library (CDN).<br>
 * 
 * <pre>
 * <code>
 * public class MyApplication extends WebApplication
 * {
 * 	public void init()
 * 	{
 * 		super.init();
 * 
 * 		this.getJavaScriptLibrarySettings().setJQueryReference(JQueryMigrateResourceReference.get());
 * 	}
 * }
 * </code>
 * </pre>
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
	public static JQueryMigrateResourceReference get()
	{
		return INSTANCE;
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = Generics.newArrayList();
		dependencies.add(JavaScriptHeaderItem.forReference(JQueryResourceReference.getV3()));

		return dependencies;
	}
}
