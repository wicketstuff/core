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
	 * 
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
		super.init();


		mountPage("/e1", Example1.class);
		mountPage("/e2", Example2.class);
		mountPage("/e3", Example3.class);
		mountPage("/e4", Example4.class);
		mountPage("/e5", Example5.class);
		mountPage("/e6", Example6.class);

	}

}
