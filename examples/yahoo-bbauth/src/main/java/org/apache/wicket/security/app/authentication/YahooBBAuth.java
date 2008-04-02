
package org.apache.wicket.security.app.authentication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Yahoo! Web Services Example: Browser Based Authentication (BBAuth) This example shows how to generate the URL used
 * for Yahoo! Brower Based Authentication. This example should be run before YahooBBAuthRequest to obtain the auth token
 * via the callback URL specified when you obtained your application ID and secret. If you do not have an Application ID
 * and Secret, go to the Yahoo! BBAuth Registration page: https://developer.yahoo.com/wsregapp/index.php
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
	public static String generateYahooAuthenticationUrl(String appId, String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		// Get the current time. Needed to sign the request.
		long time = System.currentTimeMillis() / 1000;

		/**
		 * Generate the Yahoo! authentication URL that you will send users to to verify their Yahoo! identity More
		 * information can be found here: http://developer.yahoo.com/auth/user.html
		 */
		String appData = "foobar";
		String authWS = "/WSLogin/V1/wslogin";
		String sig = authWS + "?appid=" + encode(appId) + "&appdata=" + encode(appData) + "&ts=" + time + secret;
		String signature = MD5(sig);
		String authURL = "https://api.login.yahoo.com" + authWS + "?appid=" + appId + "&appdata=" + appData + "&ts="
				+ time + "&sig=" + signature;
		/**
		 * The end user will browse to this URL and allow access to your web application. After authenticating, the user
		 * will be forwarded to the callback URL specified when you obtained your application ID and secret.
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

		if(md5Text.length() == 31)
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
}
