package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuGroup;


public class GroupMenu2Page extends WicketExamplePage {

	public GroupMenu2Page() {
		
		initMenuGroup1();
		initMenuGroup2();
		
		add(new FeedbackPanel("feedback"));

	}
	

	private void initMenuGroup1() {
		AbstractYuiMenuItem mi = null;
		
		YuiMenuGroup menuGroup = new YuiMenuGroup( "groupMenu1", "groupMenu1" );
		YuiMenu menu1 = menuGroup.addMenu();


		menu1.addMenuItem(new TestAction("M1 : L1"));
		menu1.addMenuItem(new TestAction("M1 : L2"));
		mi = menu1.addMenuItem(new TestAction("M1 : L3"));
		menu1.addMenuItem(new TestAction("M1 : L4"));
		menu1.addMenuItem(new TestAction("M1 : L5"));
		
		YuiMenu subMenu = mi.newSubMenu( "gm_subMenu1" );
		subMenu.addMenuItem(new TestAction("Label 1"));
		subMenu.addMenuItem(new TestAction("Label 2"));
		
		YuiMenu menu2 = menuGroup.addMenu();
		menu2.addMenuItem(new TestAction("M2 : L1"));
		menu2.addMenuItem(new TestAction("M2 : L2"));
		menu2.addMenuItem(new TestAction("M2 : L3"));
		menu2.addMenuItem(new TestAction("M2 : L4"));
		menu2.addMenuItem(new TestAction("M2 : L5"));

		add(menuGroup);
	}
	
	private void initMenuGroup2() {
		AbstractYuiMenuItem mi = null;
		
		YuiMenuGroup menuGroup = new YuiMenuGroup( "groupMenu2", "groupMenu2" );
		YuiMenu menu1 = menuGroup.addMenu("Group 1");


		menu1.addMenuItem(new TestAction("M1 : L1"));
		menu1.addMenuItem(new TestAction("M1 : L2"));
		mi = menu1.addMenuItem(new TestAction("M1 : L3"));
		menu1.addMenuItem(new TestAction("M1 : L4"));
		menu1.addMenuItem(new TestAction("M1 : L5"));
		
		YuiMenu subMenu = mi.newSubMenu( "gm_subMenu2" );
		subMenu.addMenuItem(new TestAction("Label 1"));
		subMenu.addMenuItem(new TestAction("Label 2"));
		
		YuiMenu menu2 = menuGroup.addMenu("Group 2");
		menu2.addMenuItem(new TestAction("M2 : L1"));
		menu2.addMenuItem(new TestAction("M2 : L2"));
		menu2.addMenuItem(new TestAction("M2 : L3"));
		menu2.addMenuItem(new TestAction("M2 : L4"));
		menu2.addMenuItem(new TestAction("M2 : L5"));

		add(menuGroup);
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
