package org.wicketstuff.pdfjs.example;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.IOUtils;
import org.wicketstuff.pdfjs.PdfJsConfig;
import org.wicketstuff.pdfjs.PdfJsPanel;

import java.io.IOException;
import java.io.InputStream;

/**
 * Demo page of {@link org.wicketstuff.pdfjs.PdfJsPanel}
 */
public class PdfJsDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PdfJsDemoPage(final PageParameters parameters) {
		super(parameters);

		PdfJsConfig config = new PdfJsConfig();
		final ResourceReference pdfDocument = new ResourceReference("somePdfAsBytes") {
			@Override
			public IResource getResource() {
				try (InputStream inputStream = PdfJsDemoPage.class.getResourceAsStream("DemoDocument.pdf")) {
					final byte[] bytes = IOUtils.toByteArray(inputStream);
					return new ByteArrayResource("application/pdf", bytes);
				} catch (IOException iox) {
					throw new WicketRuntimeException(iox);
				}
			}
		};
//				new PackageResourceReference(PdfJsDemoPage.class, "DemoDocument.pdf");
		config.withDocumentUrl(urlFor(pdfDocument, null));
		config.withInitialPage(1);
		final PdfJsPanel pdfJsPanel = new PdfJsPanel("pdfJsPanel", config);
		add(pdfJsPanel);
	}
}
