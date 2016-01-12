package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.model.IModel;

public interface IYuiMenuAction
{

	public String LINK_ID = "link";

	IModel<String> getName();

	void onClick();
}
