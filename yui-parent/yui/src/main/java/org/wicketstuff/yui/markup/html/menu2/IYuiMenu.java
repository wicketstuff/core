package org.wicketstuff.yui.markup.html.menu2;

/**
 * YuiMenu in Javascript is the superclass of "MenuBar" and "ContextMenu" since
 * AbstractYuiMenu is not the parent of YuiMenu and YuiMenuBar, IYuiMenu acts as
 * the parent which really just needs the YuiMenuId which is the variable that
 * holds the "Menu" object.
 * 
 * This is used to access directly the Menu JS object for "configuration"
 * 
 * @author josh
 * 
 */
public interface IYuiMenu
{

	String getYuiMenuId();

}
