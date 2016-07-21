package org.wicketstuff.yui.markup.html.menu2.contextMenu;


import java.io.Serializable;

public abstract class AbstractMenuItem implements Serializable{


	private String menuId;
	
	private String text;

	private boolean disabled;
	
	YuiContextMenu contextMenu;
	Menu parent;
	
	public String getPathToRoot() {
		if ( contextMenu != null ) {
			return contextMenu.getMenuId() + "_" + getMenuId();
		}
		else if ( parent != null ) {
			return parent.getPathToRoot() + "_" + getMenuId();
		}
		else {
			return getMenuId();
		}
	}


	
	protected AbstractMenuItem(String id) {
		this.menuId = id;		
	}

	protected AbstractMenuItem(String id, String text) {
		this( id );
		this.text = text;
	}
	
	protected String getMenuId() {
		return menuId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	abstract public String getItemData(YuiContextMenuBehavior behavior);





		
}
