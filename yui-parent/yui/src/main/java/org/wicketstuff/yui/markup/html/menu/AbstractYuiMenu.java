package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public abstract class AbstractYuiMenu extends Panel
{

	private WebMarkupContainer menucontainer;

	public AbstractYuiMenu(String id)
	{
		super(id);

		add(YuiHeaderContributor.forModule("menu"));

		add(getMenuContainer());
	}

	protected WebMarkupContainer getMenuContainer()
	{
		if (null == menucontainer) {
			menucontainer = new WebMarkupContainer("menucontainer")
			{
				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					String id = getMenuElementId();
					if (!Strings.isEmpty(id)) {
						tag.put("id", getMenuElementId());
					}
					tag.put("class", getMenuClass());
				}
			};
		}
		return menucontainer;
	}

	protected abstract String getMenuClass();

	protected abstract String getMenuElementId();

	protected abstract String getMenuType();

	protected abstract String getMenuName();

}
