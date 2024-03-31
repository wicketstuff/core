package org.wicketstuff.urlfragment.example.asyncpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.IRequestParameters;
import org.wicketstuff.urlfragment.AsyncUrlFragmentAwarePage;

public class AsyncHomePage extends AsyncUrlFragmentAwarePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		WebMarkupContainer emptyContentPanel = new WebMarkupContainer("content");
		emptyContentPanel.setOutputMarkupId(true);
		add(emptyContentPanel);
	}

	@Override
	protected void onParameterArrival(IRequestParameters requestParameters, AjaxRequestTarget target)
	{
		ContentPanel content = new ContentPanel("content", requestParameters);
		content.setOutputMarkupId(true);
		AsyncHomePage.this.replace(content);
		target.add(content);
	}
}
