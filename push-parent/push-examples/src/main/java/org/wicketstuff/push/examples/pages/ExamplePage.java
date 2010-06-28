package org.wicketstuff.push.examples.pages;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.examples.application.ExampleApplication;

/** 
 * Base class for channel based communication examples.
 * 
 * Used for easy access to channel and push services 
 * implementations
 * 
 * @author Xavier Hanin
 */
public class ExamplePage extends WebPage {
	public ExampleApplication getExampleApplication() {
		return (ExampleApplication) ExampleApplication.get();
	}
	
	public IChannelService getCometdService() {
		return getExampleApplication().getCometdService();
	}

	public IChannelService getTimerChannelService() {
		return getExampleApplication().getTimerChannelService();
	}
	
	public IPushService getTimerPushService() {
		return getExampleApplication().getTimerPushService();
	}
	
	
}
