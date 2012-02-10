/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:16:26 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;
import org.wicketstuff.html5.fileapi.FileApiPage;
import org.wicketstuff.html5.geolocation.GeolocationDemo;
import org.wicketstuff.html5.markup.html.ProgressDemo;
import org.wicketstuff.html5.markup.html.form.NumberFieldDemo;
import org.wicketstuff.html5.markup.html.form.RangeTextFieldDemo;
import org.wicketstuff.html5.media.audio.AudioDemo;
import org.wicketstuff.html5.media.video.VideoDemo;

/**
 * 
 * @author Andrew Lombardi
 */
public class WicketApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init()
	{
		super.init();

		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

		mountPage("/audio", AudioDemo.class);
		mountPage("/video", VideoDemo.class);
		mountPage("/geolocation", GeolocationDemo.class);
		mountPage("/form-range", RangeTextFieldDemo.class);
		mountPage("/form-number", NumberFieldDemo.class);
		mountPage("/fileapi", FileApiPage.class);
		mountPage("/progress", ProgressDemo.class);
	}
}