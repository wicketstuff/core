package org.wicketstuff.gae;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class AnotherPage extends WebPage
{

	public AnotherPage(PageParameters pageParameters)
	{

		add(new Link<Void>("noop")
		{

			@Override
			public void onClick()
			{
				System.err.println("In page 2");
			}
		});
	}

}
