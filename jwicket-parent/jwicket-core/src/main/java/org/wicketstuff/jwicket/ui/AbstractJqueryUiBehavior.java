package org.wicketstuff.jwicket.ui;

import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;

public abstract class AbstractJqueryUiBehavior extends JQueryAjaxBehavior {

    private static final long serialVersionUID = 1L;

    protected AbstractJqueryUiBehavior(final JQueryResourceReference... requiredLibraries) {
        super(AbstractJqueryUiEmbeddedBehavior.jQueryUiCoreJs, requiredLibraries);
    }
}
