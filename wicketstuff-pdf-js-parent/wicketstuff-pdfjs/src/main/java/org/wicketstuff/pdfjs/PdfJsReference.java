package org.wicketstuff.pdfjs;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class PdfJsReference extends JavaScriptResourceReference {

    public static final PdfJsReference INSTANCE = new PdfJsReference();

    private PdfJsReference() {
        super(PdfJsReference.class, "res/pdf.js");
    }
}
