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
	
	private static final String GMAP_API_ONLY_URL = "%s://www.google.com/jsapi?key=%s";
	
	// URL for Google Maps' API endpoint.
	private static final String GMAP_API_URL = "%s://maps.google.com/maps/api/js?v=3&sensor=false%s";

	// default key
	private static final String GMAP_KEY = "ABQIAAAAfLZh9Q70mEhlEushIfE2nBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQm29ZVCHJpfOqSLEonVznw3a7KUQ";

	private static final String HTTP = "http";

	// We have some custom Javascript.
	static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(GMap.class,
		"wicket-gmap.js");

	protected static final String EMPTY = "";

	private String _clientId;

	public GMapHeaderContributor()
	{
		this(HTTP, null, false, GMAP_KEY);
	}

	public GMapHeaderContributor(final boolean loadApiOnly)
	{
		this(HTTP, null, loadApiOnly, GMAP_KEY);
	}

	public GMapHeaderContributor(final String schema, final boolean loadApiOnly)
	{
		this(schema, null, loadApiOnly, GMAP_KEY);
	}

	public GMapHeaderContributor(final String schema, final boolean loadApiOnly, final String key)
	{
		this(schema, null, loadApiOnly, key);
	}

	public GMapHeaderContributor(final String schema)
	{
		this(schema, null, false, GMAP_KEY);
	}

	/**
	 * Constructor.
	 * 
	 * Should be added to the page.
	 * 
	 * @param schema http or https?
	 * @param clientId id getting after registering for Google Maps API for Business 
	 * @param loadApiOnly if the init of the map should be done only on the first real visible viewing of the map this parameter should be true
	 * @param key the api key (during development it seams this was only necessary if only the api should loaded) 
	 */
	public GMapHeaderContributor(final String schema, final String clientId, final boolean loadApiOnly, final String key)
	{
		super(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(IHeaderResponse response)
			{
				if (loadApiOnly)
				{
					response.renderJavascriptReference(String.format(GMAP_API_ONLY_URL, schema, key));
				}
				else
				{
					final String clientParm;
					if (clientId != null && !EMPTY.equals(clientId))
					{
						clientParm = "&client=" + clientId;
					}
					else
					{
						clientParm = EMPTY;
					}
					response.renderJavascriptReference(String.format(GMAP_API_URL, schema, clientParm));
				}
				response.renderJavascriptReference(WicketEventReference.INSTANCE);
				response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
				if (!loadApiOnly)
				{
					response.renderJavascriptReference(GMapHeaderContributor.WICKET_GMAP_JS);
				}
			}
		});
	}

	String getClientId()
	{
		return _clientId;
	}
}
