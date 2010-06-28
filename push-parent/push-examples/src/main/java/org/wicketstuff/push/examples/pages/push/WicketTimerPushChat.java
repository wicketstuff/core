package org.wicketstuff.push.examples.pages.push;

import org.apache.wicket.PageParameters;
import org.wicketstuff.push.IPushService;

public class WicketTimerPushChat extends WicketAbstractPushChat {

	private static final long serialVersionUID = 1L;
	
	public WicketTimerPushChat(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected IPushService getPushService() {
		return getTimerPushService();
	}

}
