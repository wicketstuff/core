package org.wicketstuff.jwicket.ui;

import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavaScriptResourceReference;

public abstract class AbstractJqueryUiBehavior extends JQueryAjaxBehavior {

    private static final long serialVersionUID = 1L;

    protected AbstractJqueryUiBehavior(final JQueryJavaScriptResourceReference... requiredLibraries) {
        super(AbstractJqueryUiEmbeddedBehavior.jQueryUiCoreJs, requiredLibraries);
    }
}
