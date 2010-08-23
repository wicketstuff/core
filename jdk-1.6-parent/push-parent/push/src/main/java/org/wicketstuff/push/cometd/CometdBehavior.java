package org.wicketstuff.push.cometd;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.push.IChannelListener;

public class CometdBehavior extends CometdAbstractBehavior {
	private static final long serialVersionUID = 1L;

	private static final PackagedTextTemplate DEFAULT_BEHAVIOR_TEMPLATE =
	  new PackagedTextTemplate(CometdBehavior.class, "DefaultBehavior.js");


	private final IChannelListener listener;


	public CometdBehavior(final String channelId, final IChannelListener listener) {
		super(channelId);
		this.listener = listener;
	}

	@Override
	public final String getCometdInterceptorScript() {

		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("behaviorMarkupId", getBehaviorMarkupId());
		map.put("url", getCallbackUrl().toString());

		return DEFAULT_BEHAVIOR_TEMPLATE.asString(map);
	}

	@Override
	public final CharSequence getPartialSubscriber() {
		return "onEventFor" + getBehaviorMarkupId();
	}

	@Override
	protected final void respond(final AjaxRequestTarget target) {

		final IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
		final Map<String, String> eventAttribute = new HashMap<String, String>();

		for (final String param : params.getParameterNames()) {
			eventAttribute.put(param, params.getParameterValue(param).toString());
		}

		final CometdTarget cTarget = new CometdTarget(target);
		listener.onEvent(getChannelId(), eventAttribute, cTarget);

	}

}
