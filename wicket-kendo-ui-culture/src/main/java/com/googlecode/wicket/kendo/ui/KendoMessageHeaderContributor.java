package com.googlecode.wicket.kendo.ui;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;

/**
 * {@link IHeaderContributor} to easily add {@link KendoMessageHeaderItem} to each page by using {@code org.apache.wicket.Application#getHeaderContributorListeners().add(new KendoCultureHeaderContributor(locale))} <br/>
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
 * 		this.getHeaderContributorListenerCollection().add(new KendoMessageHeaderContributor(KendoMessage.FR_FR));
 * 	}
 * }
 * </code>
 * </pre>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoMessageHeaderContributor implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	private final String language;

	/**
	 * Constructor
	 * 
	 * @param culture the {@link KendoMessage}
	 */
	public KendoMessageHeaderContributor(KendoMessage culture)
	{
		this(culture.toString());
	}

	/**
	 * Constructor
	 * 
	 * @param language the language, ie: 'fr-FR'
	 */
	public KendoMessageHeaderContributor(String language)
	{
		this.language = language;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(new PriorityHeaderItem(new KendoMessageHeaderItem(this.language)));
	}
}
