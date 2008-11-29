package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class YuiMenuItem extends AbstractYuiMenuItem
{
    
    static final Logger log = LoggerFactory.getLogger(YuiMenuItem.class);

    public static final String MENU_ITEM_ID = "menuItem";
    
    protected String text;
    
    public YuiMenuItem(final String label) {
        super (MENU_ITEM_ID, label);
        

    }
    
    @Override
    public String getMenuClass()
    {
        return "yuimenuitem";
    }

    
}
