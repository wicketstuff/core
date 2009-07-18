package org.wicketstuff.yui.markup.html.menu2;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;

@SuppressWarnings("serial")
public abstract class AbstractYuiMenuItem extends Panel
{

	private static final String MODULE_NAME = "wicket_yui_menu_item";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(YuiMenuBar.class,
			"res/YuiMenu.js");

	private static final String[] MODULE_REQUIRES = { "menu" };


	public static final String MENU_ITEM_SUBMENU_ID = "menu";

	private boolean checked = false;

	private boolean checkedChanged = false;

	private boolean selected = false;

	private boolean disabled = false;

	private Component subMenu = null;

	private int index = 0;

	private int groupIndex = 0;

	private static final String ITEM_ID = "item";

	private WebMarkupContainer itemContainer = null;

	private YuiMenuPath menuPath;

	public AbstractYuiMenuItem(String id)
	{
		super(id);
		setOutputMarkupId(true);

		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{

			@Override
			public String onSuccessJS()
			{
				StringBuffer bufy = new StringBuffer();

				bufy.append("YAHOO.util.Event.onDOMReady(function(){");

				bufy.append("var ").append(getMenuItemVar()).append(
						" = YAHOO.widget.MenuManager.getMenuItem('"
								+ getItemContainer().getMarkupId() + "'); ");

				bufy.append(" if ( " + getMenuItemVar() + ") { ");
				bufy.append(getMenuItemVar()).append(
						".cfg.setProperty('disabled', " + isDisabled() + "); ");
				bufy.append(getMenuItemVar()).append(
						".cfg.setProperty('checked', " + isChecked() + ");");
				bufy.append(getMenuItemVar()).append(
						".cfg.setProperty('selected', " + isSelected() + ");");

				bufy.append("}});");
				return bufy.toString();
			}

			/**
			 * since this var is local, it doens't have to be unique..
			 * 
			 * @return
			 */
			private String getMenuItemVar()
			{
				return "lv";
			}

		}));

	}

	public YuiMenu newSubMenu(String menuId)
	{
		YuiMenu subMenu = new YuiMenu(menuId, false, false);
		setSubMenu(subMenu);
		return subMenu;
	}

	private void setSubMenu(Component newSubMenu)
	{
		if (subMenu != null)
		{
			getItemContainer().remove(subMenu);
			subMenu = null;
		}

		newSubMenu.setRenderBodyOnly(true);
		getItemContainer().add(newSubMenu);
		subMenu = newSubMenu;
	}

	protected final WebMarkupContainer getItemContainer()
	{
		if (itemContainer == null)
		{
			itemContainer = new WebMarkupContainer(ITEM_ID);
			itemContainer.setOutputMarkupId(true);
			add(itemContainer);
		}
		return itemContainer;
	}

	protected YuiMenuPath getMenuPath()
	{
		if (null == menuPath)
		{

			AbstractYuiMenuItem p = (AbstractYuiMenuItem)findParent(AbstractYuiMenuItem.class);
			if (p != null)
			{
				menuPath = new YuiMenuPath(getIndex(), getGroupIndex(), p.getMenuPath(), null);
			}
			else
			{
				IYuiMenu aym = (IYuiMenu)findParent(IYuiMenu.class);

				if (aym != null)
				{
					menuPath = new YuiMenuPath(getIndex(), getGroupIndex(), null, aym
							.getYuiMenuId());
				}
				else
				{
					menuPath = new YuiMenuPathEmpty(getIndex(), getGroupIndex());
				}
			}
		}

		return menuPath;
	}

	public abstract String getMenuClass();


	public boolean isDisabled()
	{
		return disabled;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
		setCheckedChanged(true);
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	protected boolean isCheckedChanged()
	{
		boolean current = checkedChanged;
		checkedChanged = false;
		return current;
	}

	protected void setCheckedChanged(boolean checkedChanged)
	{
		this.checkedChanged = checkedChanged;
	}

	public void toggleCheckedScript(AjaxRequestTarget target)
	{
		String menuPath = getMenuPath().toPath();
		if (!menuPath.contains("EMPTY PATH"))
		{
			String menuItemCheckScript = menuPath + ".cfg.setProperty(\"checked\", "
					+ AbstractYuiMenuItem.this.isChecked() + ");";
			target.appendJavascript(menuItemCheckScript + "\nalert(menuItemCheckScript);");
		}
	}

	/**
	 * @deprecated - use ids and MenuManager directly instead of calculating the
	 *             path
	 * 
	 * @author josh
	 * 
	 */
	public static class YuiMenuPath implements Serializable
	{
		private static final long serialVersionUID = 1L;
		protected final int index;
		protected final int groupIndex;
		private final YuiMenuPath parent;
		private final String name;

		public YuiMenuPath(int index, int groupIndex, YuiMenuPath parent, String name)
		{
			this.index = index;
			this.groupIndex = groupIndex;
			this.parent = parent;
			this.name = name;
		}


		public String toPath()
		{
			if (parent == null && null != name)
			{
				return name + ".getItem(" + index + ")";
			}
			else
			{
				return parent.toPath() + ".cfg.getProperty(\"submenu\").getItem(" + index + ","
						+ groupIndex + ")";
			}
		}
	}

	public static class YuiMenuPathEmpty extends YuiMenuPath
	{
		private static final long serialVersionUID = 1L;

		public YuiMenuPathEmpty(int index, int groupIndex)
		{
			super(index, groupIndex, null, null);
		}

		@Override
		public String toPath()
		{
			return "Index " + index + " has EMPTY PATH";
		}
	}

	/**
	 * @deprecated - use MenuManager to access directly
	 * 
	 * @author josh
	 * 
	 */
	class CheckedMenuItemBehavior extends AbstractBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void renderHead(IHeaderResponse response)
		{

			if (AbstractYuiMenuItem.this.isCheckedChanged())
			{
				String menuPath = AbstractYuiMenuItem.this.getMenuPath().toPath();

				if (!menuPath.contains("EMPTY PATH"))
				{
					String menuItemCheckScript = menuPath + ".cfg.setProperty(\"checked\", "
							+ AbstractYuiMenuItem.this.isChecked() + ");";
					response.renderOnLoadJavascript(menuItemCheckScript);
				}
			}
		}
	}

	public int getGroupIndex()
	{
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex)
	{
		this.groupIndex = groupIndex;
	}
}
