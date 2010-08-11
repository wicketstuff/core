package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu.CheckedYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu.YuiMenuBar;
import org.wicketstuff.yui.markup.html.menu.YuiMenuBarItem;
import org.wicketstuff.yui.markup.html.menu.YuiMenuGroup;
import org.wicketstuff.yui.markup.html.menu.YuiMenuGroupListModel;
import org.wicketstuff.yui.markup.html.menu.YuiMenuItem;
import org.wicketstuff.yui.markup.html.menu.YuiMenuItemListModel;
import org.wicketstuff.yui.markup.html.menu.YuiSubMenu;

public class MenuPage extends WicketExamplePage
{
	public MenuPage()
	{
		add(new YuiMenuBar("menuBar", new YuiMenuItemListModel()
		{

			@Override
			protected List<AbstractYuiMenuItem> getMenuItems()
			{
				return MenuPage.this.getMenuBarItems();
			}

		})
		{

			@Override
			protected String getMenuElementId()
			{
				return "testMenuBar";
			}

		});

		add(new FeedbackPanel("feedback"));
	}

	private List<AbstractYuiMenuItem> getMenuBarItems()
	{
		List<AbstractYuiMenuItem> menuBarItems = new ArrayList<AbstractYuiMenuItem>();
		menuBarItems.add(new YuiMenuBarItem("MenuBarItem#1")
		{

			@Override
			public AbstractLink getLink(String menuItemLinkId)
			{
				return new Link(menuItemLinkId)
				{

					@Override
					public void onClick()
					{

					}

				};
			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				YuiMenuGroupListModel ymglm = new YuiMenuGroupListModel()
				{

					@Override
					protected List<YuiMenuGroup> getMenuGroupList()
					{
						return MenuPage.this.getMenuGroupA();
					}

				};

				return new YuiSubMenu(menuItemSubMenuId, ymglm)
				{
					@Override
					protected String getMenuElementId()
					{
						return "submenuA";
					}
				};
			}

		});

		menuBarItems.add(new YuiMenuBarItem("MenuBarItem#2")
		{

			@Override
			public AbstractLink getLink(String menuItemLinkId)
			{
				return new Link(menuItemLinkId)
				{

					@Override
					public void onClick()
					{

					}

				};
			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				return new WebMarkupContainer(menuItemSubMenuId)
				{
					@Override
					public boolean isVisible()
					{
						return false;
					}
				};
			}

		});

		return menuBarItems;
	}

	List<YuiMenuGroup> getMenuGroupA()
	{

		YuiMenuItemListModel menuItemListModel = new YuiMenuItemListModel()
		{

			@Override
			protected List<AbstractYuiMenuItem> getMenuItems()
			{
				return MenuPage.this.getMenuItemsForGroupA();
			}

		};

		YuiMenuGroup ymg = new YuiMenuGroup(menuItemListModel)
		{

			@Override
			protected WebComponent getGroupTitle(String id)
			{
				return new WebComponent(id)
				{
					@Override
					public boolean isVisible()
					{
						return false;
					}
				};
			}

		};

		return Collections.singletonList(ymg);
	}

	List<AbstractYuiMenuItem> getMenuItemsForGroupA()
	{
		List<AbstractYuiMenuItem> mil = new ArrayList<AbstractYuiMenuItem>();

		mil.add(new YuiMenuItem("Menu Item #1")
		{

			@Override
			public AbstractLink getLink(String menuItemLinkId)
			{
				return new Link(menuItemLinkId)
				{

					@Override
					public void onClick()
					{

					}

				};
			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				return null;
			}

		});

		mil.add(new YuiMenuItem("Menu Item #2")
		{

			@Override
			public AbstractLink getLink(String menuItemLinkId)
			{
				return new Link(menuItemLinkId)
				{

					@Override
					public void onClick()
					{
						// TODO Auto-generated method stub

					}

				};
			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				YuiMenuGroupListModel ymglm = new YuiMenuGroupListModel()
				{

					@Override
					protected List<YuiMenuGroup> getMenuGroupList()
					{
						return MenuPage.this.getMenuGroupB();
					}

				};

				return new YuiSubMenu(menuItemSubMenuId, ymglm)
				{
					@Override
					protected String getMenuElementId()
					{
						return "submenuB";
					}
				};
			}

		});

		return mil;
	}

	List<YuiMenuGroup> getMenuGroupB()
	{
		YuiMenuItemListModel menuItemListModel = new YuiMenuItemListModel()
		{

			@Override
			protected List<AbstractYuiMenuItem> getMenuItems()
			{
				return MenuPage.this.getMenuItemsForGroupB();
			}

		};

		YuiMenuGroup ymg = new YuiMenuGroup(menuItemListModel)
		{

			@Override
			protected WebComponent getGroupTitle(String id)
			{
				return new WebComponent(id)
				{
					@Override
					public boolean isVisible()
					{
						return false;
					}
				};
			}

		};

		return Collections.singletonList(ymg);
	}

	protected List<AbstractYuiMenuItem> getMenuItemsForGroupB()
	{
		List<AbstractYuiMenuItem> mil = new ArrayList<AbstractYuiMenuItem>();

		mil.add(new YuiMenuItem("Menu Item B1")
		{

			@Override
			public AbstractLink getLink(String menuItemLinkId)
			{
				return new Link(menuItemLinkId)
				{

					@Override
					public void onClick()
					{

					}

				};
			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				return null;
			}

		});

		final YuiMenuItem mi;
		mil.add(mi = new CheckedYuiMenuItem("Menu Item B2")
		{

			@Override
			protected void onCheck(AjaxRequestTarget target)
			{

			}

			@Override
			public MarkupContainer getSubMenu(String menuItemSubMenuId)
			{
				// TODO Auto-generated method stub
				return null;
			}

		});
		mi.setChecked(true);
		return mil;

	}

}
