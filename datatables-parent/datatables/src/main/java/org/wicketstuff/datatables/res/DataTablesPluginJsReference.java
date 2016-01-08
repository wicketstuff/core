package org.wicketstuff.datatables.res;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import java.util.List;
import java.util.Locale;

public abstract class DataTablesPluginJsReference extends JQueryPluginResourceReference {

    public DataTablesPluginJsReference(final Class<?> scope, final String name) {
        super(scope, name);
    }

    public DataTablesPluginJsReference(final Class<?> scope, final String name, final Locale locale, final String style, final String variation) {
        super(scope, name, locale, style, variation);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));
        return dependencies;
    }
}
