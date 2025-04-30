package org.wicketstuff.datatables.res;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import java.util.List;
import java.util.Locale;

public abstract class DataTablesPluginJsReference extends WebjarsJavaScriptResourceReference {


    public DataTablesPluginJsReference(String name) {
        super(name);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));
        return dependencies;
    }
}
