package org.wicketstuff.pdfjs;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Url;

/**
 * A panel for
 */
public class PdfJsPanel extends Panel {

    private final PdfJsConfig config;
    private final WebComponent pdfJsCanvas;

    /**
     * Constructor.
     *
     * @param id The component id
     */
    public PdfJsPanel(String id, PdfJsConfig config) {
        super(id);

        this.config = config;

        pdfJsCanvas = new WebComponent("pdfJsCanvas");
        pdfJsCanvas.setOutputMarkupId(true);
        config.withCanvasId(pdfJsCanvas.getMarkupId());
        add(pdfJsCanvas);
    }

    public PdfJsConfig getConfig() {
        return config;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(PdfJsReference.INSTANCE));
        renderWicketStuffPdfJs(response);
    }

    protected void renderWicketStuffPdfJs(final IHeaderResponse response) {
        config.withWorkerUrl(createPdfJsWorkerUrl());
        response.render(JavaScriptHeaderItem.forReference(WicketStuffPdfJsReference.INSTANCE));
        response.render(OnDomReadyHeaderItem.forScript(String.format("Wicket.PDFJS.init(%s)", config.toJsonString())));
//
//        final PackageTextTemplate pdfSetupTemplate = new PackageTextTemplate(PdfJsReference.class, "res/wicketstuff-pdf.js");
//        final Map<String, Object> variables = new HashMap<>();
//        variables.put("pdfDocumentUrl", urlFor(pdfDocument, null));
//        variables.put("pdfWorkerUrl", createPdfJsWorkerUrl());
//        variables.put("pdfCanvasId", pdfJsCanvas.getMarkupId());
//        variables.put("pdfWorkerDisabled", isPdfJsWorkerDisabled());
//        response.render(OnDomReadyHeaderItem.forScript(pdfSetupTemplate.asString(variables)));
    }

    protected boolean isPdfJsWorkerDisabled() {
        return false;
    }

    protected String createPdfJsWorkerUrl() {
        final CharSequence _pdfJsUrl = urlFor(PdfJsReference.INSTANCE, null);
        final Url pdfJsUrl = Url.parse(_pdfJsUrl);
        final Url pdfJsWorkerUrl = Url.parse("./pdf.worker.js");
        pdfJsUrl.resolveRelative(pdfJsWorkerUrl);
        return pdfJsUrl.toString();
    }

}
