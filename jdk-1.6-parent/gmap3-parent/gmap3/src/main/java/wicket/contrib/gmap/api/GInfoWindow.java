package wicket.contrib.gmap.api;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.gmap.GMap;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GInfoWindow"
 * >GInfoWindow</a>.
 */
public class GInfoWindow extends WebMarkupContainer
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private GLatLng _latLng;

	private GMarker _marker;

	private final RepeatingView _content = new RepeatingView("content");

	public GInfoWindow()
	{
		super("infoWindow");
		setOutputMarkupId(true);
		add(_content);

	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update()
	{
		Request request = RequestCycle.get().getRequest();

		if (Boolean.parseBoolean(request.getParameter("infoWindow.hidden")))
		{
			// Attention: don't use close() as this might result in an
			// endless AJAX request loop
			_marker = null;
			_latLng = null;
		}
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GInfoWindow open(GLatLng latLng, Component content)
	{
		content.setOutputMarkupId(true);
		this._latLng = latLng;
		this._marker = null;
		_content.addOrReplace(content);
		if (AjaxRequestTarget.get() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSopen(latLng, content));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	/**
	 * Open an info window.
	 * 
	 * @param marker
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GInfoWindow open(GMarker marker, Component content)
	{
		content.setOutputMarkupId(true);
		_content.addOrReplace(content);

		this._latLng = null;
		this._marker = marker;

		if (AjaxRequestTarget.get() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSopen(marker, content));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	public boolean isOpen()
	{
		return (_latLng != null || _marker != null);
	}

	public void close()
	{
		_marker = null;
		_latLng = null;

		if (AjaxRequestTarget.get() != null && getGMap().isDirectUpdate())
		{
			AjaxRequestTarget.get().appendJavascript(getJSclose());
			AjaxRequestTarget.get().addComponent(this);
		}
	}

	private String getJSopen(GLatLng latLng, Component content)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(getGMap().getJSinvoke(
			"openInfoWindow(" + latLng.getJSconstructor() + ", '" + content.getMarkupId() + "')"));
		return buffer.toString();
	}

	private String getJSopen(GMarker marker, Component content)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(getGMap().getJSinvoke(
			"openInfoWindow(null, '" + content.getMarkupId() + "', " + marker.getId() + " )"));
		return buffer.toString();
	}

	private String getJSclose()
	{
		return getGMap().getJSinvoke("closeInfoWindow()");
	}

	private GMap getGMap()
	{
		return findParent(GMap.class);
	}

}