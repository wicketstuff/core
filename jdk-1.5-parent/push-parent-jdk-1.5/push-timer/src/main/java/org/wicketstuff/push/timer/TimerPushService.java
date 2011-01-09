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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.push.AbstractPushService;
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushEventHandler;
import org.wicketstuff.push.IPushNode;
import org.wicketstuff.push.IPushNodeDisconnectedListener;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class TimerPushService extends AbstractPushService
{
	private static final class PushNodeState<EventType>
	{
		protected final TimerPushNode<EventType> node;
		protected Time lastPolledAt = Time.now();
		protected List<TimerPushEventContext<EventType>> queuedEvents = new ArrayList<TimerPushEventContext<EventType>>(
			2);
		protected final Object queuedEventsLock = new Object();

		protected PushNodeState(final TimerPushNode<EventType> node)
		{
			this.node = node;
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(TimerPushService.class);

	static final ConcurrentHashMap<Application, TimerPushService> INSTANCES = new ConcurrentHashMap<Application, TimerPushService>(
		2);

	public static TimerPushService get()
	{
		return get(Application.get());
	}

	public static TimerPushService get(final Application application)
	{
		TimerPushService service = INSTANCES.get(application);
		if (service == null)
		{
			service = new TimerPushService();
			final TimerPushService existingInstance = INSTANCES.putIfAbsent(application, service);

			if (existingInstance == null)
				/*
				 * If this is the first instance of this service for the given application, then
				 * schedule the cleanup task.
				 */
				service.setCleanupInterval(Duration.seconds(60));
			else
				// If it is not the first instance, throw it away.
				service = existingInstance;
		}
		return service;
	}

	private Duration _defaultPollingInterval = Duration.seconds(2);
	private Duration _maxTimeLag = Duration.seconds(10);

	private final ConcurrentMap<TimerPushNode<?>, PushNodeState<?>> _nodeStates = new ConcurrentHashMap<TimerPushNode<?>, PushNodeState<?>>();
	private final Set<IPushNodeDisconnectedListener> _disconnectListeners = new CopyOnWriteArraySet<IPushNodeDisconnectedListener>();

	private final ScheduledThreadPoolExecutor _cleanupExecutor = new ScheduledThreadPoolExecutor(1);
	private ScheduledFuture<?> _cleanupFuture = null;
	private final Runnable _cleanupTask = new Runnable()
	{
		public void run()
		{
			LOG.debug("Running timer push node cleanup task...");
			final Time now = Time.now();
			int count = 0;
			for (final PushNodeState<?> state : _nodeStates.values())
				if (now.subtract(state.lastPolledAt).greaterThan(_maxTimeLag))
				{
					onDisconnect(state.node);
					count++;
				}
			LOG.debug("Cleaned up {} timer push nodes.", count);
		}
	};

	private TimerPushService()
	{
		super();
	}

	private TimerPushBehavior _findPushBehaviour(final Component component)
	{
		for (final Behavior behavior : component.getBehaviors())
			if (behavior instanceof TimerPushBehavior)
				return (TimerPushBehavior)behavior;
		return null;
	}

	private <EventType> void _onConnect(final TimerPushNode<EventType> node)
	{
		_nodeStates.put(node, new PushNodeState<EventType>(node));
	}

	/**
	 * {@inheritDoc}
	 */
	public void addNodeDisconnectedListener(final IPushNodeDisconnectedListener listener)
	{
		_disconnectListeners.add(listener);
	}

	public Duration getDefaultPollingInterval()
	{
		return _defaultPollingInterval;
	}

	public Duration getMaxTimeLag()
	{
		return _maxTimeLag;
	}

	/**
	 * {@inheritDoc}
	 */
	public <EventType> TimerPushNode<EventType> installNode(final Component component,
		final IPushEventHandler<EventType> handler)
	{
		return installNode(component, handler, _defaultPollingInterval);
	}

	public <EventType> TimerPushNode<EventType> installNode(final Component component,
		final IPushEventHandler<EventType> handler, final Duration pollingInterval)
	{
		TimerPushBehavior behavior = _findPushBehaviour(component);
		if (behavior == null)
		{
			behavior = new TimerPushBehavior(pollingInterval);
			component.add(behavior);
		}
		final TimerPushNode<EventType> node = behavior.addNode(handler, pollingInterval);
		_onConnect(node);
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(final IPushNode<?> node)
	{
		if (node instanceof TimerPushNode)
		{
			final PushNodeState<?> state = _nodeStates.get(node);
			if (state == null)
				return false;

			if (Time.now().subtract(state.lastPolledAt).greaterThan(_maxTimeLag))
			{
				onDisconnect(state.node);
				return false;
			}
			return true;
		}
		LOG.warn("Unsupported push node type {}", node);
		return false;
	}

	void onApplicationShutdown()
	{
		LOG.info("Shutting down timer push service...");
		_cleanupFuture.cancel(false);
		_cleanupFuture = null;
		_cleanupExecutor.shutdownNow();
		INSTANCES.remove(this);
	}

	void onDisconnect(final TimerPushNode<?> node)
	{
		if (_nodeStates.remove(node) != null)
		{
			LOG.debug("Timer push node {} disconnected.", node);

			disconnectFromAllChannels(node);

			for (final IPushNodeDisconnectedListener listener : _disconnectListeners)
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

	@SuppressWarnings("unchecked")
	<EventType> List<TimerPushEventContext<EventType>> pollEvents(
		final TimerPushNode<EventType> node)
	{
		final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
		if (state == null)
		{
			LOG.debug("Reconnecting push node {}...", node);
			_onConnect(node);
			return Collections.EMPTY_LIST;
		}

		state.lastPolledAt = Time.now();

		if (state.queuedEvents.size() == 0)
			return Collections.EMPTY_LIST;

		synchronized (state.queuedEventsLock)
		{
			final List<TimerPushEventContext<EventType>> events = state.queuedEvents;
			state.queuedEvents = new ArrayList<TimerPushEventContext<EventType>>(2);
			return events;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <EventType> void publish(final IPushChannel<EventType> channel, final EventType event)
	{
		if (channel == null)
			throw new IllegalArgumentException("Argument [channel] must not be null");

		final Set<IPushNode<?>> pnodes = nodesByChannels.get(channel);
		if (pnodes == null)
			throw new IllegalArgumentException("Unknown channel " + channel);

		final TimerPushEventContext<EventType> ctx = new TimerPushEventContext<EventType>(event,
			channel, this);

		// publish the event to all registered nodes
		for (final IPushNode<?> pnode : pnodes)
		{
			@SuppressWarnings("unchecked")
			final TimerPushNode<EventType> node = (TimerPushNode<EventType>)pnode;

			if (isConnected(node))
			{
				@SuppressWarnings("unchecked")
				final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
				if (state != null)
					synchronized (state.queuedEventsLock)
					{
						state.queuedEvents.add(ctx);
					}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <EventType> void publish(final IPushNode<EventType> node, final EventType event)
	{
		if (node instanceof TimerPushNode)
		{
			if (isConnected(node))
			{
				@SuppressWarnings("unchecked")
				final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
				if (state != null)
					synchronized (state.queuedEventsLock)
					{
						state.queuedEvents.add(new TimerPushEventContext<EventType>(event, null,
							this));
					}
			}
		}
		else
			LOG.warn("Unsupported push node type {}", node);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeNodeDisconnectedListener(final IPushNodeDisconnectedListener listener)
	{
		_disconnectListeners.remove(listener);
	}

	/**
	 * Sets the interval in which the clean up task will be executed that removes information about
	 * disconnected push nodes. Default is 60 seconds.
	 */
	public void setCleanupInterval(final Duration interval)
	{
		synchronized (this)
		{
			if (_cleanupFuture != null)
				_cleanupFuture.cancel(false);
			_cleanupFuture = _cleanupExecutor.scheduleAtFixedRate(_cleanupTask,
				interval.getMilliseconds(), interval.getMilliseconds(), TimeUnit.MILLISECONDS);
		}
	}

	public void setDefaultPollingInterval(final Duration defaultPollingInterval)
	{
		_defaultPollingInterval = defaultPollingInterval;
	}

	public void setMaxTimeLag(final Duration maxTimeLag)
	{
		_maxTimeLag = maxTimeLag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void uninstallNode(final Component component, final IPushNode<?> node)
	{
		if (node instanceof TimerPushNode)
		{
			final TimerPushBehavior behavior = _findPushBehaviour(component);
			if (behavior == null)
				return;
			if (behavior.removeNode(node) == 0)
				component.remove(behavior);
		}
		else
			LOG.warn("Unsupported push node type {}", node);
	}
}
