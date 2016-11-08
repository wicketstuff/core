package org.wicketstuff.pdfjs.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.pdfjs.PdfJsPanel;

/**
 * Demo page of {@link org.wicketstuff.pdfjs.PdfJsPanel}
 */
public class PdfJsDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PdfJsDemoPage(final PageParameters parameters) {
		super(parameters);

		final PackageResourceReference pdfDocument = new PackageResourceReference(PdfJsDemoPage.class, "DemoDocument.pdf");
		final PdfJsPanel pdfJsPanel = new PdfJsPanel("pdfJsPanel", pdfDocument);
//		pdfJsPanel.setInitialPageNumber(2);
		add(pdfJsPanel);
	}
}
