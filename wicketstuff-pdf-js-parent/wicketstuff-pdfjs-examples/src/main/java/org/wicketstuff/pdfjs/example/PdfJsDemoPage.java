package org.wicketstuff.pdfjs.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.pdfjs.PdfJsConfig;
import org.wicketstuff.pdfjs.PdfJsPanel;

/**
 * Demo page of {@link org.wicketstuff.pdfjs.PdfJsPanel}
 */
public class PdfJsDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PdfJsDemoPage(final PageParameters parameters) {
		super(parameters);

		PdfJsConfig config = new PdfJsConfig();
		final PackageResourceReference pdfDocument = new PackageResourceReference(PdfJsDemoPage.class, "DemoDocument.pdf");
		config.withDocumentUrl(urlFor(pdfDocument, null));
		config.withInitialPage(2);
		final PdfJsPanel pdfJsPanel = new PdfJsPanel("pdfJsPanel", config);
		add(pdfJsPanel);
	}
}
