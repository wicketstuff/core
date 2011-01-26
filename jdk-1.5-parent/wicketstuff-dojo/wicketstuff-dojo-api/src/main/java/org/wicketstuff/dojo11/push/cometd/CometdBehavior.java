package org.wicketstuff.dojo11.push.cometd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.dojo11.push.IChannelListener;
import org.wicketstuff.dojo11.templates.DojoPackagedTextTemplate;

/**
 * TODO comment
 */
public class CometdBehavior extends CometdAbstractBehavior {
	private static final long serialVersionUID = 1L;

	private final IChannelListener listener;

	/**
	 * Construct.
	 * @param channelId
	 * @param listener
	 */
	public CometdBehavior(final String channelId, final IChannelListener listener) {
		super(channelId);
		this.listener = listener;
	}

	protected final IChannelListener getListener()
	{
		return listener;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getCometdInterceptorScript()
	 */
	@SuppressWarnings("unchecked")
	public String getCometdInterceptorScript() {
		final HashMap map = new HashMap();
		map.put("markupId", getComponent().getMarkupId());
		map.put("url", getCallbackUrl().toString());
		return new DojoPackagedTextTemplate(CometdBehavior.class, "CometdDefaultBehaviorTemplate.js")
						.asString(map);
	}

	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getPartialSubscriber()
	 */
	public CharSequence getPartialSubscriber() {
		return "'onEventFor"+ getComponent().getMarkupId() + "'";
	}

	protected final void respond(final AjaxRequestTarget target) {
		Iterator<?> it = ((WebRequestCycle)RequestCycle.get()).getRequest().getParameterMap().entrySet().iterator();
		final HashMap<String, String> eventAttribute = new HashMap<String, String>();
		while(it.hasNext()){
			Map.Entry<?, ?> e = (Entry< ? , ? >)it.next();
			final String key = (String) e.getKey();
			final String[] val = (String[]) e.getValue();
			eventAttribute.put(key, val[0]);
		}
		final CometdTarget cTarget = new CometdTarget(target);
		listener.onEvent(getChannelId(), eventAttribute, cTarget);
	}

}
