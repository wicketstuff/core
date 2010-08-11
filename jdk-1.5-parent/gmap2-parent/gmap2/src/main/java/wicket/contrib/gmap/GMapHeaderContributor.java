package wicket.contrib.gmap;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

public class GMapHeaderContributor extends HeaderContributor
{
	private static final long serialVersionUID = 1L;

	// URL for Google Maps' API endpoint.
	private static final String GMAP_API_URL = "http://www.google.com/jsapi?key=";

	// We have some custom Javascript.
	private static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(
			GMap2.class, "wicket-gmap.js");

	protected static final String GOOGLE_LOAD_MAPS = "google.load(\"maps\", \"2.x\");";

	public GMapHeaderContributor(final String gMapKey)
	{
		super(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * see: <a
			 * href="http://www.google.com/apis/maps/documentation/#Memory_Leaks">IE
			 * memory leak issues</a>
			 * 
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(IHeaderResponse response)
			{
				response.renderJavascriptReference(GMAP_API_URL + gMapKey);
				response.renderJavascript(GOOGLE_LOAD_MAPS, GMapHeaderContributor.class.getName() + "_googleload");
				response.renderJavascriptReference(WicketEventReference.INSTANCE);
				response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
				response.renderJavascriptReference(WICKET_GMAP_JS);
				// see:
				// http://www.google.com/apis/maps/documentation/#Memory_Leaks
				response.renderOnEventJavascript("window", "onUnload", "google.maps.Unload();");
			}
		});
	}
}
