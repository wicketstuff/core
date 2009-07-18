package org.wicketstuff.yui.markup.html.menu2;

import java.util.ArrayList;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;


@SuppressWarnings( { "serial", "unchecked" })
public class YuiMenuBar extends Panel implements IYuiMenu
{
	private static final long serialVersionUID = 1L;

	public static final String MENU_BAR_ID = "menuBar";

	public static final String MENU_ITEMS_ID = "menuItems";

	private static final String MODULE_NAME = "wicket_yui_menubar";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(YuiMenuBar.class,
			"res/YuiMenu.js");

	private static final String[] MODULE_REQUIRES = { "menu", "animation" };

	private ListView list;

	WebMarkupContainer mg;

	public YuiMenuBar(String wicketId)
	{
		super(wicketId);

		// Add a container around the menu
		final WebMarkupContainer yuiMenuBar;
		add(yuiMenuBar = new WebMarkupContainer("skin_container"));
		yuiMenuBar.add(new AttributeModifier("class", true, new Model<String>(getCssClass())));
		yuiMenuBar.setOutputMarkupId(true);

		// Add Module definition
		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{

			@Override
			public String onSuccessJS()
			{
				StringBuffer buffer = new StringBuffer();

				buffer.append("var menu = YAHOO.widget.MenuManager.getMenu('" + getYuiMenuId()
						+ "');");

				buffer.append("if (menu) { menu.destroy(); } ");

				buffer.append("var ").append(getMenuBarVar())
						.append(" = new YAHOO.widget.MenuBar(").append("\"").append(getYuiMenuId())
						.append("\",").append(getOpts()).append(");");

				buffer.append(getMenuBarVar()).append(".render();");

				return buffer.toString();
			}

			private Object getMenuBarVar()
			{
				return "var_" + getYuiMenuId();
			}

		}));

		mg = new WebMarkupContainer(MENU_BAR_ID);
		mg.setOutputMarkupId(true);
		mg.add(new AttributeModifier("class", true, new Model<String>("yuimenubar yuimenubarnav")));
		yuiMenuBar.add(mg);

		list = new ListView(MENU_ITEMS_ID, new ArrayList<YuiMenuBarItem>())
		{

			@Override
			protected void populateItem(ListItem item)
			{
				item.setRenderBodyOnly(true);
				YuiMenuBarItem mi = (YuiMenuBarItem)item.getModelObject();

				if (0 == item.getIndex())
				{
					mi.addFirstOfType();
				}
				mi.setRenderBodyOnly(true);
				item.add(mi);

			}
		}.setReuseItems(true);
		mg.add(list);
	}

	public YuiMenuBarItem addMenu(String label)
	{
		YuiMenuBarItem item = new YuiMenuBarItem(label);
		addMenuItem(item);
		return item;
	}

	public YuiMenuBarItem addMenu(IYuiMenuAction action)
	{
		YuiMenuBarItem item = new YuiMenuBarItem(action);
		addMenuItem(item);
		return item;
	}

	private void addMenuItem(YuiMenuBarItem menuItem)
	{
		ArrayList<YuiMenuBarItem> newList = new ArrayList<YuiMenuBarItem>();
		newList.addAll(list.getList());
		newList.add(menuItem);
		list.setList(newList);
	}

	public AbstractYuiMenuItem getMenuItem(int idx)
	{
		ListItem item = (ListItem)list.getList().get(idx);
		return item == null ? null : (YuiMenuBarItem)item.getModelObject();
	}

	protected String getOpts()
	{
		Attributes attributes = new Attributes();
		attributes.add(new Attributes("visible", true));
		attributes.add(new Attributes("clicktohide", false));
		attributes.add(new Attributes("autosubmenudisplay", true));
		attributes.add(new Attributes("hidedelay", 5000));
		attributes.add(new Attributes("lazyload", true));
		return attributes.toString();
	}

	protected String getCssClass()
	{
		return "yui-skin-sam";
	}

	public String getYuiMenuId()
	{
		return mg.getMarkupId();
	}
}
