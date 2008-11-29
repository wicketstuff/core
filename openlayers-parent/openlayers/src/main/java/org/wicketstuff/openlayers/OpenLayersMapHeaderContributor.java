package org.wicketstuff.openlayers;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

public class OpenLayersMapHeaderContributor extends HeaderContributor {
	private static final long serialVersionUID = 1L;

	// We have some custom Javascript.
	private static final ResourceReference WICKET_OMAP_JS = new JavascriptResourceReference(
			OpenLayersMap.class, "wicket-openlayersmap.js");

	public OpenLayersMapHeaderContributor() {
		super(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(IHeaderResponse response) {
				response
						.renderJavascriptReference("http://openlayers.org/api/OpenLayers.js");
				response
						.renderJavascriptReference(WicketEventReference.INSTANCE);
				response
						.renderJavascriptReference(WicketAjaxReference.INSTANCE);
				response.renderJavascriptReference(WICKET_OMAP_JS);
			}
		});
	}
}
