package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBar;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBarItem;


public class MenuBar2Page extends WicketExamplePage {

	public MenuBar2Page() {

		initMenuBar();
		
		add(new FeedbackPanel("feedback"));
	}
	
	private void initMenuBar() {
		AbstractYuiMenuItem mi = null;
		YuiMenu subMenu = null;
		
		YuiMenuBar mb = new YuiMenuBar( "menuBar", "menuBar");
		
		YuiMenuBarItem firstMenu = mb.addMenu( "First Menu" );


		subMenu = firstMenu.newSubMenu( "mb_firstMenu");
		
		subMenu.addMenuItem(new TestAction("M1 : L1"));
		subMenu.addMenuItem(new TestAction("M1 : L2"));
		mi = subMenu.addMenuItem(new TestAction("M1 : L3"));
		subMenu.addMenuItem(new TestAction("M1 : L4"));
		subMenu.addMenuItem(new TestAction("M1 : L5"));
		
		subMenu = mi.newSubMenu( "subMenu1" );
		subMenu.addMenuItem(new TestAction("Label 1"));
		subMenu.addMenuItem(new TestAction("Label 2"));
		
		YuiMenuBarItem secondMenu = mb.addMenu( "Second Menu");
		
		subMenu = secondMenu.newSubMenu( "mb_secondMenu");
		subMenu.addMenuItem(new TestAction("M2 : L1"));
		subMenu.addMenuItem(new TestAction("M2 : L2"));
		subMenu.addMenuItem(new TestAction("M2 : L3"));
		subMenu.addMenuItem(new TestAction("M2 : L4"));
		subMenu.addMenuItem(new TestAction("M2 : L5"));

		
		YuiMenuBarItem thirdMenu = mb.addMenu( new TestAction( "Third Menu" ) );

		
		add( mb );
	}
	

	private static class TestAction implements IYuiMenuAction, java.io.Serializable {
		private String id;

		public TestAction(String id) {
			this.id = id;
		}
		
		public IModel getName() {
			return new Model( id );
		}

		public void onClick() {
			System.out.println("Link: " + id);
		}
	}

}
