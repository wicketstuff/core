package org.wicketstuff.yui.markup.html.menu2;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

@SuppressWarnings( { "unchecked", "serial" })
public class YuiMenu extends AbstractYuiMenu
{

	private static final String MENU_ID = "menu";

	private static final String MENU_ITEMS_ID = "menuItems";

	private List<AbstractYuiMenuItem> items = new ArrayList<AbstractYuiMenuItem>();

	private ListView list;

	private WebMarkupContainer menu;

	private boolean firstOfType = false;

	public YuiMenu(String elementId)
	{
		this(elementId, true, true);
	}

	YuiMenu(String elementId, final boolean firstMenu, boolean addInit)
	{
		super(MENU_ID, elementId, addInit);

		menu = new WebMarkupContainer(MENU_ID);

		if (firstMenu)
		{
			addFirstOfType();
		}

		WebMarkupContainer menuContainer = getMenuContainer();
		setRenderBodyOnly(true);

		menuContainer = (menuContainer == null) ? this : menuContainer;

		menuContainer.add(menu);

		list = new ListView(MENU_ITEMS_ID, items)
		{

			@Override
			protected void populateItem(ListItem item)
			{
				item.setRenderBodyOnly(true);
				YuiMenuItem mi = (YuiMenuItem)item.getModelObject();
				mi.setIndex(item.getIndex());
				mi.setRenderBodyOnly(true);
				item.add(mi);
			}

		}.setReuseItems(false);
		menu.add(list);
	}

	void addFirstOfType()
	{
		if (firstOfType == false)
		{
			menu.add(new AttributeAppender("class", true, new Model("first-of-type"), " "));
			firstOfType = true;
		}
	}

	public AbstractYuiMenuItem addMenuItem(String label, AbstractLink link)
	{
		YuiMenuItem item = new YuiMenuItem(label, link);
		addMenuItem(item);
		return item;
	}

	public AbstractYuiMenuItem addMenuItem(IYuiMenuAction action)
	{
		YuiMenuItem item = new YuiMenuItem(action);
		addMenuItem(item);
		return item;
	}

	public void addMenuItem(AbstractYuiMenuItem menuItem)
	{
		items.add(menuItem);
		list.setList(items);
	}

	public AbstractYuiMenuItem getMenuItem(int idx)
	{
		ListItem item = (ListItem)list.getList().get(idx);
		return item == null ? null : (YuiMenuItem)item.getModelObject();
	}

	@Override
	protected String getMenuClass()
	{
		return "yuimenu";
	}

	@Override
	public String getMenuName()
	{
		return "yuiMenu" + getYuiMenuId();
	}

	public void removeMenuItem(int idx)
	{
		AbstractYuiMenuItem item = items.get(idx);
		items.remove(item);
		list.setList(items);
	}

	public String getYuiMenuId()
	{
		return getMarkupId();
	}
}
