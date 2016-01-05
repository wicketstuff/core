package org.wicketstuff.datatables;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

/**
 *
 */
public class DataTablesBehavior extends Behavior {

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
    
        tag.append("class", "display", " ");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
    
        response.render(CssHeaderItem.forReference(new DataTablesCssReference()));
        response.render(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));
    }
}
