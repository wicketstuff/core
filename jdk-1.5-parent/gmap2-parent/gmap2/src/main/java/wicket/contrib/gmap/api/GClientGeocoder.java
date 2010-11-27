package wicket.contrib.gmap.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

import wicket.contrib.gmap.GMapHeaderContributor;

/**
 */
public abstract class GClientGeocoder extends AjaxEventBehavior {

	private static final long serialVersionUID = 1L;

	// the TextField providing the requested address.
	private TextField<?> addressField;

	private GMapHeaderContributor headerContrib;

	/**
	 * Construct.
	 * 
	 * @param event
	 */
	public GClientGeocoder(String event, TextField<?> addressField, String key) {
		super(event);

		this.addressField = addressField;
		this.addressField.setOutputMarkupId(true);

		this.headerContrib = new GMapHeaderContributor(key);
	}

	@Override
	public void renderHead(Component c, IHeaderResponse response) {
		super.renderHead(c, response);
		headerContrib.renderHead(response);
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		IRequestParameters parameters = request.getRequestParameters();
		onGeoCode(target, parameters.getParameterValue("status").toInt(),
				parameters.getParameterValue("address").toString(), 
				GLatLng.parse(parameters.getParameterValue("point").toString()));
	}

	public abstract void onGeoCode(AjaxRequestTarget target, int status,
			String address, GLatLng latLng);

	@Override
	protected CharSequence generateCallbackScript(CharSequence partialCall) {
		return "Wicket.geocoder.getLatLng('" + getCallbackUrl() + "', '"
				+ addressField.getMarkupId() + "');" + "return false;";
	}
}
