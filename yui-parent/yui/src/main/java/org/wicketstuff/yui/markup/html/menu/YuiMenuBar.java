package org.wicketstuff.yui.markup.html.menu;

import java.util.Map;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.collections.MiniMap;
import org.apache.wicket.velocity.VelocityHeaderContributor;
import org.apache.wicket.velocity.VelocityJavascriptContributor;

public abstract class YuiMenuBar extends AbstractYuiMenu
{

	public YuiMenuBar(String id, YuiMenuItemListModel model)
	{
		super(id);
		getMenuContainer().add(new ListView("menuBarItems", model)
		{

			@Override
			protected void populateItem(ListItem item)
			{
				item.setRenderBodyOnly(true);
				AbstractYuiMenuItem mi = (AbstractYuiMenuItem) item.getModelObject();
				mi.setIndex(item.getIndex());
				if (0 == item.getIndex()) {
					mi.add(new AttributeAppender("class", true, new Model("first-of-type"), " "));
				}
				item.add(mi);

			}

		});
		add(getMenuInit());

	}

	@Override
	protected String getMenuClass()
	{
		return "yuimenubar";
	}

	@Override
	protected String getMenuType()
	{
		return "MenuBar";
	}

	public String getMenuName()
	{
		return "yuiMenuBar" + getMenuElementId();
	}

	protected IBehavior getMenuInit()
	{
		final Map vars = new MiniMap(3);
		vars.put("menuName", getMenuName());
		vars.put("elementId", getMenuElementId());
		vars.put("menuType", getMenuType());
		return new VelocityHeaderContributor().add(new VelocityJavascriptContributor(YuiMenuBar.class, "res/menuinit.vm", Model.valueOf(vars), getMenuElementId() + "Script"));
	}

}
