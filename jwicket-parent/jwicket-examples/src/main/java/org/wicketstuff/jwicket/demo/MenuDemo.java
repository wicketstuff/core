package org.wicketstuff.jwicket.demo;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.Model;
import org.wicketstuff.menu.Menu;
import org.wicketstuff.menu.MenuBarPanel;
import org.wicketstuff.menu.MenuItem;


public class MenuDemo extends MenuBarPanel{

	private static final long serialVersionUID = 1L;


	public MenuDemo(final String id) {
		super(id, new ArrayList<Menu>());
		
		
		MenuItem item1_1 = new MenuItem(new Model<String>("Apache Wicket"), "http://www.wicketframework.org");
		MenuItem item1_2 = new MenuItem(new Model<String>("Apache Wicketstuff"), "http://www.wicketstuff.org");
		List<MenuItem> itemsForMenu1 = new ArrayList<MenuItem>();
		itemsForMenu1.add(item1_1);
		itemsForMenu1.add(item1_2);
		
		
		Menu menu1 = new Menu(new Model<String>("External"), itemsForMenu1);
		menus.add(menu1);

		Menu menu2 = new Menu(new Model<String>("Menu 2"), itemsForMenu1);
		menus.add(menu2);
		
	}
}
