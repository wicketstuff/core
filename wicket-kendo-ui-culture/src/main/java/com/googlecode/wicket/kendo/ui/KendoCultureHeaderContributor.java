package com.googlecode.wicket.kendo.ui;

import java.util.Locale;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.util.lang.Args;

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
 * 		Locale locale = Locale.GERMANY;
 * 		getHeaderContributorListenerCollection().add(new KendoCultureHeaderContributor(locale));
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

	private final Locale locale;

	/**
	 * Constructor
	 * 
	 * @param locale the {@link Locale}
	 */
	public KendoCultureHeaderContributor(Locale locale)
	{
		this.locale = Args.notNull(locale, "locale");
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(new KendoCultureHeaderItem(this.locale));
	}
}
