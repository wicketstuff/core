package org.wicketstuff.html5.geolocation;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.util.template.PackagedTextTemplate;

public abstract class AjaxGeolocationBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private static PackagedTextTemplate GEOLOCATION_TMPL_JS = new PackagedTextTemplate(
			AjaxGeolocationBehavior.class, "geolocation.js",
			"application/javascript", "UTF-8");

	@Override
	protected void respond(AjaxRequestTarget target) {
		final Request request = RequestCycle.get().getRequest();
        final String latitude = request.getParameter("lat");
        final String longitude = request.getParameter("long");
        onGeoAvailable(target, latitude, longitude);
	}

	protected abstract void onGeoAvailable(AjaxRequestTarget target, String latitude, String longitude);

	@Override
	protected void onBind() {
		super.onBind();

		final Component component = getComponent();
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		final CharSequence callbackUrl = getCallbackUrl(false);
		final String componentMarkupId = getComponent().getMarkupId();

		final Map<String, String> variables = new HashMap<String, String>();
		variables.put("componentId", componentMarkupId);
		variables.put("callbackUrl", callbackUrl.toString());

		final String javascript = MapVariableInterpolator.interpolate(
				GEOLOCATION_TMPL_JS.asString(), variables);
		response.renderOnDomReadyJavascript(javascript);
	}
}
