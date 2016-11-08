package org.wicketstuff.pdfjs;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * A panel for
 */
public class PdfJsPanel extends Panel {

    private final ResourceReference pdfDocument;
    private final WebComponent pdfJsCanvas;

    /**
     * Constructor.
     *
     * @param id The component id
     */
    public PdfJsPanel(String id, ResourceReference pdfDocument) {
        super(id);

        Args.notNull(pdfDocument, "pdfDocument");

        pdfJsCanvas = new WebComponent("pdfJsCanvas");
        pdfJsCanvas.setOutputMarkupId(true);
        add(pdfJsCanvas);
        this.pdfDocument = pdfDocument;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(PdfJsReference.INSTANCE));
        renderSetupJs(response);
    }

    private void renderSetupJs(final IHeaderResponse response) {

        PackageTextTemplate pdfSetupTemplate = new PackageTextTemplate(PdfJsReference.class, "res/pdfJs.setup.tmpl.js");
        Map<String, Object> variables = new HashMap<>();
        variables.put("pdfDocumentUrl", urlFor(pdfDocument, null));
        variables.put("pdfCanvasId", pdfJsCanvas.getMarkupId());

        response.render(OnDomReadyHeaderItem.forScript(pdfSetupTemplate.asString(variables)));
    }
}
