package org.wicketstuff.clipboardjs.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.pdfjs.PdfJsPanel;

/**
 * Demo page of {@link org.wicketstuff.pdfjs.PdfJsPanel}
 */
public class PdfJsDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PdfJsDemoPage(final PageParameters parameters) {
		super(parameters);

		add(new PdfJsPanel("pdfJsPanel"));
	}
}
