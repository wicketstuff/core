package org.wicketstuff.datatables.res;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;

import java.util.List;

public class DataTablesJsReference extends WebjarsJavaScriptResourceReference {
    public DataTablesJsReference() {
        super("datatables/current/js/dataTables.min.js");
    }

    @Override
    public List<HeaderItem> getDependencies()
    {
        final ResourceReference backingLibraryReference;
        if (Application.exists())
        {
            backingLibraryReference = Application.get()
                    .getJavaScriptLibrarySettings()
                    .getJQueryReference();
        }
        else
        {
            backingLibraryReference = JQueryResourceReference.getV3();
        }
        List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(JavaScriptHeaderItem.forReference(backingLibraryReference));
        return dependencies;
    }
}
