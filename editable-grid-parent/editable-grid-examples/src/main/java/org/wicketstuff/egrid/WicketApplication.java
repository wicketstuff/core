package org.wicketstuff.egrid;

import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.egrid.page.HomePage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see org.wicketstuff.egrid.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        getCspSettings().blocking().disabled();
        getMarkupSettings().setStripWicketTags(true);
    }
}
