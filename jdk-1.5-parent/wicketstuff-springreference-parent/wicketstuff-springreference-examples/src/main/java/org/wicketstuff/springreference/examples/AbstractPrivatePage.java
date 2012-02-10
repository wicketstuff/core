package org.wicketstuff.springreference.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.springreference.SpringReference;

/**
 * This page shows an example about using a {@link PrivateService service} that does not have a
 * public constructor. Dynamic proxies and <code>&#64;{@link SpringBean}</code> does not work with
 * these kind of classes but {@link SpringReference} does.
 * 
 * This page is accessible from the home page trough a link.
 * 
 * Subclasses must implement {@link #getPrivateService()} used to get the service.
 * 
 * See the subclasses for the different variants.
 * 
 * @author akiraly
 */
public abstract class AbstractPrivatePage extends WebPage
{
	private static final long serialVersionUID = 651924848676007898L;

	/**
	 * Constructor.
	 */
	public AbstractPrivatePage()
	{
		add(new Label("message", new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = -4674815203779218869L;

			@Override
			public String getObject()
			{
				return getPrivateService().getMessage();
			}
		}));
	}

	/**
	 * @return service instance
	 */
	public abstract PrivateService getPrivateService();
}
