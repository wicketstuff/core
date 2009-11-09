package org.wicketstuff.theme.standard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.theme.IThemeFactory;

public class ThemeFactory implements IThemeFactory
{
	public List<HeaderContributor> getHeaderContributors()
	{
		return new ArrayList<HeaderContributor>();
	}
}
