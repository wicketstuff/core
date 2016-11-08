package org.wicketstuff.pdfjs;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import java.util.List;

public class PdfJsReference extends JQueryPluginResourceReference {

    public static final PdfJsReference INSTANCE = new PdfJsReference();

    private PdfJsReference() {
        super(PdfJsReference.class, "res/pdf.js");
    }

    @Override
    public List<HeaderItem> getDependencies() {
        final List<HeaderItem> dependencies = super.getDependencies();
        ResourceReference wicketEventReference;
        if (Application.exists()) {
            wicketEventReference = Application.get().getJavaScriptLibrarySettings().getWicketEventReference();
        } else {
            wicketEventReference = WicketEventJQueryResourceReference.get();
        }
        dependencies.add(JavaScriptHeaderItem.forReference(wicketEventReference));
        return dependencies;
    }
}
