package org.wicketstuff.yui.markup.html.menu2.contextMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Menu extends AbstractMenuItem {

	private List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();

	private Integer x;
	private Integer y;
	private boolean fixedCenter;
	private String width;
	private String height;
	private Integer zIndex;
	private Integer showDelay;
	private Integer hideDelay;
	private Integer subMenuHideDelay;
	private Integer maxHeight;
	private String className;
	private boolean clickToHide = true;
	private boolean disabled;
	
	public Menu( String id ) {
		this( id, id );
	}

	public Menu(String id, String text ) {
		super(id, text);
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public boolean isFixedCenter() {
		return fixedCenter;
	}

	public void setFixedCenter(boolean fixedCenter) {
		this.fixedCenter = fixedCenter;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Integer getZIndex() {
		return zIndex;
	}

	public void setZIndex(Integer index) {
		zIndex = index;
	}

	public Integer getShowDelay() {
		return showDelay;
	}

	public void setShowDelay(Integer showDelay) {
		this.showDelay = showDelay;
	}

	public Integer getHideDelay() {
		return hideDelay;
	}

	public void setHideDelay(Integer hideDelay) {
		this.hideDelay = hideDelay;
	}

	public Integer getSubMenuHideDelay() {
		return subMenuHideDelay;
	}

	public void setSubMenuHideDelay(Integer subMenuHideDelay) {
		this.subMenuHideDelay = subMenuHideDelay;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isClickToHide() {
		return clickToHide;
	}

	public void setClickToHide(boolean clickToHide) {
		this.clickToHide = clickToHide;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Menu newMenu(String menuId) {
		Menu menu = new Menu(menuId);
		add(menu);
		return menu;
	}

	public Menu newMenuItem(String menuId) {
		Menu menu = new Menu(menuId);
		add(menu);
		return menu;
	}

	/**
	 * Add an item to the menu
	 * 
	 * @param child
	 *            menu Item
	 */
	protected void add(AbstractMenuItem child) {
		menuItems.add(child);
		child.parent = this;
	}

	public Iterator<AbstractMenuItem> menuItems() {
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

	public HashMap<String, String> getProperties() {
		HashMap<String, String> props = new HashMap<String, String>();

		props.put("text", getText());

		if (x != null) {
			props.put("x", x.toString());
		}

		if (y != null) {
			props.put("y", y.toString());
		}


		props.put("fixedcenter", String.valueOf(fixedCenter));


		if (width != null) {
			props.put("width", width);
		}

		if (height != null) {
			props.put("height", height);
		}

		if (zIndex != null) {
			props.put("zIndex", zIndex.toString());
		}

		if (showDelay != null) {
			props.put("showdelay", showDelay.toString());
		}

		if (hideDelay != null) {
			props.put("hidedelay", hideDelay.toString());
		}

		if (subMenuHideDelay != null) {
			props.put("subMenuHideDelay", subMenuHideDelay.toString());
		}

		if (maxHeight != null) {
			props.put("maxheight", maxHeight.toString());
		}

		if (className != null) {
			props.put("classname", className);
		}

		if (clickToHide) {
			props.put("clicktohide", String.valueOf(clickToHide));
		}

		if (disabled) {
			props.put("disabled", String.valueOf(disabled));
		}

		return props;
	}

	public String getItemData(YuiContextMenuBehavior behavior) {
		StringBuffer buf = new StringBuffer();

		buf.append("{");
		HashMap<String, String> props = getProperties();
		Iterator<String> keys = props.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			buf.append(key).append(": ");
			buf.append("\"").append(props.get(key)).append("\"");
			if (keys.hasNext()) {
				buf.append(",\n");
			}
			else if ( menuItems.size() > 0 ) {
				buf.append( ",\n" );
			}

		}

		if (menuItems.size() > 0) {
			buf.append("submenu: {");
			buf.append("id: \"").append(getMenuId()).append("\",\n");
			buf.append("itemdata: [");
			Iterator<AbstractMenuItem> miIter = menuItems.iterator();
			while (miIter.hasNext()) {
				AbstractMenuItem mi = miIter.next();
				buf.append(mi.getItemData(behavior));
				if ( miIter.hasNext() ) {
					buf.append( ",\n" );
				}

			}

			buf.append("] }");

		}
		buf.append("}\n");

		return buf.toString();
	}

}
