package org.wicketstuff.html5.geolocation;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.util.template.PackageTextTemplate;

public abstract class AjaxGeolocationBehavior extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	private static PackageTextTemplate GEOLOCATION_TMPL_JS = new PackageTextTemplate(
		AjaxGeolocationBehavior.class, "geolocation.js", "application/javascript", "UTF-8");
        
        /**Default timeout is set to 10 seconds.*/
        private int timeoutInMilliseconds = 10000;

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		final Request request = RequestCycle.get().getRequest();
		final String latitude = request.getRequestParameters().getParameterValue("lat").toString();
		final String longitude = request.getRequestParameters().getParameterValue("long").toString();
		final String errorCode = request.getRequestParameters().getParameterValue("code").toString();
		final String errorMessage = request.getRequestParameters().getParameterValue("message").toString();
		final String timeout = request.getRequestParameters().getParameterValue("timeout").toString();
                
                if (timeout != null)
                {
                    onNotAvailable(target, "3", "Timeout after " + getTimeout() + " milliseconds");
                }
                else if (latitude == null || longitude == null)
                {
                    onNotAvailable(target, errorCode, errorMessage);
                }
                else 
                {
                    onGeoAvailable(target, latitude, longitude);
                }
	}

	protected abstract void onGeoAvailable(AjaxRequestTarget target, String latitude,
		String longitude);

        /**
         * Override this method if you want to react on the event that
         * no geolocation could be determined.
         * @param target the AjaxRequestTarget
         * @param errorCode the numeric code for the reason no geolocation could 
         * be determined as specified by the W3C 
         * (@see http://dev.w3.org/geo/api/spec-source.html#position_error_interface).<br/>
         * "1" indicates a "permission denied"<br/>
         * "2" indicates that the position could not be determined<br/>
         * "3" indicates a timeout<br/>
         * @param errorMessage describes the details. Primarily for debugging purposes
         */
        protected void onNotAvailable(AjaxRequestTarget target, String errorCode,
		String errorMessage)
        {
            // do nothing
        }

	@Override
	protected void onBind()
	{
		super.onBind();

		final Component component = getComponent();
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		super.renderHead(c, response);

		final CharSequence callbackUrl = getCallbackUrl();
		final String componentMarkupId = getComponent().getMarkupId();

		final Map<String, String> variables = new HashMap<String, String>();
		variables.put("componentId", componentMarkupId);
		variables.put("callbackUrl", callbackUrl.toString());
		variables.put("timeout", timeoutInMilliseconds+"");

		final String javascript = MapVariableInterpolator.interpolate(
			GEOLOCATION_TMPL_JS.asString(), variables);
		response.render(OnDomReadyHeaderItem.forScript(javascript));
	}
        
        /**
         * Returns the current timeout in milliseconds.
         * @see #setTimeout(int) 
         */
        public int getTimeout()
        {
            return timeoutInMilliseconds;
        }
        
        /**
         * Sets the timeout in milliseconds.
         * If the user is ignoring the request for sharing his location or 
         * postpones his decision (in Firefox the "Not now" option) no callback
         * method is called. This is intended by design (see 
         * https://bugzilla.mozilla.org/show_bug.cgi?id=650247 ).
         * For those cases we can set a timeout. After the specified amount of
         * time the {@link onNotAvailable} method is called, passing a "3" as 
         * an error code.
         * 
         * The specification (http://dev.w3.org/geo/api/spec-source.html#position_error_interface)
         * defines also a timeout attribute:
         * "The length of time specified by the timeout property has elapsed 
         * before the implementation could successfully acquire a new Position 
         * object."
         * 
         * So it applies only for the case the user grants permission to share 
         * his location and is NOT the timeout which is set in this method!
         * 
         * It MIGHT happen that the determination of the location takes a bit 
         * longer, even though the user granted permission to share his location.
         * In this case first {@link #onNotAvailable(org.apache.wicket.ajax.AjaxRequestTarget, java.lang.String, java.lang.String) }
         * will be called and as soon as the location could be obtained
         * {@link #onGeoAvailable(org.apache.wicket.ajax.AjaxRequestTarget, java.lang.String, java.lang.String) }
         * will be called. So don't set the timeout too low.
         * 
         * The default timeout is set to 10 seconds.
         */
        public void setTimeout(int timeout)
        {
            this.timeoutInMilliseconds = timeout;
        }
}
