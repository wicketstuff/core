package org.wicketstuff.datatables;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DataTablesJsReference extends WebjarsJavaScriptResourceReference {
    public DataTablesJsReference() {
        super("/datatables/current/js/jquery.dataTables.js");
    }

    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<HeaderItem>();
        ResourceReference jQueryReference = Application.get().getJavaScriptLibrarySettings().getJQueryReference();
        dependencies.add(JavaScriptHeaderItem.forReference(jQueryReference));
        return dependencies;
    }
}
