package org.wicketstuff.pdfjs;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * A panel for
 */
public class PdfJsPanel extends Panel {

    /**
     * Constructor.
     *
     * @param id The component id
     */
    public PdfJsPanel(String id) {
        super(id);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(PdfJsReference.INSTANCE));
    }
}
