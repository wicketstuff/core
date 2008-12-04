package org.wicketstuff.push.cometd;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.dojo.DojoPackagedTextTemplate;

public class CometdBehavior extends CometdAbstractBehavior {
	private static final long serialVersionUID = 1L;

	private final IChannelListener listener;

	public CometdBehavior(final String channelId, final IChannelListener listener) {
		super(channelId);
		this.listener = listener;
	}

	@Override
  public final String getCometdInterceptorScript() {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("markupId", getComponent().getMarkupId());
		map.put("url", getCallbackUrl().toString());
		return new DojoPackagedTextTemplate(CometdBehavior.class, "CometdDefaultBehaviorTemplate.js")
						.asString(map);
	}

	@Override
  public final CharSequence getPartialSubscriber() {
		return "'onEventFor"+ getComponent().getMarkupId() + "'";
	}

	@Override
  protected final void respond(final AjaxRequestTarget target) {
		final Map<String, String[]> map = ((WebRequestCycle) RequestCycle.get())
        .getRequest().getParameterMap();
		final Map<String, String> eventAttribute = new HashMap<String, String>();

		for (final Map.Entry<String, String[]> entry : map.entrySet()) {
			eventAttribute.put(entry.getKey(), entry.getValue()[0]);
		}

		final CometdTarget cTarget = new CometdTarget(target);
		listener.onEvent(getChannelId(), eventAttribute, cTarget);
	}

}
