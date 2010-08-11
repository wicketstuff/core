/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class JSR303ExampleApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return IndexPage.class;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.wicket.protocol.http.WebApplication#init()
     */
    @Override
    protected void init()
    {
        super.init();

        mountBookmarkablePage("/e1", Example1.class);
        mountBookmarkablePage("/e2", Example2.class);
        mountBookmarkablePage("/e3", Example3.class);
        mountBookmarkablePage("/e4", Example4.class);
        mountBookmarkablePage("/e5", Example5.class);
        mountBookmarkablePage("/e6", Example6.class);

    }

}
