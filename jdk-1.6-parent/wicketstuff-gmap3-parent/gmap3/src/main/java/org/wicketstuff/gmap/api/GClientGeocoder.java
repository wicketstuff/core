package org.wicketstuff.gmap.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.gmap.GMapHeaderContributor;
import org.wicketstuff.gmap.geocoder.GeocoderStatus;

/**
 */
public abstract class GClientGeocoder extends AjaxEventBehavior {
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
	public GClientGeocoder(final String event, final TextField<?> addressField) {
		this(event, addressField, Duration.milliseconds(500));
	}

	public GClientGeocoder(final String event, final TextField<?> addressField, final Duration timeout) {
		super(event);

		addressField.setOutputMarkupId(true);
		this.addressFieldMarkupId = addressField.getMarkupId();

		this.headerContrib = new GMapHeaderContributor();
		this.timeout = timeout;
	}

	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		super.renderHead(c, response);
		headerContrib.renderHead(c, response);
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target) {
		final Request request = RequestCycle.get().getRequest();
		final IRequestParameters requestParameters = request.getRequestParameters();
		final String status = requestParameters.getParameterValue("status").toString();
		final GeocoderStatus geocoderStatus = status != null ? GeocoderStatus.valueOf(status) : null;

		String address = null;
		GLatLng coordinates = null;
		if (geocoderStatus == null) {
			final StringBuilder js = new StringBuilder();
			js.append("setTimeout(function() {").append(getCallbackScript()).append("}, ")
			        .append(timeout.getMilliseconds()).append(");");
			target.appendJavaScript(js);
		} else {
			if (GeocoderStatus.OK.equals(geocoderStatus)) {
				address = requestParameters.getParameterValue("address").toString();
				coordinates = GLatLng.parse(requestParameters.getParameterValue("coordinates").toString());
			}
			onGeoCode(target, geocoderStatus, address, coordinates);
		}
	}

	/**
	 * @param target
	 * @param status The status of the client side operation.
	 * @param address The address if the status is {@code GeocoderStatus#OK}, otherwise- {@code null}
	 * @param latLng The coordinates if the status is {@code GeocoderStatus#OK}, otherwise- {@code null}
	 */
	public abstract void onGeoCode(AjaxRequestTarget target, GeocoderStatus status, String address, GLatLng latLng);

	@Override
	public CharSequence getCallbackScript() {
		return "Wicket.geocoder.getLatLng('" + getCallbackUrl() + "', '" + addressFieldMarkupId + "');";
	}
}
