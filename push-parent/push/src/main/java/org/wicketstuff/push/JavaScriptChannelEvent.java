package org.wicketstuff.push;

/**
 * Usually on sending an event to a channel, the ChannelEvent invokes a javascript method on the
 * client which then starts an Ajax-request. In order to save the additional round-trip, this
 * class has been made. It executes the javascript-code returned by  the {@link #getJavaScript()}
 * method.
 * 
 * @author Michael Sparer (msparer)
 */
public abstract class JavaScriptChannelEvent extends ChannelEvent {
	
	/** Setting the event's name to this key makes the client side execute its value */
	public final static String KEY = "evalscript";
	
	/**
	 * @param channel
	 * 			the channel name
	 */
	public JavaScriptChannelEvent(String channel) {
		super(channel);
		addData(KEY, getJavaScript());		
	}
	
	/**	 
	 * @return
	 * 		javascript code that gets evaluated on the client without making a roundtrip
	 */
	protected abstract String getJavaScript();

}
