package org.wicketstuff.theme.standard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.wicketstuff.theme.IThemeFactory;

public class ThemeFactory implements IThemeFactory
{
	public List<HeaderContributor> getHeaderContributors()
	{
		return Arrays.asList(CSSPackageResource.getHeaderContribution(ThemeFactory.class, "standard.css"));
	}
}
