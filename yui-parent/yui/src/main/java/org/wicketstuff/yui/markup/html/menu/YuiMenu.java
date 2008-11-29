package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public abstract  class YuiMenu extends AbstractYuiMenu
{

    public YuiMenu(String id, YuiMenuGroupListModel model)
    {
        super(id);
        getMenuContainer().add(new ListView ("menuGroupList", model) {

            @Override
            protected void populateItem(ListItem item)
            {
                item.setRenderBodyOnly(true);
                YuiMenuGroup ymg = (YuiMenuGroup) item.getModelObject();
                ymg.setIndex(item.getIndex());
                ymg.setRenderBodyOnly(true);
                item.add(ymg);
            }
            
        }.setReuseItems(true));
    }

    @Override
    protected String getMenuClass()
    {
        return "yuimenu";
    }

    @Override
    protected abstract String getMenuElementId();
    
    @Override
    protected String getMenuType()
    {
        return "Menu";
    }

}
