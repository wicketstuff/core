package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBar;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBarItem;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.action.AjaxLinkAction;

public class MenuBar2Page extends WicketExamplePage
{
	private FeedbackPanel feedback;

	private YuiMenuBar mb;

	public MenuBar2Page()
	{
		initMenuBar();
		add(feedback = new FeedbackPanel("feedback"));
		feedback.setOutputMarkupId(true);
	}

	@SuppressWarnings("serial")
	private void initMenuBar()
	{
		mb = new YuiMenuBar("menuBar")
		{
			@Override
			protected String getOpts()
			{
				Attributes attributes = new Attributes();
				attributes.add(new Attributes("visible", true));
				attributes.add(new Attributes("clicktohide", true));
				attributes.add(new Attributes("autosubmenudisplay", true));
				attributes.add(new Attributes("hidedelay", 5000));
				// attributes.add(new Attributes("lazyload", true));
				attributes.add(new Attributes("effect",
						"{effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25}"));
				return attributes.toString();
			}
		};
		mb.setOutputMarkupId(true);

		YuiMenuBarItem firstMenu = mb.addMenu("First Menu");

		// 1st Menu
		AbstractYuiMenuItem mi = null;
		YuiMenu subMenu = null;

		subMenu = firstMenu.newSubMenu("mb_firstMenu");
		subMenu.addMenuItem(new TestAction("M1 : L1"));
		subMenu.addMenuItem(new TestAction("M1 : L2"));
		mi = subMenu.addMenuItem(new TestAction("M1 : L3"));
		subMenu.addMenuItem(new TestAction("M1 : L4"));
		subMenu.addMenuItem(new TestAction("M1 : L5"));
		subMenu = mi.newSubMenu("subMenu1");
		subMenu.addMenuItem(new TestAction("Label 1"));
		subMenu.addMenuItem(new TestAction("Label 2"));

		// 2nd Menu
		YuiMenuBarItem secondMenu = mb.addMenu(new TestAction("Second Menu"));

		final YuiMenu subMenu2 = secondMenu.newSubMenu("mb_secondMenu");
		subMenu2.setOutputMarkupId(true);
		subMenu2.addMenuItem(new TestAction("M2 : L1"));

		final YuiMenuItem m2L2 = new YuiMenuItem(new TestAction("M2 : L2"));
		subMenu2.addMenuItem(m2L2);

		subMenu2.addMenuItem(new AjaxLinkAction("M2 : L3 (Ajax) - toggles M2 : L2")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MenuBar2Page.this.info(getName().getObject());
				m2L2.setDisabled(!m2L2.isDisabled());
				m2L2.setChecked(!m2L2.isDisabled());
				m2L2.setSelected(m2L2.isChecked());
				target.addComponent(feedback);
				target.addComponent(mb);
			}
		});
		subMenu2.addMenuItem(new TestAction("M2 : L4"));
		subMenu2.addMenuItem(new TestAction("M2 : L5"));

		// 3rd Menu
		mb.addMenu(new TestAction("Third Menu"));
		add(mb);

	}

	@SuppressWarnings("serial")
	private class TestAction implements IYuiMenuAction, java.io.Serializable
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
			MenuBar2Page.this.info("Link: " + id);
		}
	}

}
