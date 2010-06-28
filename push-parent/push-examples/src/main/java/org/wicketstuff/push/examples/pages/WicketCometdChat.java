package org.wicketstuff.push.examples.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.cometd.CometdService;

import dojox.cometd.RemoveListener;

public class WicketCometdChat extends WicketAbstractChat {
	private static final long serialVersionUID = 1L;

	public WicketCometdChat(final PageParameters parameters) {
		super(parameters);
		final CometdService s = (CometdService) getCometdService();
		s.addChannelRemoveListener("chat", new RemoveListener () {

      public void removed(final String clientId, final boolean timeout) {
        System.out.println("Chat Removed");
      }
		});
	}

	@Override
	protected IChannelService getChannelService() {
		return getCometdService();
	}
}
