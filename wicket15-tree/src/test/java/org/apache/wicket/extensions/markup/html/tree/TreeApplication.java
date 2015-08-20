package org.apache.wicket.extensions.markup.html.tree;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TreeApplication extends WebApplication 
{
	@Override
	public Class<? extends Page> getHomePage() 
	{
		return TreeTablePage.class;
	}
}
