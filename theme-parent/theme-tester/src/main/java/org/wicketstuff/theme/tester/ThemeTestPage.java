package org.wicketstuff.theme.tester;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.theme.*;
import org.wicketstuff.theme.standard.ThemeFactory;

public class ThemeTestPage extends WicketThemeBasePage
{
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
