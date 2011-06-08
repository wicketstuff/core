package org.wicketstuff.pageserializer.kryo;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.pageserializer.kryo.KryoSerializer;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see org.wicketstuff.pageserializer.kryo.mycompany.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
	    super.init();

		getFrameworkSettings().setSerializer(new KryoSerializer());
	}
}
