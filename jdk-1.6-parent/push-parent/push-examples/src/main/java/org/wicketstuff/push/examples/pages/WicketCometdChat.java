package org.wicketstuff.push.examples.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.bayeux.server.ServerSession.RemoveListener;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.cometd.CometdService;

public class WicketCometdChat extends WicketAbstractChat {
	private static final long serialVersionUID = 1L;

	public WicketCometdChat(final PageParameters parameters) {
		super(parameters);
		final CometdService s = (CometdService) getCometdService();
		s.addChannelRemoveListener("chat", new RemoveListener () {

	      @Override
        public void removed(final ServerSession client, final boolean timeout) {
	        final ChannelEvent event = new ChannelEvent("chat");
	        event.addData("message", client.getId() + " just left");
	        getChannelService().publish(event);
	      }
		});
	}

	@Override
	protected IChannelService getChannelService() {
		return getCometdService();
	}
}
