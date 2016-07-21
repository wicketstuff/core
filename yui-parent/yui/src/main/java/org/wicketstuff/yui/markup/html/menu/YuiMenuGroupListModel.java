package org.wicketstuff.yui.markup.html.menu;

import java.util.List;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public abstract class YuiMenuGroupListModel implements IModel, IDetachable
{

    private transient List<YuiMenuGroup> menuGroupList;
    
    public Object getObject()
    {
        if(menuGroupList == null) {
            menuGroupList = getMenuGroupList();
        }
        return menuGroupList;
    }

    public void setObject(Object object)
    {
        // noop
    }

    public void detach()
    {
        this.menuGroupList = null;
    }
    
    protected abstract List<YuiMenuGroup> getMenuGroupList();

}
