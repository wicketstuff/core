package org.wicketstuff.gmap.api;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.gmap.js.Constructor;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GInfoWindow"
 * >GInfoWindow</a>.
 *
 * You can attach following events to an InfoWindow in case you want to react to one of those:
 *
 * @see GEvent#content_changed
 * @see GEvent#domready
 * @see GEvent#closeclick
 */
public class GInfoWindow extends GOverlay
{

    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private GLatLng latLng;
    private GMarker marker;
    private String content;

    /**
     * Constructor.
     * @param latLng the position where the info window should be opened
     * @param content the (HTML) content which should be shown. Any containing 
     * apostrophes will be escaped.
     */
    public GInfoWindow(GLatLng latLng, String content)
    {
        super();
        this.latLng = latLng;
        // escape any apostrophes
        this.content = content.replace("'", "\\'");
    }

    @Override
    public String getJSconstructor()
    {

        Constructor constructor = new Constructor("google.maps.InfoWindow").add("{content: '" + content + "', position: " + latLng.toString() + "}");
        return constructor.toJS();
    }

    /**
     * Update state from a request to an AJAX target.
     */
    @Override
    protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
    {
    }

    public boolean isOpen()
    {
        return (latLng != null || marker != null);
    }

    public void close()
    {
        marker = null;
        latLng = null;

        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target != null)
        {
            target.appendJavaScript(super.getJSremove());
        }
    }
}