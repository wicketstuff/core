/**
 * 
 */

package org.apache.wicket.security.app;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.app.WicketApplication.MySession;
import org.apache.wicket.security.app.authentication.ApplicationLoginContext;
import org.apache.wicket.security.app.authentication.YahooBBAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
			WicketApplication app = (WicketApplication)Application.get();
			// set your application id and secret
			String appId = app.getApplicationId();
			String secret = app.getSharedSecret();

			// change to your BBAuth handler
			String uri = "/wicketsecurity/yahoo-bbauth/yahoo-response";
			/**
			 * The response querystring will include: appid=[application id]&
			 * token=[auth token]& appdata=[optional data]& ts=[request time
			 * (Unix timestamp)]& sig=[MD5(request URI including querystring
			 * with secret appended)
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

			String calcsig = uri + "?appid=" + appId + "&token=" + token + "&appdata=" + appdata
					+ "&ts=" + ts + secret;
			log.info(calcsig);
			// MessageDigest digest = MessageDigest.getInstance("MD5");
			// calcsig = new BigInteger(1,
			// digest.digest((calcsig).getBytes())).toString(16);
			calcsig = YahooBBAuth.MD5(calcsig);
			// Verify that the signature sent by Yahoo! matches the calculated
			// signture
			if (!calcsig.equals(requestsig))
			{
				error("Signature mismatch:" + requestsig + " vs " + calcsig);
				return;
			}

			// Get the current time. Needed to sign the request.
			long time = System.currentTimeMillis() / 1000;
			long requesttime = Long.parseLong(ts);
			long clockSkew = Math.abs(time - requesttime);

			// Make sure the server time is within 10 minutes (600 seconds) of
			// Yahoo!'s servers
			if (clockSkew >= 600)
			{
				error("Invalid timestamp - clockSkew is " + clockSkew + " seconds, current time = "
						+ time + ", ts =" + requesttime);
				return;
			}

			/**
			 * Generate the portion of the URL that's used for signing. More
			 * information on BBAuth can be found here:
			 * http://developer.yahoo.com/auth/
			 */
			String authWS = "/WSLogin/V1/wspwtoken_login";
			String sig = authWS + "?appid=" + YahooBBAuth.encode(appId) + "&token="
					+ YahooBBAuth.encode(token) + "&ts=" + time + secret;
			// String signature = new BigInteger(1,
			// digest.digest((sig).getBytes())).toString(16);
			String signature = YahooBBAuth.MD5(sig);
			String authURL = "https://api.login.yahoo.com" + authWS + "?appid=" + appId + "&token="
					+ token + "&ts=" + time + "&sig=" + signature;
			log.info(authURL);
			// out.println(authURL);
			// out.println("<br>");

			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(authURL);
			InputStream rstream = null;
			client.executeMethod(method);
			// Get the response body
			rstream = method.getResponseBodyAsStream();
			if (rstream == null)
			{
				Header[] headers = method.getResponseHeaders();
				if (headers != null)
				{
					for (int i = 0; i < headers.length; i++)
					{
						Header h = headers[i];
						log.info(h.getName() + "=" + h.getValue());
					}
				}
				throw new WicketRuntimeException("No response from Yahoo.");
			}

			/**
			 * Retrieve the XML response to the auth request and get the wssid
			 * and cookie values.
			 */
			Document xmlresponse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					rstream);
			StringWriter writer = new StringWriter(5000);
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xmlresponse),
					new StreamResult(writer));
			log.info("\n" + writer.toString());

			String wssid = null;
			String cookie = null;
			String timeout = null;

			// Check if token is in the response
			NodeList wssidResponse = xmlresponse.getElementsByTagName("WSSID");
			NodeList cookieResponse = xmlresponse.getElementsByTagName("Cookie");
			NodeList timeoutResponse = xmlresponse.getElementsByTagName("Timeout");
			Node wssidNode = wssidResponse.item(0);
			Node cookieNode = cookieResponse.item(0);
			Node timeoutNode = timeoutResponse.item(0);
			if (wssidNode != null)
			{
				// out.println("BBauth authentication Successful");
				// out.println("<br>");
				// wssid = wssidNode.getTextContent();
				wssid = wssidNode.getFirstChild().getNodeValue();
				// cookie = cookieNode.getTextContent();
				cookie = cookieNode.getFirstChild().getNodeValue();
				// timeout = timeoutNode.getTextContent();
				timeout = timeoutNode.getFirstChild().getNodeValue();
				// out.println("wssid = " + wssid);
				// out.println("<br>");
				// out.println("cookie = " + cookie);
				// out.println("<br>");
				// out.println("timeout = " + timeout);
				((WaspSession)Session.get()).login(new ApplicationLoginContext("foo", "bar"));
				((MySession)Session.get()).setUsername("foo");
				if (!continueToOriginalDestination())
					setResponsePage(((WicketApplication)Application.get()).getHomePage());
			}
			else
			{
				/**
				 * Print the response error code and message <?xml version="1.0"
				 * encoding="UTF-8" standalone="no"?> <wspwtoken_login_response>
				 * <Error> <ErrorCode>3000</ErrorCode>
				 * <ErrorDescription>Invalid (missing) appid</ErrorDescription>
				 * </Error> </wspwtoken_login_response>
				 */
				String code = xmlresponse.getElementsByTagName("ErrorCode").item(0).getFirstChild()
						.getNodeValue();
				String msg = xmlresponse.getElementsByTagName("ErrorDescription").item(0)
						.getFirstChild().getNodeValue();
				String error = "BBAuth request failed with errorcode: " + code + ", " + msg;
				log.error(error);
				throw new WicketRuntimeException(error);
			}

			/**
			 * The web service session id (wssid) and Yahoo! cookie can now be
			 * used for calls to the SOAP or JSON-RPC endpoints.
			 * http://developer.yahoo.com/mail/docs/html/index.html
			 */
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			error(e.getMessage());
		}
		add(new FeedbackPanel("feedback"));
	}

}
