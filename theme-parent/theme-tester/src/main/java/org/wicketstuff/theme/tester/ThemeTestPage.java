package org.wicketstuff.theme.tester;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.theme.IThemeFactory;
import org.wicketstuff.theme.WicketThemeBasePage;
import org.wicketstuff.theme.standard.ThemeFactory;

public class ThemeTestPage extends WicketThemeBasePage
{
	public ThemeTestPage()
	{
		add(new FeedbackPanel("feedback"));
		
		for (int i=0;i<2;i++)
		{
			info("Info "+i);
			warn("Warn "+i);
			error("Error "+i);
		}
		
	}
	
	@Override
	protected IModel<String> getTitleModel()
	{
		return Model.of("Theme Test");
	}
	
	@Override
	protected IThemeFactory getThemeFactory()
	{
		return new ThemeFactory();
	}
}
