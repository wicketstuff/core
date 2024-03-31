package org.wicketstuff.datatables.themes;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.res.DataTablesBootstrapCssReference;
import org.wicketstuff.datatables.res.DataTablesBootstrapJsReference;

/**
 *
 */
public class BootstrapTheme extends Behavior {

    public BootstrapTheme(Options options) {
        options.style(Options.Style.bootstrap);
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);

        tag.append("class", "table table-striped table-bordered", " ");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("/bootstrap/current/css/bootstrap.css")));
        response.render(CssHeaderItem.forReference(new DataTablesBootstrapCssReference()));
        response.render(JavaScriptHeaderItem.forReference(new DataTablesBootstrapJsReference()));
    }
}
