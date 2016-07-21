package org.wicketstuff.gae;

import org.apache.wicket.pageStore.memory.IDataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.MemorySizeEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class GaeWicketApplication extends WebApplication implements GaeApplication
{
	/**
	 * Constructor
	 */
	public GaeWicketApplication()
	{
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * Setup custom eviction strategy for this application
	 */
	public IDataStoreEvictionStrategy getEvictionStrategy()
	{
		return new MemorySizeEvictionStrategy(Bytes.megabytes(2));
	}

}
