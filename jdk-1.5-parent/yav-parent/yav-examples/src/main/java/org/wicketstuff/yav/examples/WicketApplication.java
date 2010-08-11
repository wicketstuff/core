package org.wicketstuff.yav.examples;

import org.apache.wicket.protocol.http.WebApplication;


/**
 * @author Zenika
 */
public class WicketApplication extends WebApplication {
	
	/**
	 * Constructor
	 */
	public WicketApplication() {
	}

	protected void init() {
		super.init();

		this.getRequestLoggerSettings().setRequestLoggerEnabled(true);
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@SuppressWarnings("unchecked")
	public Class getHomePage() {
		return TestPage.class;
	}
	
}
