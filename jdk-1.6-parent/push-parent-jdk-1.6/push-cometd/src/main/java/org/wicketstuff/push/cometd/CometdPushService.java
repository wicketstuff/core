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
package org.wicketstuff.push.cometd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.protocol.http.WebApplication;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.BayeuxServer.ChannelListener;
import org.cometd.bayeux.server.BayeuxServer.SessionListener;
import org.cometd.bayeux.server.BayeuxServer.SubscriptionListener;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushChannelDisconnectedListener;
import org.wicketstuff.push.IPushEventHandler;
import org.wicketstuff.push.IPushService;

/**
 * Cometd based implementation of {@link IPushService}.
 * <p>
 * This implementation relies on cometd for updating the page, but actually uses regular cometd
 * events, that will trigger a Wicket AJAX call to get the page actually refreshed using regular
 * Wicket AJAX mechanisms.
 * <p>
 * This mean that each time an event is published, a new connection is made to the server to get the
 * actual page update performed by the {@link IPushEventHandler}.
 * 
 * @author Xavier Hanin
 * @author Rodolfo Hansen
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class CometdPushService implements IPushService
{
	private static final class PushChannelState
	{
		protected final CometdPushChannel<?> channel;
		protected List<Object> queuedEvents = new ArrayList<Object>(2);
		protected final Object queuedEventsLock = new Object();

		protected PushChannelState(final CometdPushChannel<?> channel)
		{
			this.channel = channel;
		}
	}

	private static Logger LOG = LoggerFactory.getLogger(CometdPushService.class);

	private static final AtomicInteger channelIdx = new AtomicInteger();

	private static final Map<WebApplication, CometdPushService> INSTANCES = new WeakHashMap<WebApplication, CometdPushService>();

	public static CometdPushService get()
	{
		return get(WebApplication.get());
	}

	public static CometdPushService get(final WebApplication application)
	{
		CometdPushService service = INSTANCES.get(application);
		if (service == null)
		{
			service = new CometdPushService(application);
			INSTANCES.put(application, service);
		}
		return service;
	}

	private final ConcurrentMap<String, List<CometdPushChannel<?>>> _channelsByCometdChannelId = new ConcurrentHashMap<String, List<CometdPushChannel<?>>>();
	private final ConcurrentMap<CometdPushChannel<?>, PushChannelState> _channelStates = new ConcurrentHashMap<CometdPushChannel<?>, PushChannelState>();

	private final Set<IPushChannelDisconnectedListener> _disconnectListeners = new CopyOnWriteArraySet<IPushChannelDisconnectedListener>();

	private final WebApplication _application;

	private BayeuxServer _bayeux;

	private CometdPushService(final WebApplication application)
	{
		_application = application;

		_getBayeuxServer().addListener(new ChannelListener()
		{
			@Override
			public void channelAdded(final ServerChannel channel)
			{
				LOG.debug("Cometd channel added. channel={}", channel);
			}

			@Override
			public void channelRemoved(final String channelId)
			{
				LOG.debug("Cometd channel removed. channel={}", channelId);
			}

			@Override
			public void configureChannel(final ConfigurableServerChannel channel)
			{
				// nothing
			}
		});

		_getBayeuxServer().addListener(new SessionListener()
		{
			@Override
			public void sessionAdded(final ServerSession session)
			{
				LOG.debug("Cometd server session added. session={}", session);
			}

			@Override
			public void sessionRemoved(final ServerSession session, final boolean timedout)
			{
				LOG.debug("Cometd server session removed. session={}", session);
			}
		});

		_getBayeuxServer().addListener(new SubscriptionListener()
		{
			@Override
			public void subscribed(final ServerSession session, final ServerChannel channel)
			{
				LOG.debug("Cometd channel subscribe. session={} channel={}", session, channel);
			}

			@Override
			public void unsubscribed(final ServerSession session, final ServerChannel channel)
			{
				LOG.debug("Cometd channel unsubscribe. session={}, channel={}", session, channel);

				final List<CometdPushChannel<?>> pushChannels = _channelsByCometdChannelId.remove(channel.getId());
				if (pushChannels != null)
					for (final CometdPushChannel<?> pushChannel : pushChannels)
						_onDisconnect(pushChannel);
			}
		});
	}

	private CometdPushBehavior _findPushBehaviour(final Component component)
	{
		for (final IBehavior behavior : component.getBehaviors())
			if (behavior instanceof CometdPushBehavior)
				return (CometdPushBehavior)behavior;
		return null;
	}

	private synchronized final BayeuxServer _getBayeuxServer()
	{
		if (_bayeux == null)
			_bayeux = (BayeuxServer)_application.getServletContext().getAttribute(
				BayeuxServer.ATTRIBUTE);

		return _bayeux;
	}

	private ServerChannel _getBayeuxServerChannel(final CometdPushChannel<?> pushChannel)
	{
		return _getBayeuxServer().getChannel(pushChannel.getCometdChannelId());
	}

	private void _onConnect(final CometdPushChannel<?> pushChannel)
	{
		_channelStates.put(pushChannel, new PushChannelState(pushChannel));
		List<CometdPushChannel<?>> channels = _channelsByCometdChannelId.get(pushChannel.getCometdChannelId());
		if (channels == null)
		{
			// create a new list
			final List<CometdPushChannel<?>> newList = new ArrayList<CometdPushChannel<?>>(2);

			// put the list, a list object is returned in case it was put in the meantime
			channels = _channelsByCometdChannelId.putIfAbsent(pushChannel.getCometdChannelId(),
				newList);

			if (channels == null)
				newList.add(pushChannel);
			else
				channels.add(pushChannel);
		}
	}

	private void _onDisconnect(final CometdPushChannel<?> pushChannel)
	{
		if (_channelStates.remove(pushChannel) != null)
		{
			LOG.debug("Cometd push channel {} disconnected.", pushChannel);

			for (final IPushChannelDisconnectedListener listener : _disconnectListeners)
				try
				{
					listener.onDisconnect(pushChannel);
				}
				catch (final RuntimeException ex)
				{
					LOG.error("Failed to notify " + listener, ex);
				}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPushChannelDisconnectedListener(final IPushChannelDisconnectedListener listener)
	{
		_disconnectListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <EventType> CometdPushChannel<EventType> installPushChannel(final Component component,
		final IPushEventHandler<EventType> pushEventHandler)
	{
		return installPushChannel(component, createPushChannel((EventType) null, "push_channel_"), pushEventHandler);
	}

	@Override
	public <EventType> CometdPushChannel<EventType> installPushChannel(final Component component,
			final IPushChannel<EventType> pushChannel,
			final IPushEventHandler<EventType> pushEventHandler)
	{
		if (!(pushChannel instanceof CometdPushChannel))
		{
			throw new IllegalArgumentException("Invalid Push channel. " + pushChannel);
		}
		CometdPushChannel<EventType> channel = (CometdPushChannel<EventType>)pushChannel;
		CometdPushBehavior behavior = _findPushBehaviour(component);
		if (behavior == null)
		{
			behavior = new CometdPushBehavior();
			component.add(behavior);
		}
		channel = behavior.addPushChannel(channel, pushEventHandler);
		_onConnect(channel);
		return channel;
	}

	@Override
	public <EventType> CometdPushChannel<EventType> createPushChannel(final EventType event,
			final String key)
	{
		return new CometdPushChannel<EventType>(key + channelIdx.incrementAndGet());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(final IPushChannel<?> pushChannel)
	{
		if (pushChannel instanceof CometdPushChannel)
			return _getBayeuxServerChannel((CometdPushChannel<?>)pushChannel) != null;

		LOG.warn("Unsupported push channel type {}", pushChannel);
		return false;
	}

	@SuppressWarnings("unchecked")
	<EventType> List<EventType> pollEvents(final CometdPushChannel<EventType> pushChannel)
	{
		final PushChannelState state = _channelStates.get(pushChannel);
		if (state == null)
		{
			LOG.debug("Reconnecting push channel {}...", pushChannel);
			_onConnect(pushChannel);
			return Collections.EMPTY_LIST;
		}

		if (state.queuedEvents.size() == 0)
			return Collections.EMPTY_LIST;

		synchronized (state.queuedEventsLock)
		{
			final List<EventType> events = (List<EventType>)state.queuedEvents;
			state.queuedEvents = new ArrayList<Object>(2);
			return events;
		}
	}

	@Override
	public <EventType> void publish(final IPushChannel<EventType> pushChannel, final EventType event)
	{
		if (pushChannel instanceof CometdPushChannel)
		{
			final ServerChannel channel = _getBayeuxServerChannel((CometdPushChannel<?>)pushChannel);
			if (channel == null)
				LOG.warn("No cometd channel found for {}", pushChannel);
			else
			{
				final PushChannelState state = _channelStates.get(pushChannel);
				if (state == null)
					return;

				synchronized (state.queuedEventsLock)
				{
					state.queuedEvents.add(event);
				}

				channel.publish(null, "pollEvents", state.channel.getCometdChannelEventId());
			}
		}
		else
			LOG.warn("Unsupported push channel type {}", pushChannel);
	}

	/**
	 * Directly sends JavaScript code to the client via a cometd channel without an additional
	 * Wicket AJAX request roundtrip.
	 */
	public <EventType> void publishJavascript(final CometdPushChannel<EventType> pushChannel,
		final String javascript)
	{
		final ServerChannel channel = _getBayeuxServerChannel(pushChannel);
		if (channel == null)
			LOG.warn("No cometd channel found for {}", pushChannel);
		else
			channel.publish(null, "javascript:" + javascript, pushChannel.getCometdChannelEventId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePushChannelDisconnectedListener(
		final IPushChannelDisconnectedListener listener)
	{
		_disconnectListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uninstallPushChannel(final Component component, final IPushChannel<?> pushChannel)
	{
		if (pushChannel instanceof CometdPushChannel)
		{
			final CometdPushBehavior behavior = _findPushBehaviour(component);
			if (behavior == null)
				return;
			if (behavior.removePushChannel(pushChannel) == 0)
				component.remove(behavior);
		}
		else
			LOG.warn("Unsupported push channel type {}", pushChannel);
	}
}
