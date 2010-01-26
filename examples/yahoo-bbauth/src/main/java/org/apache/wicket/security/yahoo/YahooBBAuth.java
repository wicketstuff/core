package org.apache.wicket.security.yahoo;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.wicket.security.authentication.LoginException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Yahoo! Web Services Example: Browser Based Authentication (BBAuth) This example shows
 * how to generate the URL used for Yahoo! Brower Based Authentication. This example
 * should be run before YahooBBAuthRequest to obtain the auth token via the callback URL
 * specified when you obtained your application ID and secret. If you do not have an
 * Application ID and Secret, go to the Yahoo! BBAuth Registration page:
 * https://developer.yahoo.com/wsregapp/index.php
 * 
 * @author Daniel Jones www.danieljones.org Copyright 2007
 * @author marrink, slightly modified
 */
public class YahooBBAuth
{

	/**
	 * For standalone use.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		/**
		 * Use the appId and secret provided when you registered your application
		 * https://developer.yahoo.com/wsregapp/index.php
		 */
		String appId = "";
		String secret = "";
		String authURL;
		try
		{
			authURL = generateYahooAuthenticationUrl(appId, secret);
			System.out.println(authURL);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Use the appId and secret provided when you registered your application
	 * https://developer.yahoo.com/wsregapp/index.php
	 * 
	 * @param appId
	 * @param secret
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateYahooAuthenticationUrl(String appId, String secret)
			throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		// Get the current time. Needed to sign the request.
		long time = System.currentTimeMillis() / 1000;

		/**
		 * Generate the Yahoo! authentication URL that you will send users to to verify
		 * their Yahoo! identity More information can be found here:
		 * http://developer.yahoo.com/auth/user.html
		 */
		String appData = "foobar";
		String authWS = "/WSLogin/V1/wslogin";
		String sig =
			authWS + "?appid=" + encode(appId) + "&appdata=" + encode(appData) + "&ts=" + time
				+ secret;
		String signature = MD5(sig);
		String authURL =
			"https://api.login.yahoo.com" + authWS + "?appid=" + appId + "&appdata=" + appData
				+ "&ts=" + time + "&sig=" + signature;
		/**
		 * The end user will browse to this URL and allow access to your web application.
		 * After authenticating, the user will be forwarded to the callback URL specified
		 * when you obtained your application ID and secret.
		 */
		return authURL;
	}

	/**
	 * This method returns the MD5 hash of a text string
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static String MD5(String text) throws NoSuchAlgorithmException
	{
		String md5Text = "";
		MessageDigest digest = MessageDigest.getInstance("MD5");
		md5Text = new BigInteger(1, digest.digest((text).getBytes())).toString(16);

		if (md5Text.length() == 31)
		{
			md5Text = "0" + md5Text;
		}
		return md5Text;
	}

	/**
	 * URL encode a text string
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String text) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(text, "UTF-8");
	}

	/**
	 * Returns a session id and cookie if the user was succesfully authenticated. The web
	 * service session id (wssid) and Yahoo! cookie can then be used for calls to the SOAP
	 * or JSON-RPC endpoints. http://developer.yahoo.com/mail/docs/html/index.html
	 */
	public static YahooResponse authenticateUser(String appId, String secret, String uri,
			String appdata, String ts, String requestsig, String token) throws LoginException
	{
		try
		{
			String calcsig =
				uri + "?appid=" + appId + "&token=" + token + "&appdata=" + appdata + "&ts=" + ts
					+ secret;
			// log.info(calcsig);
			// MessageDigest digest = MessageDigest.getInstance("MD5");
			// calcsig = new BigInteger(1,
			// digest.digest((calcsig).getBytes())).toString(16);
			calcsig = MD5(calcsig);
			// Verify that the signature sent by Yahoo! matches the calculated
			// signture
			if (!calcsig.equals(requestsig))
				throw new YahooLoginException("Signature mismatch:" + requestsig + " vs " + calcsig);

			// Get the current time. Needed to sign the request.
			long time = System.currentTimeMillis() / 1000;
			long requesttime = Long.parseLong(ts);
			long clockSkew = Math.abs(time - requesttime);

			// Make sure the server time is within 10 minutes (600 seconds) of
			// Yahoo!'s servers
			if (clockSkew >= 600)
				throw new YahooLoginException("Invalid timestamp - clockSkew is " + clockSkew
					+ " seconds, current time = " + time + ", ts =" + requesttime);

			/**
			 * Generate the portion of the URL that's used for signing. More information
			 * on BBAuth can be found here: http://developer.yahoo.com/auth/
			 */
			String authWS = "/WSLogin/V1/wspwtoken_login";
			String sig =
				authWS + "?appid=" + encode(appId) + "&token=" + encode(token) + "&ts=" + time
					+ secret;
			// String signature = new BigInteger(1,
			// digest.digest((sig).getBytes())).toString(16);
			String signature = MD5(sig);
			String authURL =
				"https://api.login.yahoo.com" + authWS + "?appid=" + appId + "&token=" + token
					+ "&ts=" + time + "&sig=" + signature;
			// log.info(authURL);
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
						// log.info(h.getName() + "=" + h.getValue());
					}
				}
				throw new YahooLoginException("No response from Yahoo.");
			}

			/*
			 * Retrieve the XML response to the auth request and get the wssid and cookie
			 * values.
			 */
			Document xmlresponse =
				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rstream);
			StringWriter writer = new StringWriter(5000);
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xmlresponse),
				new StreamResult(writer));
			// log.info("\n" + writer.toString());

			// Check if token is in the response
			NodeList wssidResponse = xmlresponse.getElementsByTagName("WSSID");
			NodeList cookieResponse = xmlresponse.getElementsByTagName("Cookie");
			NodeList timeoutResponse = xmlresponse.getElementsByTagName("Timeout");
			Node wssidNode = wssidResponse.item(0);
			Node cookieNode = cookieResponse.item(0);
			Node timeoutNode = timeoutResponse.item(0);
			if (wssidNode != null)
			{
				YahooResponse response =
					new YahooResponse(wssidNode.getFirstChild().getNodeValue(), Long.valueOf(
						timeoutNode.getFirstChild().getNodeValue()).longValue(), cookieNode
						.getFirstChild().getNodeValue());
				return response;

			}
			else
			{
				/*
				 * Print the response error code and message <?xml version="1.0"
				 * encoding="UTF-8" standalone="no"?> <wspwtoken_login_response> <Error>
				 * <ErrorCode>3000</ErrorCode> <ErrorDescription>Invalid (missing)
				 * appid</ErrorDescription> </Error> </wspwtoken_login_response>
				 */
				String code =
					xmlresponse.getElementsByTagName("ErrorCode").item(0).getFirstChild()
						.getNodeValue();
				String msg =
					xmlresponse.getElementsByTagName("ErrorDescription").item(0).getFirstChild()
						.getNodeValue();
				throw new YahooLoginException(code, msg);
			}
		}
		catch (Exception e)
		{
			throw new YahooLoginException("Authentication failed.", e);
		}
	}
}
