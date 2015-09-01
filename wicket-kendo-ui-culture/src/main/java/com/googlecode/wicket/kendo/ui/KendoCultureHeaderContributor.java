package com.googlecode.wicket.kendo.ui;

import java.util.Locale;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;

/**
 * {@link IHeaderContributor} to easily add {@link KendoCultureHeaderItem} to each page by using {@code org.apache.wicket.Application#getHeaderContributorListeners().add(new KendoCultureHeaderContributor(locale))} <br/>
 * Usage:
 * 
 * <pre>
 * <code>
 * public class MyApplication extends WebApplication
 * {
 * 	public void init()
 * 	{
 * 		super.init();
 * 		
 * 		getHeaderContributorListenerCollection().add(new KendoCultureHeaderContributor(Locale.GERMANY));
 * 	}
 * }
 * </code>
 * </pre>
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public class KendoCultureHeaderContributor implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	private final String culture;

	/**
	 * Constructor
	 * 
	 * @param locale the {@link Locale}, ie: Locale.FRENCH
	 */
	public KendoCultureHeaderContributor(Locale locale)
	{
		this(locale.toLanguageTag()); // java7
	}

	/**
	 * Constructor
	 * 
	 * @param culture the {@link KendoCulture}
	 */
	public KendoCultureHeaderContributor(KendoCulture culture)
	{
		this(culture.toString());
	}

	/**
	 * Constructor
	 * 
	 * @param culture the culture, ie: 'fr' or 'fr-FR'
	 */
	public KendoCultureHeaderContributor(String culture)
	{
		this.culture = culture;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(new PriorityHeaderItem(new KendoCultureHeaderItem(this.culture)));
	}
}
