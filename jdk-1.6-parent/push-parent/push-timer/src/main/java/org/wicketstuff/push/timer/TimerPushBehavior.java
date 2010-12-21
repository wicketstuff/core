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
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushEventHandler;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TimerPushBehavior extends AbstractAjaxTimerBehavior
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(TimerPushBehavior.class);

	private final Map<TimerPushChannel, IPushEventHandler> handlers =
		new HashMap<TimerPushChannel, IPushEventHandler>(2);

	TimerPushBehavior(final Duration pollingInterval)
	{
		super(pollingInterval);
	}

	<EventType> TimerPushChannel<EventType> addPushChannel(
			final IPushEventHandler<EventType> pushEventHandler, final Duration pollingInterval)
	{
		return addPushChannel(pushEventHandler, new TimerPushChannel<EventType>(pollingInterval));
	}

	<EventType> TimerPushChannel<EventType> addPushChannel(
			final IPushEventHandler<EventType> pushEventHandler,
			final TimerPushChannel<EventType> channel)
	{
		final Duration pollingInterval = channel.getPollingInterval();
		if (pollingInterval.lessThan(getUpdateInterval()))
		{
			setUpdateInterval(pollingInterval);
		}

		handlers.put(channel, pushEventHandler);
		return channel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onTimer(final AjaxRequestTarget target)
	{
		final TimerPushService pushService = TimerPushService
				.get(target.getPage().getApplication());

		final WebRequest request = (WebRequest)RequestCycle.get().getRequest();

		if (request.getRequestParameters().getParameterValue("unload") != null)
		{
			// if the page is unloaded notify the pushService to disconnect all
			// push channels
			for (final TimerPushChannel<?> channel : handlers.keySet())
			{
				pushService.onDisconnect(channel);
			}
		}
		else
		{
			// retrieve all collected events and process them
			for (final Entry<TimerPushChannel, IPushEventHandler> entry : handlers.entrySet())
			{
				for (final Object event : pushService.pollEvents(entry.getKey()))
				{
					try
					{
						entry.getValue().onEvent(target, event);
					}
					catch (final RuntimeException ex)
					{
						LOG.error("Failed while processing event", ex);
					}
				}
			}
		}
	}

	int removePushChannel(final IPushChannel<?> channel)
	{
		handlers.remove(channel);

		// adjust the polling interval based on the fastest remaining channel
		Duration newPollingInterval = Duration.MAXIMUM;
		for (final TimerPushChannel chan : handlers.keySet())
		{
			if (chan.getPollingInterval().lessThan(newPollingInterval))
			{
				newPollingInterval = chan.getPollingInterval();
			}
		}
		setUpdateInterval(newPollingInterval);

		return handlers.size();
	}

	@Override
	public void renderHead(final Component c, final IHeaderResponse response)
	{
		// install an onunload handler
		response.renderJavaScript("history.navigationMode = 'compatible';",
				"Opera onunload support");
		response.renderOnEventJavaScript("window", "unload", "wicketAjaxGet('"
				+ getCallbackUrl().toString() + "&unload=1', function() { }, function() { });");
	}
}
