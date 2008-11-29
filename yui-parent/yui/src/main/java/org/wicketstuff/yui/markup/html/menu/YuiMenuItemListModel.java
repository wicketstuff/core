package org.wicketstuff.yui.markup.html.menu;

import java.util.List;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public abstract class YuiMenuItemListModel implements IModel, IDetachable
{

    private transient List<AbstractYuiMenuItem> menuItems;
    
    public Object getObject()
    {
        if(null == menuItems) {
            menuItems = getMenuItems();
        }
        return menuItems;
    }

    protected abstract List<AbstractYuiMenuItem> getMenuItems();

    public void setObject(Object object)
    {

    }

    public void detach()
    {
        this.menuItems = null;
    }

}
