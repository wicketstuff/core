package org.wicketstuff.clipboardjs;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class ClipboardJsReference extends JavaScriptResourceReference {

    public static final ClipboardJsReference INSTANCE = new ClipboardJsReference();

    private ClipboardJsReference() {
        super(ClipboardJsReference.class, "res/clipboard.js");
    }
}
