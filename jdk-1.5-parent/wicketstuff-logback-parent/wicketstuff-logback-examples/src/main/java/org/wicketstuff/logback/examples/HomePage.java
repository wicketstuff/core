package org.wicketstuff.logback.examples;

import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example homepage. Logs every creation.
 * 
 * @author akiraly
 */
public class HomePage extends WebPage
{
	private static final long serialVersionUID = 2696517689763106924L;

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

	public HomePage()
	{
		LOGGER.info("Logging is good - said the lumberjack.");
	}
}
