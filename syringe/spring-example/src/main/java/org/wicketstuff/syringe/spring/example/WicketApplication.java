package org.wicketstuff.syringe.spring.example;

import org.wicketstuff.plugin.WicketPluginApplication;
import org.wicketstuff.syringe.spring.SpringSyringePlugin;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @since 1.4
 */
public class WicketApplication extends WicketPluginApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
	}

    @Override
    protected void init()
    {
        getPluginRegistry().registerPlugin(new SpringSyringePlugin(this, WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())));
    }

    /**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

}
