package org.wicketstuff.yui.markup.html.menu2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.collections.MiniMap;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.velocity.VelocityHeaderContributor;
import org.apache.wicket.velocity.VelocityJavascriptContributor;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * TODO : clean up this to use YuiLoader 
 * @author josh
 *
 */
public class YuiMenuGroup extends Panel
{
	private static final long serialVersionUID = 1L;

	public static final String MENU_GROUP_ID = "menuGroup";


	private List<YuiMenu> menus = new ArrayList<YuiMenu>();

	private ListView list;

	public YuiMenuGroup(final String elementId)
	{
		this("menu", elementId);
	}

	public YuiMenuGroup(String wicketId, final String elementId)
	{
		super(wicketId);

		add(YuiHeaderContributor.forModule("menu", null, false, "2.5.2"));
		setRenderBodyOnly(true);
		WebMarkupContainer mg = new WebMarkupContainer(MENU_GROUP_ID)
		{
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);

				if (!Strings.isEmpty(elementId))
				{
					tag.put("id", elementId);
				}
				tag.put("class", "yuimenu");
			}
		};

		add(mg);

		list = new ListView("menuItems", menus)
		{

			@Override
			protected void populateItem(ListItem item)
			{
				item.setRenderBodyOnly(true);
				YuiMenuGroupMenu menu = (YuiMenuGroupMenu)item.getModelObject();

				if (0 == item.getIndex())
				{
					menu.addFirstOfType();
				}
				menu.setRenderBodyOnly(true);
				item.add(menu);
			}

		}.setReuseItems(true);

		mg.add(list);
		add(getMenuInit(elementId));
	}

	public YuiMenu addMenu(IModel label)
	{
		YuiMenuGroupMenu subMenu = new YuiMenuGroupMenu(label, menus.size() == 0, false);
		menus.add(subMenu);
		list.setList(menus);

		return subMenu;
	}

	public YuiMenu addMenu(String label)
	{
		return addMenu(new Model(label));
	}

	public YuiMenu addMenu()
	{
		YuiMenuGroupMenu subMenu = new YuiMenuGroupMenu(false, false);
		menus.add(subMenu);
		list.setList(menus);

		return subMenu;
	}

	private IBehavior getMenuInit(String elementId)
	{
		final Map<String, String> vars = new MiniMap(2);
		vars.put("menuName", "Menu");
		vars.put("elementId", elementId);
		return new VelocityHeaderContributor().add(new VelocityJavascriptContributor(
				YuiMenuGroup.class, "res/menuinit.vm", Model.valueOf(vars), elementId + "Script"));
	}
}
