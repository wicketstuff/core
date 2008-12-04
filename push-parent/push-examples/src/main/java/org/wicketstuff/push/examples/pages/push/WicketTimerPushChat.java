package org.wicketstuff.push.examples.pages.push;

import org.apache.wicket.PageParameters;
import org.wicketstuff.push.IPushService;

public class WicketTimerPushChat extends WicketAbstractPushChat {
	public WicketTimerPushChat(PageParameters parameters) {
		super(parameters);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected IPushService getPushService() {
		return getTimerPushService();
	}

}
