package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;

public abstract class CheckedYuiMenuItem extends YuiMenuItem
{

    public CheckedYuiMenuItem(String label)
    {
        super(label);
    }

    @Override
    public AbstractLink getLink(String menuItemLinkId)
    {
        AjaxLink link = new AjaxLink(menuItemLinkId) {

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                CheckedYuiMenuItem.this.setChecked(!CheckedYuiMenuItem.this.isChecked());
                onCheck(target);
            }
            
            @Override
            protected IAjaxCallDecorator getAjaxCallDecorator()
            {
                return new YuiMenuItemCheckedToggleScriptDecorator();
            }
            
        };
        
        
        
        return link;
    }
    
    protected abstract void onCheck(AjaxRequestTarget target);

    class YuiMenuItemCheckedToggleScriptDecorator extends AjaxCallDecorator
    {
        
        public String getToggleOnClickScript()
        {
            String id = CheckedYuiMenuItem.this.getMarkupId();
            StringBuilder checkScript = new StringBuilder("checker" + id);
            checkScript.append(" = YAHOO.widget.MenuManager.getFocusedMenuItem(); ");
            checkScript.append(getToggleScript());
            return checkScript.toString();
        }
        
        public String getToggleScript() 
        {
            String id = CheckedYuiMenuItem.this.getMarkupId();
            StringBuilder checkScript = new StringBuilder ();
            checkScript.append("var is" + id + "checked =  checker" + id + ".cfg.getProperty(\'checked\'); ");
            checkScript.append("checker" + id + ".cfg.setProperty(\'checked\', !is" + id + "checked); " );
            return checkScript.toString();
        }
        
        @Override
        public CharSequence decorateScript(CharSequence script)
        {
            return "var " + getToggleOnClickScript() + script;
        }
        
        @Override
        public CharSequence decorateOnFailureScript(CharSequence script)
        {
            return getToggleScript() + script;
        }
        
    }

}
