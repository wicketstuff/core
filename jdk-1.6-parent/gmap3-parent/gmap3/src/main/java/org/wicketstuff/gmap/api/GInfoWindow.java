package org.wicketstuff.gmap.api;

import org.apache.wicket.Component;
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
    private boolean contentIsNode;

    /**
     * Constructor.
     * @param latLng the position where the info window should be opened
     * @param content the (HTML) content which should be shown. Any containing 
     * apostrophes will be escaped.
     */
    public GInfoWindow(GLatLng latLng, String content)
    {
      this(latLng);
      this.content = content.replace("'", "\\'");
    }
    
    /**
     * Constructor.
     * @param latLng the position where the info window should be opened
     * @param content the Component which should be shown. Internally uses document.getElementById('" + markupId + "') to link to the GInfoWindow 
     */
    public GInfoWindow(GLatLng latLng, Component content)
    {
      this(latLng);
      content.setOutputMarkupId(true);
      String markupId = content.getMarkupId(true);
      this.content = "document.getElementById('" + markupId + "')";
      contentIsNode = true;
    }
    
    private GInfoWindow(GLatLng latLng)
    {
        super();
        this.latLng = latLng;
    }

    @Override
    public String getJSconstructor()
    {

        Constructor constructor = null;
        if(!contentIsNode)
            constructor = new Constructor("google.maps.InfoWindow").add("{content: '" + content + "', position: " + latLng.toString() + "}");
        else
          constructor = new Constructor("google.maps.InfoWindow").add("{'content': " + content + ", position: " + latLng.toString() + "}");
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