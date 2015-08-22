package org.apache.wicket.security.app;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.security.yahoo.YahooBBAuth;

/**
 * Login Page. Uses Yahoo BBAuth.
 * 
 * @author marrink
 */
public class LoginPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public LoginPage()
	{
		try
		{
			WicketApplication app = (WicketApplication) Application.get();
			String url =
				YahooBBAuth.generateYahooAuthenticationUrl(app.getApplicationId(), app
					.getSharedSecret());
			add(new WebMarkupContainer("yahooUrl").add(new SimpleAttributeModifier("content",
				"5; url=" + url)));
			add(new WebMarkupContainer("yahooUrl2").add(new SimpleAttributeModifier("href", url)));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new WicketRuntimeException(e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new WicketRuntimeException(e);
		}
	}
}
