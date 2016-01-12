/**
 * 
 */

package org.apache.wicket.security.app;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.app.WicketApplication.MySession;
import org.apache.wicket.security.app.authentication.ApplicationLoginContext;
import org.apache.wicket.security.yahoo.YahooBBAuth;
import org.apache.wicket.security.yahoo.YahooResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page that will process the response from Yahoo.
 * 
 * @author marrink
 */
public class YahooResponsePage extends WebPage
{

	private static final Logger log = LoggerFactory.getLogger(YahooResponsePage.class);

	/**
	 * @param parameters
	 */
	public YahooResponsePage(PageParameters parameters)
	{
		super(parameters);
		try
		{
			WicketApplication app = (WicketApplication) Application.get();
			// set your application id and secret
			String appId = app.getApplicationId();
			String secret = app.getSharedSecret();

			// change to your BBAuth handler
			String uri = "/wicketsecurity/yahoo-bbauth/yahoo-response";
			/**
			 * The response querystring will include: appid=[application id]& token=[auth
			 * token]& appdata=[optional data]& ts=[request time (Unix timestamp)]&
			 * sig=[MD5(request URI including querystring with secret appended)
			 */

			// Hard coded parameters
			// String token = "";
			// String requestsig = "";
			// String ts = "";
			// String appdata = "";
			// Get request parameters
			String appdata = parameters.getString("appdata");
			String ts = parameters.getString("ts");
			String requestsig = parameters.getString("sig");
			String token = parameters.getString("token");

			YahooResponse authenticateUser =
				YahooBBAuth.authenticateUser(appId, secret, uri, appdata, ts, requestsig, token);
			// TODO identify user and log in
			((WaspSession) Session.get()).login(new ApplicationLoginContext("foo", "bar"));
			((MySession) Session.get()).setUsername("foo");
			if (!continueToOriginalDestination())
				setResponsePage(((WicketApplication) Application.get()).getHomePage());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			error(e.getMessage());
		}
		add(new FeedbackPanel("feedback"));
	}

}
