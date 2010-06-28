package org.wicketstuff.push.examples.pages;

import org.apache.wicket.PageParameters;
import org.cometd.RemoveListener;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.cometd.CometdService;

public class WicketCometdChat extends WicketAbstractChat {
	private static final long serialVersionUID = 1L;

	public WicketCometdChat(final PageParameters parameters) {
		super(parameters);
		final CometdService s = (CometdService) getCometdService();
		s.addChannelRemoveListener("chat", new RemoveListener () {

      public void removed(final String clientId, final boolean timeout) {
        final ChannelEvent event = new ChannelEvent("chat");
        event.addData("message", clientId + "just left");
        getChannelService().publish(event);
      }
		});
	}

	@Override
	protected IChannelService getChannelService() {
		return getCometdService();
	}
}
