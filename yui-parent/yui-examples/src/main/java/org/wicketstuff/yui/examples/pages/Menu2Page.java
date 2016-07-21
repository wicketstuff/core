package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.behavior.animation.YuiEffect;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;


public class Menu2Page extends WicketExamplePage
{

	public Menu2Page()
	{

		initMenu();

		add(new FeedbackPanel("feedback"));

	}

	private void initMenu()
	{
		AbstractYuiMenuItem mi = null;

		YuiMenu menu = new YuiMenu("menu")
		{
			@Override
			protected String getOpts()
			{
				Attributes attributes = new Attributes();
				attributes.add(new Attributes("visible", true));
				attributes.add(new Attributes("clicktohide", false));
				attributes.add(new Attributes("fixedcenter", true));
				return attributes.toString();
			}
		};


		menu.addMenuItem(new TestAction("M : L1"));
		menu.addMenuItem(new TestAction("M : L2"));
		mi = menu.addMenuItem(new TestAction("M : L3"));
		menu.addMenuItem(new TestAction("M : L4"));
		menu.addMenuItem(new TestAction("M : L5"));

		YuiMenu subMenu = mi.newSubMenu("m_subMenu1");
		subMenu.addMenuItem(new TestAction("Label 1"));
		subMenu.addMenuItem(new TestAction("Label 2"));


		add(menu);
	}

	private static class TestAction implements IYuiMenuAction, java.io.Serializable
	{
		private String id;

		public TestAction(String id)
		{
			this.id = id;
		}

		public IModel getName()
		{
			return new Model(id);
		}

		public void onClick()
		{
			System.out.println("Link: " + id);
		}
	}

}
