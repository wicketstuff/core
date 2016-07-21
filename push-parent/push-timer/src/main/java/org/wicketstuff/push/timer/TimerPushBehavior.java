/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.push.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnEventHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.push.IPushEventContext;
import org.wicketstuff.push.IPushEventHandler;
import org.wicketstuff.push.IPushNode;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TimerPushBehavior extends AbstractAjaxTimerBehavior
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(TimerPushBehavior.class);

	private final Map<TimerPushNode, IPushEventHandler> handlers = new HashMap<TimerPushNode, IPushEventHandler>(
		2);

	TimerPushBehavior(final Duration pollingInterval)
	{
		super(pollingInterval);
	}

	<EventType> TimerPushNode<EventType> addNode(
		final IPushEventHandler<EventType> pushEventHandler, final Duration pollingInterval)
	{
		if (pollingInterval.lessThan(getUpdateInterval()))
			setUpdateInterval(pollingInterval);

		final TimerPushNode<EventType> node = new TimerPushNode<EventType>(pollingInterval);
		handlers.put(node, pushEventHandler);
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onTimer(final AjaxRequestTarget target)
	{
		if (isStopped())
		{
			getComponent().remove(this);
			return;
		}

		final TimerPushService pushService = TimerPushService.get(target.getPage().getApplication());

		final WebRequest request = (WebRequest)RequestCycle.get().getRequest();

		if (!request.getRequestParameters().getParameterValue("unload").isNull())
			// if the page is unloaded notify the pushService to disconnect all push nodes
			for (final TimerPushNode<?> node : handlers.keySet())
				pushService.onDisconnect(node);

		else
			// retrieve all collected events and process them
			for (final Entry<TimerPushNode, IPushEventHandler> entry : handlers.entrySet())
			{
				final TimerPushNode node = entry.getKey();
				for (final IPushEventContext<?> ctx : (List<IPushEventContext<?>>)pushService.pollEvents(node))
					try
					{
						entry.getValue().onEvent(target, ctx.getEvent(), node, ctx);
					}
					catch (final RuntimeException ex)
					{
						LOG.error("Failed while processing event", ex);
					}
			}
	}

	/**
	 * @return the number of handlers after the removal
	 */
	int removeNode(final IPushNode<?> node)
	{
		handlers.remove(node);

		// adjust the polling interval based on the fastest remaining node
		Duration newPollingInterval = Duration.MAXIMUM;
		for (final TimerPushNode n : handlers.keySet())
			if (n.getPollingInterval().lessThan(newPollingInterval))
				newPollingInterval = n.getPollingInterval();
		setUpdateInterval(newPollingInterval);

		return handlers.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response)
	{
		super.renderHead(c, response);

		if (!isStopped())
		{
			// install an on-unload handler
	        response.render(JavaScriptHeaderItem.forScript("history.navigationMode = 'compatible';", "Opera on-unload support"));
	        response.render(OnEventHeaderItem.forScript("window", "unload", "Wicket.Ajax.get({'u': '" + getCallbackUrl().toString() + "&unload=1'});"));
		}
	}
}
