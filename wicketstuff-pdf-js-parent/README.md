An integration between Apache Wicket and PDF JS - view PDF documents inline.

For more information see https://github.com/mozilla/pdf.js

## Usage

Java:
```java
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
		final ResourceReference pdfDocument = new PackageResourceReference(PdfJsDemoPage.class, "DemoDocument.pdf");
		config.withDocumentUrl(urlFor(pdfDocument, null));
		config.withInitialPage(1);
		final PdfJsPanel pdfJsPanel = new PdfJsPanel("pdfJsPanel", config);
		add(pdfJsPanel);
	}
}

```

HTML:
```html
<div wicket:id="pdfJsPanel"></div>

		<div>
			<button id="prev">Previous</button>
			<button id="next">Next</button>
			&nbsp; &nbsp;
			<span>Page: <span id="page_num"></span> / <span id="page_count"></span></span>
		</div>

		<script type="text/javascript">
			$(function () {
				$('#prev').click(function () {
					Wicket.Event.publish(Wicket.PDFJS.Topic.PREVIOUS_PAGE);
				});
				$('#next').click(function () {
					Wicket.Event.publish(Wicket.PDFJS.Topic.NEXT_PAGE);
				});

				Wicket.Event.subscribe(Wicket.PDFJS.Topic.TOTAL_PAGES, function (jqEvent, total) {
					$('#page_count').text(total);
				});
			});
		</script>

```
