package org.wicketstuff.push.examples.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.push.IChannelService;

public class WicketTimerChat extends WicketAbstractChat {
	private static final long serialVersionUID = 1L;

	public WicketTimerChat(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected IChannelService getChannelService() {
		return getTimerChannelService();
	}
}
