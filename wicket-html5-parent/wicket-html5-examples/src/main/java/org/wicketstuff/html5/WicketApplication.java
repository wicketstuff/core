/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:16:26 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.html5.geolocation.GeolocationDemo;
import org.wicketstuff.html5.media.audio.AudioDemo;
import org.wicketstuff.html5.media.video.VideoDemo;

/**
 *
 * @author Andrew Lombardi
 */
public class WicketApplication extends WebApplication {

    /**
     * Constructor
     */
    public WicketApplication() {

    }

    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    protected void init() {
        super.init();

        mountBookmarkablePage("/audio", AudioDemo.class);
        mountBookmarkablePage("/video", VideoDemo.class);
        mountBookmarkablePage("/geolocation", GeolocationDemo.class);
    }
}