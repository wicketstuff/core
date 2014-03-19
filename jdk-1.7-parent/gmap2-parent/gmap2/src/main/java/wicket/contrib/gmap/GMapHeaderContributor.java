package wicket.contrib.gmap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnEventHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;

/**
 * 
 * using a {@link Behavior}
 */
public class GMapHeaderContributor extends Behavior
{
	private static final long serialVersionUID = 1L;

	// URL for Google Maps' API endpoint.
	private static final String GMAP_API_URL = "http://www.google.com/jsapi?key=";
	// We have some custom JavaScript.
	private static final ResourceReference WICKET_GMAP_JS = new JavaScriptResourceReference(
		GMap2.class, "wicket-gmap.js");
	protected static final String GOOGLE_LOAD_MAPS = "google.load(\"maps\", \"2.x\");";

	private final String gMapKey;

	/**
	 * 
	 * @param gMapKey
	 */
	public GMapHeaderContributor(final String gMapKey)
	{
		this.gMapKey = gMapKey;
	}

	/**
	 * see: <a href="http://www.google.com/apis/maps/documentation/#Memory_Leaks">IE memory leak
	 * issues</a>
	 */
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forUrl(GMAP_API_URL + gMapKey));
		response.render(JavaScriptHeaderItem.forScript(GOOGLE_LOAD_MAPS,
			GMapHeaderContributor.class.getName() + "_googleload"));
		CoreLibrariesContributor.contributeAjax(component.getApplication(), response);
		response.render(JavaScriptHeaderItem.forReference(WICKET_GMAP_JS));
		// see: http://www.google.com/apis/maps/documentation/#Memory_Leaks
		response.render(OnEventHeaderItem.forScript("window", "onUnload", "google.maps.Unload();"));
	}
}
