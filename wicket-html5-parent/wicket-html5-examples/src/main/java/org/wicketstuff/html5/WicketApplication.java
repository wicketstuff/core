/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:16:26 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;
import org.wicketstuff.html5.eventsource.EventSourceDemo;
import org.wicketstuff.html5.eventsource.EventSourceResourceReference;
import org.wicketstuff.html5.fileapi.FileApiPage;
import org.wicketstuff.html5.geolocation.GeolocationDemo;
import org.wicketstuff.html5.image.InlineImagePage;
import org.wicketstuff.html5.markup.html.ProgressDemo;
import org.wicketstuff.html5.markup.html.form.NumberFieldDemo;
import org.wicketstuff.html5.markup.html.form.RangeTextFieldDemo;
import org.wicketstuff.html5.media.webrtc.WebRTCDemo;
import org.wicketstuff.html5.media.webvtt.WebVttDemo;
import org.wicketstuff.html5.shape.ShapeCircleExamplePage;

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

		mountPage("/webrtc", WebRTCDemo.class);
		mountPage("/webvtt", WebVttDemo.class);
		mountPage("/geolocation", GeolocationDemo.class);
		mountPage("/form-range", RangeTextFieldDemo.class);
		mountPage("/form-number", NumberFieldDemo.class);
		mountPage("/fileapi", FileApiPage.class);
		mountPage("/progress", ProgressDemo.class);

		mountPage("/eventSource", EventSourceDemo.class);
		mountPage("/shape", ShapeCircleExamplePage.class);
		mountPage("/inlineimage", InlineImagePage.class);
		mountResource("/eventSourceResource", new EventSourceResourceReference());

		IPackageResourceGuard packageResourceGuard = getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard)
		{
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard)packageResourceGuard;
			guard.addPattern("+*.json");
		}
	}
}