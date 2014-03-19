package org.wicketstuff.dojo11.push.cometd;

import java.util.Map;

import org.wicketstuff.dojo11.push.ChannelEvent;


/**
 * Default abstract event for wicket.<br/>
 * This event will ping all component on client side to make them do
 * a XmlHttpRequest<br/>
 *
 * This Event can be seen as a proxy, it will request the client side
 * to make a request to make it update
 *
 * @author Vincent Demay
 *
 */
public class CometdEvent extends ChannelEvent{


	/**
	 * 
	 * Construct.
	 * @param channel
	 */
	public CometdEvent(final String channel) {
		super(channel);
	}

	/**
	 * 
	 * @see org.wicketstuff.dojo11.push.ChannelEvent#getData()
	 */
	public final Map<String, Object> getData() {
		addData("proxy", "true");
		return super.getData();
	}

}
