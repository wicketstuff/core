package org.wicketstuff.gmap.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.gmap.GMapHeaderContributor;
import org.wicketstuff.gmap.geocoder.GeocoderStatus;

/**
 */
public abstract class GClientGeocoder extends AjaxEventBehavior
{
    private static final long serialVersionUID = 1L;

    // the markup id of the TextField providing the requested address.
    private final String addressFieldMarkupId;

    private final GMapHeaderContributor headerContrib;

	/**
	 * The time to wait before making a new request to self
	 */
	private final Duration timeout;

    /**
     * Construct.
     *
     * @param event
     */
    public GClientGeocoder(String event, TextField<?> addressField)
    {
	    this(event, addressField, Duration.milliseconds(500));
    }

	public GClientGeocoder(String event, TextField<?> addressField, Duration timeout)
	{
        super(event);

        addressField.setOutputMarkupId(true);
        this.addressFieldMarkupId = addressField.getMarkupId();

        this.headerContrib = new GMapHeaderContributor();
		this.timeout = timeout;
    }

    @Override
    public void renderHead(Component c, IHeaderResponse response)
    {
        super.renderHead(c, response);
        headerContrib.renderHead(c, response);
    }

    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        Request request = RequestCycle.get().getRequest();
        IRequestParameters requestParameters = request.getRequestParameters();
        String address = requestParameters.getParameterValue("address").toString();

        if (address != null)
        {
            GeocoderStatus status = GeocoderStatus.valueOf(requestParameters.getParameterValue("status").toString());
            onGeoCode(target, status,
                    requestParameters.getParameterValue("address").toString(),
                    GLatLng.parse(requestParameters.getParameterValue("coordinates").toString()));
        }
        else
        {
	        StringBuilder js = new StringBuilder();
	        js.append("setTimeout(function() {")
			        .append(getCallbackScript())
			        .append("}, ")
			        .append(timeout.getMilliseconds())
			        .append(");");
            target.appendJavaScript(js);
        }


    }

    public abstract void onGeoCode(AjaxRequestTarget target, GeocoderStatus status, String address, GLatLng latLng);

    @Override
    public CharSequence getCallbackScript()
    {
        return "Wicket.geocoder.getLatLng('" + getCallbackUrl() + "', '" + addressFieldMarkupId + "');";
    }
}
