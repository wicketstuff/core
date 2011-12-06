package org.wicketstuff.yui.markup.html.menu;


public class YuiSubMenu extends YuiMenu
{

    public YuiSubMenu(String id, YuiMenuGroupListModel model)
    {
        super(id, model);
    }

    @Override
    protected String getMenuElementId()
    {
        return null;
    }

    @Override
    protected String getMenuName()
    {
        return "yuiSubMenu";
    }

    
    

}
