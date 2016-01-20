package org.wicketstuff.datatables;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.res.DataTablesCssReference;
import org.wicketstuff.datatables.res.DataTablesJsReference;
import org.wicketstuff.datatables.res.DataTablesScrollerJsReference;
import org.wicketstuff.datatables.res.DataTablesSelectJsReference;

/**
 *
 */
public class DataTablesBehavior extends Behavior {

    private final Options options;

    public DataTablesBehavior(final Options options) {
        this.options = options;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
    
        tag.append("class", "display", " ");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        boolean shouldRenderCss = shouldRenderCss();

        if (shouldRenderCss) {
            response.render(CssHeaderItem.forReference(new DataTablesCssReference()));
        }
        response.render(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));

        Options.Style style = options.getStyle();
        if (shouldRenderCss) {
            style.renderHead(response, "dataTables");
        }

        if (options.contains(Options.Scroller)) {
            if (shouldRenderCss) {
                style.renderHead(response, "scroller");
            }
            response.render(JavaScriptHeaderItem.forReference(new DataTablesScrollerJsReference()));
        }

        if (options.contains(Options.Select)) {
            if (shouldRenderCss) {
                style.renderHead(response, "select");
            }
            response.render(JavaScriptHeaderItem.forReference(new DataTablesSelectJsReference()));
        }
    }

    protected boolean shouldRenderCss() {
        return true;
    }
}
