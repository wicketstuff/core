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

	
	private static String getURL (boolean developmentMode, String version) {
		String prefix = "http://dev.openlayers.org/releases/OpenLayers-"+version;
		
		if (developmentMode) {
			return prefix + "/lib/OpenLayers.js";
		}
		else {
			// production mode
			return prefix + "/OpenLayers.js";
		}
		
	}
	
	/**
	 * 
	 * @param developmentMode
	 * @param openLayersVersion the version of openlayers to use: like 2.9.1 or 2.8.  Comes from the list available here: http://dev.openlayers.org/releases
	 */
	public OpenLayersMapHeaderContributor(final boolean developmentMode, final String openLayersVersion) {
		super(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(IHeaderResponse response) {
				
				String url = getURL(developmentMode, openLayersVersion);
				
				response.renderJavascriptReference(url);
				response
						.renderJavascriptReference(WicketEventReference.INSTANCE);
				response
						.renderJavascriptReference(WicketAjaxReference.INSTANCE);
				response.renderJavascriptReference(WICKET_OMAP_JS);
			}
		});
	}
}
