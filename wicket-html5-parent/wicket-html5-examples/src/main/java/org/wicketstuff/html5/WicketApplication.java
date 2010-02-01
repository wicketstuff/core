/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:16:26 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrew Lombardi
 */
public class WicketApplication extends WebApplication {

    private static final Logger logger = LoggerFactory.getLogger(WicketApplication.class);

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
    }
}