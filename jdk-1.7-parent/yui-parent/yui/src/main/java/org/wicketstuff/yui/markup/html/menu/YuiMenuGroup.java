package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public abstract class YuiMenuGroup extends Panel
{
    public static final String MENU_GROUP_ID = "menuGroup";
    public static final String GROUP_TITLE_ID = "groupTitle";
    
    private int index = -1;
    
    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public YuiMenuGroup(YuiMenuItemListModel model)
    {
        super(MENU_GROUP_ID);
        setRenderBodyOnly(true);
        add(getGroupTitle(GROUP_TITLE_ID));
        WebMarkupContainer mg = new WebMarkupContainer("mg");
        add(mg);
        
        mg.add(new ListView("menuItemList", model) {

            @Override
            protected void populateItem(ListItem item)
            {
                item.setRenderBodyOnly(true);
                AbstractYuiMenuItem mi = (AbstractYuiMenuItem) item.getModelObject();
                mi.setIndex(item.getIndex());
                mi.setGroupIndex(YuiMenuGroup.this.getIndex());
                if(0 == item.getIndex()) {
                    mi.add(new AttributeAppender("class", true, new Model("first-of-type"), " "));
                }
                item.add(mi);
            }
            
        }.setReuseItems(true));
    }
    
    protected abstract WebComponent getGroupTitle(String id);

}
