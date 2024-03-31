package org.wicketstuff.facebook;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Till Freier
 * 
 */
public class HomePage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HomePage()
	{
		super();
	}

	/**
	 * @param model
	 */
	public HomePage(final IModel<?> model)
	{
		super(model);
	}

	/**
	 * @param parameters
	 */
	public HomePage(final PageParameters parameters)
	{
		super(parameters);
	}

}
