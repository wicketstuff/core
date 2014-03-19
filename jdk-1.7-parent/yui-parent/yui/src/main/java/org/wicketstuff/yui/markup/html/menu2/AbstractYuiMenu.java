package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;

@SuppressWarnings("serial")
public abstract class AbstractYuiMenu extends Panel implements IYuiMenu
{
	private WebMarkupContainer menucontainer;

	private String menuId;

	private static final String MODULE_NAME = "wicket_yui_menu";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(YuiMenuBar.class,
			"res/YuiMenu.js");

	private static final String[] MODULE_REQUIRES = { "menu" };

	public AbstractYuiMenu(String wicketId, final String menuId, final boolean addInit)
	{
		super(wicketId);

		this.menuId = menuId;

		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{

			@Override
			public String onSuccessJS()
			{
				StringBuffer buffer = new StringBuffer();
				if (addInit)
				{
					buffer.append("var ").append(getMenuVar()).append(" = new YAHOO.widget.Menu(")
							.append("\"").append(getYuiMenuId()).append("\",").append(getOpts())
							.append(");");
					buffer.append(getMenuVar()).append(".render();");
					buffer.append(getMenuVar()).append(".show();");
				}
				return buffer.toString();
			}

			private Object getMenuVar()
			{
				return "var_" + getYuiMenuId();
			}

		}));

		menucontainer = getMenuContainer();
		if (menucontainer != null)
		{
			add(menucontainer);
		}
	}

	public String getYuiMenuId()
	{
		return menuId;
	}

	protected WebMarkupContainer getMenuContainer()
	{
		if (menucontainer == null)
		{
			menucontainer = new WebMarkupContainer("menucontainer")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);

					if (!Strings.isEmpty(menuId))
					{
						tag.put("id", menuId);
					}
					tag.put("class", getMenuClass());
				}
			};

		}
		return menucontainer;
	}

	protected abstract String getMenuClass();

	protected abstract String getMenuName();

	/**
	 * Override this for your own config
	 * @return
	 */
	protected String getOpts()
	{
		Attributes attributes = new Attributes();
		attributes.add(new Attributes("visible", true));
		attributes.add(new Attributes("clicktohide", false));
		return attributes.toString();
	}
}
