package com.googlecode.wicket.kendo.ui;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;

/**
 * {@link IHeaderContributor} to automatically add the {@link KendoCultureHeaderItem} to each rendered page, using the specified culture.<br/>
 * If no culture is specified, the Session's Locale *culture* will be used. If that culture is invalid, the Session's Locale *language* will be used. If Session's Locale *language* is still invalid, the {@code IHeaderContributor} will not be rendered, providing a natural fallback to default widget's culture/language.<br/>
 * <br/>
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
 * 		this.getHeaderContributorListeners().add(new KendoCultureHeaderContributor());
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
	 * Constructor that will use the current {@link Session#getLocale()} culture/language
	 */
	public KendoCultureHeaderContributor()
	{
		this.culture = null;
	}

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
	 * @param culture the culture or language, ie: 'fr' or 'fr-FR'
	 */
	public KendoCultureHeaderContributor(String culture)
	{
		this.culture = culture;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		final Locale locale = Session.get().getLocale();

		if (locale != null)
		{
			String culture = KendoCulture.get(this.culture, locale.toLanguageTag(), locale.getLanguage());

			if (culture != null)
			{
				response.render(new PriorityHeaderItem(new KendoCultureHeaderItem(culture)));
			}
		}
	}
}
