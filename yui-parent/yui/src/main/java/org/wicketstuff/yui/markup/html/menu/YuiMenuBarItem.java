package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;

public abstract class YuiMenuBarItem extends AbstractYuiMenuItem
{

    public static final String MENU_BAR_ITEM_ID = "menuBarItem";
    
    public YuiMenuBarItem(String text)
    {
        super(MENU_BAR_ITEM_ID, text);
    }
    
    

    @Override
    public String getMenuClass()
    {
        return "yuimenubaritem";
    }

}
