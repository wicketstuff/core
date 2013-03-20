package org.wicketstuff.gmap.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.gmap.GMapHeaderContributor;
import org.wicketstuff.gmap.geocoder.GeocoderStatus;

/**
 */
public abstract class GClientGeocoder extends AjaxEventBehavior
{

    private static final long serialVersionUID = 1L;
    // the TextField providing the requested address.
    private final TextField<?> _addressField;
    private final GMapHeaderContributor headerContrib;

    /**
     * Construct.
     *
     * @param event
     */
    public GClientGeocoder(String event, TextField<?> addressField)
    {
        super(event);

        this._addressField = addressField;
        this._addressField.setOutputMarkupId(true);

        this.headerContrib = new GMapHeaderContributor();
    }

    /**
     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(Component c, IHeaderResponse response)
    {
        super.renderHead(c, response);
        headerContrib.renderHead(c, response);
    }

    /**
     * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        Request request = RequestCycle.get().getRequest();
        String address = request.getRequestParameters().getParameterValue("address").toString();

        if (address != null)
        {
            GeocoderStatus status = GeocoderStatus.valueOf(request.getRequestParameters().getParameterValue("status").toString());
            onGeoCode(target, status,
                    request.getRequestParameters().getParameterValue("address").toString(),
                    GLatLng.parse(request.getRequestParameters().getParameterValue("coordinates").toString()));
        }
        else
        {
            target.appendJavaScript(getCallbackScript());
        }


    }

    public abstract void onGeoCode(AjaxRequestTarget target, GeocoderStatus status, String address, GLatLng latLng);

    /**
     * @see org.apache.wicket.ajax.AjaxEventBehavior#generateCallbackScript(java.lang.CharSequence)
     */
    @Override
    public CharSequence getCallbackScript()
    {
        return "Wicket.geocoder.getLatLng('" + getCallbackUrl() + "', '" + _addressField.getMarkupId() + "');";
    }
}
