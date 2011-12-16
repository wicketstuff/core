package org.wicketstuff.yui.markup.html.menu;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public abstract class AbstractYuiMenuItem extends Panel
{
    public static final String MENU_ITEM_LINK_ID = "menuItemLink";
    public static final String MENU_ITEM_LABEL_ID = "menuItemLabel";
    public static final String MENU_ITEM_SUBMENU_ID = "submenu";
    public static final String FRAGMENT_NO_SUBMENU_ID = "menuItemNoSubMenu";
    public static final String FRAGMENT_WITH_SUBMENU_ID = "menuItemWithSubMenu";
    public static final String MENU_ITEM_ID = "theMenuItem";
    
    private static final String CSS_SELECTED = "selected";
    
    private static final String CSS_DISABLED = "disabled";
    
    private static final String CSS_CHECKED = "checked";
    
    private boolean checked = false;
    private boolean checkedChanged = false;

    
    private boolean selected = false;
    
    private boolean disabled = false;
    
    
    private String text;

    private WebMarkupContainer menuLink;
    
    
    private MarkupContainer subMenu;
    
    private int index = -1;
    
    private int groupIndex = -1;
    
    private YuiMenuPath menuPath;
    
    private boolean inited = false;

    public AbstractYuiMenuItem(String id, String text)
    {
        super(id);
        setOutputMarkupId(true);
        setText(text);
        Label label = getLabel(MENU_ITEM_LABEL_ID);
        label.setRenderBodyOnly(true);
        add(new CheckedMenuItemBehavior());
                                
        subMenu = getSubMenu(MENU_ITEM_SUBMENU_ID);
        if(null == subMenu) {
            Fragment fragNoSubMenu = new Fragment(MENU_ITEM_ID, FRAGMENT_NO_SUBMENU_ID);
            menuLink = getLink(MENU_ITEM_LINK_ID);
            menuLink.add(label);
            fragNoSubMenu.add(menuLink);
            fragNoSubMenu.setRenderBodyOnly(true);
            add(fragNoSubMenu);
        } else {
            Fragment fragWithSubMenu = new Fragment(MENU_ITEM_ID, FRAGMENT_WITH_SUBMENU_ID);

            fragWithSubMenu.add(label);
            
            subMenu.setRenderBodyOnly(true);
            
            fragWithSubMenu.add(subMenu);
            
            
            fragWithSubMenu.setRenderBodyOnly(true);
            
            add(fragWithSubMenu);
            
        }
        

    }
    
    protected YuiMenuPath getMenuPath () 
    {
        if(null == menuPath) {
            AbstractYuiMenuItem p = (AbstractYuiMenuItem) findParent(AbstractYuiMenuItem.class);
            if(p != null) {
                menuPath = new YuiMenuPath(getIndex(), getGroupIndex(),p.getMenuPath(), null);
            } else {
                AbstractYuiMenu aym = (AbstractYuiMenu) findParent(AbstractYuiMenu.class);
                if(aym != null) {
                    menuPath = new YuiMenuPath(getIndex(), getGroupIndex(), null, aym.getMenuName());
                } else {
                    menuPath = new YuiMenuPathEmpty(getIndex(), getGroupIndex());
                }
            }
        }
        return menuPath;
    }

    

    public abstract WebMarkupContainer getLink(String menuItemLinkId);


    public abstract MarkupContainer getSubMenu(String menuItemSubMenuId);

    public abstract String getMenuClass();
    
    public final String getText()
    {
        return text;
    }

    public final void setText(String text)
    {
        this.text = text;
    }

    public Label getLabel(String menuItemLabelId)
    {
        String labelText = getText();
        return new Label(menuItemLabelId, labelText);
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
        setCheckedChanged(true);
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
    
    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
    
    protected boolean isCheckedChanged()
    {
        boolean current = checkedChanged;
        checkedChanged = false;
        return current;
    }

    protected void setCheckedChanged(boolean checkedChanged)
    {
        this.checkedChanged = checkedChanged;
    }
    
    public void toggleCheckedScript(AjaxRequestTarget target) 
    {
        String menuPath = getMenuPath().toPath();
        if(!menuPath.contains("EMPTY PATH")) {
            String menuItemCheckScript = menuPath + ".cfg.setProperty(\"checked\", " + AbstractYuiMenuItem.this.isChecked() + ");";
            target.appendJavascript(menuItemCheckScript);
        }
    }

    public static class YuiMenuPath implements Serializable
    {
        protected final int index;
        protected final int groupIndex;
        private final YuiMenuPath parent;
        private final String name;
        public YuiMenuPath(int index, int groupIndex, YuiMenuPath parent, String name)
        {
            this.index = index;
            this.groupIndex = groupIndex;
            this.parent = parent;
            this.name = name;
        }
        
        
        public String toPath() {
            if(parent == null && null != name) {
                return name + ".getItem(" + index + ")";
            } else {
                return parent.toPath() + ".cfg.getProperty(\"submenu\").getItem(" + index 
                    + "," + groupIndex + ")";
            }
        }
        
    }
    
    public static class YuiMenuPathEmpty extends YuiMenuPath
    {

        public YuiMenuPathEmpty(int index, int groupIndex)
        {
            super(index, groupIndex, null, null);
        }
        
        @Override
        public String toPath()
        {
            return "Index " + index + " has EMPTY PATH";
        }
    }
    
    class CheckedMenuItemBehavior extends AbstractBehavior
    {
        @Override
        public void renderHead(IHeaderResponse response)
        {
            if(AbstractYuiMenuItem.this.isCheckedChanged()) {
                String menuPath = AbstractYuiMenuItem.this.getMenuPath().toPath();
                if(!menuPath.contains("EMPTY PATH")) {
                    String menuItemCheckScript = menuPath + ".cfg.setProperty(\"checked\", " + AbstractYuiMenuItem.this.isChecked() + ");";
                    response.renderOnLoadJavascript(menuItemCheckScript);
                }
            }
        }
    }

    public int getGroupIndex()
    {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex)
    {
        this.groupIndex = groupIndex;
    }


}
