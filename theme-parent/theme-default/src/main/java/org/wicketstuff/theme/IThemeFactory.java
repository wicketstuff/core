package org.wicketstuff.theme;

import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;

public interface IThemeFactory
{
	public List<HeaderContributor> getHeaderContributors();
}
