package org.wicketstuff.theme;

import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public abstract class WicketThemeBasePage extends WebPage
{
	public WicketThemeBasePage()
	{
		List<HeaderContributor> headerContributors = getThemeFactory().getHeaderContributors();
		for (HeaderContributor hc : headerContributors) add(hc);
		
		add(new Label("title",getTitleModel()));
	}
	
	protected abstract IModel<String> getTitleModel(); 
	
	protected abstract IThemeFactory getThemeFactory();
}
