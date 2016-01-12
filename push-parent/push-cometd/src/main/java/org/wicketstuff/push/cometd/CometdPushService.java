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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
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
import org.wicketstuff.push.AbstractPushService;
import org.wicketstuff.push.AbstractPushServiceRef;
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushEventHandler;
import org.wicketstuff.push.IPushNode;
import org.wicketstuff.push.IPushNodeDisconnectedListener;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.IPushServiceRef;
import org.wicketstuff.push.PushChannel;

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
public class CometdPushService extends AbstractPushService
{
	private static final class PushNodeState<EventType>
	{
		protected final CometdPushNode<EventType> node;

		protected List<CometdPushEventContext<EventType>> queuedEvents = new ArrayList<CometdPushEventContext<EventType>>(
			2);
		protected final Object queuedEventsLock = new Object();

		protected PushNodeState(final CometdPushNode<EventType> node)
		{
			this.node = node;
		}
	}

	private static Logger LOG = LoggerFactory.getLogger(CometdPushService.class);

	private static final Map<WebApplication, CometdPushService> INSTANCES = new WeakHashMap<WebApplication, CometdPushService>();

	private static final IPushServiceRef<CometdPushService> PUSH_SERVICE_REF = new AbstractPushServiceRef<CometdPushService>()
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected CometdPushService lookupService()
		{
			return CometdPushService.get();
		}
	};

	public static CometdPushService get()
	{
		return get(WebApplication.get());
	}

	public static CometdPushService get(final WebApplication application)
	{
		synchronized (INSTANCES)
		{
			CometdPushService service = INSTANCES.get(application);
			if (service == null)
			{
				service = new CometdPushService(application);
				INSTANCES.put(application, service);
			}
			return service;
		}
	}

	/**
	 * @return a serializable service reference
	 */
	public static IPushServiceRef<CometdPushService> getRef()
	{
		return PUSH_SERVICE_REF;
	}

	private final ConcurrentMap<String, List<CometdPushNode<?>>> _nodesByCometdChannelId = new ConcurrentHashMap<String, List<CometdPushNode<?>>>();
	private final ConcurrentMap<CometdPushNode<?>, PushNodeState<?>> _nodeStates = new ConcurrentHashMap<CometdPushNode<?>, PushNodeState<?>>();

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

				final List<CometdPushNode<?>> nodes = _nodesByCometdChannelId.remove(channel.getId());
				if (nodes != null)
					for (final CometdPushNode<?> node : nodes)
						_onDisconnect(node);
			}
		});
	}

	private CometdPushBehavior _findPushBehaviour(final Component component)
	{
		for (final Behavior behavior : component.getBehaviors())
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

	private ServerChannel _getBayeuxServerChannel(final CometdPushNode<?> node)
	{
		return _getBayeuxServer().getChannel(node.getCometdChannelId());
	}

	private <EventType> void _onConnect(final CometdPushNode<EventType> node)
	{
		_nodeStates.put(node, new PushNodeState<EventType>(node));
		List<CometdPushNode<?>> nodes = _nodesByCometdChannelId.get(node.getCometdChannelId());
		if (nodes == null)
		{
			// create a new list
			final List<CometdPushNode<?>> newList = new ArrayList<CometdPushNode<?>>(2);

			// put the list, a list object is returned in case it was put in the meantime
			nodes = _nodesByCometdChannelId.putIfAbsent(node.getCometdChannelId(), newList);

			if (nodes == null)
				newList.add(node);
			else
				nodes.add(node);
		}
	}

	private void _onDisconnect(final CometdPushNode<?> node)
	{
		if (_nodeStates.remove(node) != null)
		{
			LOG.debug("Cometd push node {} disconnected.", node);

			disconnectFromAllChannels(node);

			for (final IPushNodeDisconnectedListener listener : disconnectListeners)
				try
				{
					listener.onDisconnect(node);
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
	public <EventType> CometdPushNode<EventType> installNode(final Component component,
		final IPushEventHandler<EventType> handler)
	{
		CometdPushBehavior behavior = _findPushBehaviour(component);
		if (behavior == null)
		{
			behavior = new CometdPushBehavior();
			component.add(behavior);
		}
		final CometdPushNode<EventType> node = behavior.addNode(handler);
		_onConnect(node);
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(final IPushNode<?> node)
	{
		if (node instanceof CometdPushNode)
			return _getBayeuxServerChannel((CometdPushNode<?>)node) != null;

		LOG.warn("Unsupported push node type {}", node);
		return false;
	}

	boolean isWebSocketTransportAvailable()
	{
		return CometdPushService.get()
			._getBayeuxServer()
			.getAllowedTransports()
			.contains("websocket");
	}

	@SuppressWarnings("unchecked")
	<EventType> List<CometdPushEventContext<EventType>> pollEvents(
		final CometdPushNode<EventType> node)
	{
		final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
		if (state == null)
		{
			LOG.debug("Reconnecting push node {}...", node);
			_onConnect(node);
			return Collections.EMPTY_LIST;
		}

		if (state.queuedEvents.size() == 0)
			return Collections.EMPTY_LIST;

		synchronized (state.queuedEventsLock)
		{
			final List<CometdPushEventContext<EventType>> events = state.queuedEvents;
			state.queuedEvents = new ArrayList<CometdPushEventContext<EventType>>(2);
			return events;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <EventType> void publish(final IPushChannel<EventType> channel, final EventType event)
	{
		if (channel == null)
			throw new IllegalArgumentException("Argument [channel] must not be null");

		final Set<IPushNode<?>> pnodes = nodesByChannels.get(channel);
		if (pnodes == null)
			throw new IllegalArgumentException("Unknown channel " + channel);

		final CometdPushEventContext<EventType> ctx = new CometdPushEventContext<EventType>(event,
			channel, this);

		// publish the event to all registered nodes
		for (final IPushNode<?> pnode : pnodes)
		{
			@SuppressWarnings("unchecked")
			final CometdPushNode<EventType> node = (CometdPushNode<EventType>)pnode;

			final ServerChannel cchannel = _getBayeuxServerChannel(node);
			if (cchannel == null)
			{
				LOG.warn("No cometd channel found for {}", node);
				continue;
			}

			@SuppressWarnings("unchecked")
			final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
			if (state != null)
			{
				synchronized (state.queuedEventsLock)
				{
					state.queuedEvents.add(ctx);
				}
				cchannel.publish(null, "pollEvents", state.node.getCometdChannelEventId());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <EventType> void publish(final IPushNode<EventType> node, final EventType event)
	{
		if (node instanceof CometdPushNode)
		{
			final ServerChannel cchannel = _getBayeuxServerChannel((CometdPushNode<?>)node);
			if (cchannel == null)
				LOG.warn("No cometd channel found for {}", node);
			else
			{
				@SuppressWarnings("unchecked")
				final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
				if (state != null)
				{
					synchronized (state.queuedEventsLock)
					{
						state.queuedEvents.add(new CometdPushEventContext<EventType>(event, null,
							this));
					}
					cchannel.publish(null, "pollEvents", state.node.getCometdChannelEventId());
				}
			}
		}
		else
			LOG.warn("Unsupported push node type {}", node);
	}

	/**
	 * Directly sends JavaScript code to the node via a cometd channel without an additional Wicket
	 * AJAX request roundtrip.
	 */
	public <EventType> void publishJavascript(final CometdPushNode<EventType> node,
		final String javascript)
	{
		final ServerChannel channel = _getBayeuxServerChannel(node);
		if (channel == null)
			LOG.warn("No cometd channel found for {}", node);
		else
			channel.publish(null, "javascript:" + javascript, node.getCometdChannelEventId());
	}

	/**
	 * Directly sends JavaScript code to all nodes listeing to the given push channel via a cometd
	 * channel without an additional Wicket AJAX request roundtrip.
	 */
	@SuppressWarnings("unchecked")
	public <EventType> void publishJavascript(final PushChannel<EventType> channel,
		final String javascript)
	{
		if (channel == null)
			throw new IllegalArgumentException("Argument [channel] must not be null");

		final Set<IPushNode<?>> pnodes = nodesByChannels.get(channel);
		if (pnodes == null)
			throw new IllegalArgumentException("Unknown channel " + channel);

		// publish the event to all registered nodes
		for (final IPushNode<?> node : pnodes)
			publishJavascript((CometdPushNode<EventType>)node, javascript);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uninstallNode(final Component component, final IPushNode<?> node)
	{
		if (node instanceof CometdPushNode)
		{
			final CometdPushBehavior behavior = _findPushBehaviour(component);
			if (behavior == null)
				return;
			if (behavior.removeNode(node) == 0)
				component.remove(behavior);
		}
		else
			LOG.warn("Unsupported push node type {}", node);
	}
}
