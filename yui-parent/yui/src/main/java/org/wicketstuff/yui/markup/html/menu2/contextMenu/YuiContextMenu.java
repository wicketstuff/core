package org.wicketstuff.yui.markup.html.menu2.contextMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class YuiContextMenu implements Serializable {

	private List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();
	private String menuId;

	public YuiContextMenu(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void add(AbstractMenuItem item) {
		menuItems.add(item);
		item.contextMenu = this;
	}

	public Iterator<AbstractMenuItem> items() {
		return menuItems.iterator();
	}

	/**
	 * Return the {@link MenuItem} according to its action
	 * 
	 * @param action
	 *            action of a {@link MenuItem}
	 * @return the {@link MenuItem} according to its action
	 */
	public MenuItem getMenuItem(String action) {
		MenuItem ret = null;

		// depth first
		for (AbstractMenuItem item : menuItems) {
			if (item instanceof Menu) {
				Menu menu = (Menu) item;
				ret = menu.getMenuItem(action);
				if (ret != null) {
					break;
				}
			} else if (item instanceof MenuItem
					&& item.getMenuId().equals(action)) {
				ret = (MenuItem) item;
				break;
			}
		}

		return ret;
	}
	
	public List<MenuItem> getAllMenuItems() {
		List<MenuItem> items = new ArrayList<MenuItem>();
		
		for ( AbstractMenuItem item : menuItems ) {
			if (item instanceof Menu) {
				Menu menu = (Menu) item;
				items.addAll( menu.getAllMenuItems() );
			} else if (item instanceof MenuItem) {
				items.add((MenuItem)item );
			}
		}
		
		return items;
	}
	
	public HashMap<String,String> getProperties() {
		HashMap<String,String> props = new HashMap<String,String>();
		
		return props;
	}
	
	public String formatMenuItemData(String trigger, YuiContextMenuBehavior behavior ) {
		
		StringBuffer buf = new StringBuffer();
		
		buf.append( "{" );
		
		buf.append( "trigger: " ).append( trigger ).append( ",\n" );
		buf.append( "itemdata:").append( getItemData(behavior) ).append( "\n" );
		
		HashMap<String,String> props = getProperties();
		Iterator<String> keys = props.keySet().iterator();
		while ( keys.hasNext() ) {
			String key = keys.next();
			buf.append( ",");
			buf.append( key ).append( ": " );
			buf.append( "\"").append( props.get(key)).append( "\"");
			if ( keys.hasNext() ) {
				buf.append("\n");
			}
		}
		
		buf.append( "}\n" );
		
		return buf.toString();
	}
	
	public String getItemData(YuiContextMenuBehavior behavior) {
		StringBuffer buf = new StringBuffer();

		buf.append("[ ");
		Iterator<AbstractMenuItem> miIter = menuItems.iterator();
		while (miIter.hasNext()) {
			AbstractMenuItem mi = miIter.next();
			buf.append(mi.getItemData(behavior));
			
			if ( miIter.hasNext() ) {
				buf.append( ",\n" );
			}
		}

		buf.append("]\n");

		return buf.toString();
	}
	
	public static void main( String[] args ) {
		YuiContextMenu contextMenu = new YuiContextMenu( "testMenu" );
		
		contextMenu.add( new MenuItem( "Cut"));
		contextMenu.add( new MenuItem( "Copy"));
		contextMenu.add( new MenuItem( "Paste" ));
		Menu menu = new Menu( "colors" );
		menu.add( new MenuItem( "Red" ));
		menu.add( new MenuItem( "Green" ));
		menu.add( new MenuItem( "Blue" ));
		contextMenu.add( menu );
		
		System.out.println( contextMenu.formatMenuItemData( "testTrigger", null ));
	}
}
