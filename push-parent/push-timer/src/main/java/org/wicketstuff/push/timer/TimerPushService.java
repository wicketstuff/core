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

import static java.util.Collections.EMPTY_LIST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;
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

/**
 * AJAX timer based implementation of {@link IPushService}.
 * <p>
 * 
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class TimerPushService extends AbstractPushService
{
	private final class PushNodeState<EventType>
	{
		final TimerPushNode<EventType> node;
		Time lastPolledAt = Time.now();
		List<TimerPushEventContext<EventType>> queuedEvents = new ArrayList<TimerPushEventContext<EventType>>(
			2);

		PushNodeState(final TimerPushNode<EventType> node)
		{
			this.node = node;
		}

		boolean isTimedOut()
		{
			return Time.now().subtract(lastPolledAt).greaterThan(_maxTimeLag);
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(TimerPushService.class);

	private static final ConcurrentHashMap<Application, TimerPushService> INSTANCES = new ConcurrentHashMap<Application, TimerPushService>(
		2);

	private static final IPushServiceRef<TimerPushService> PUSH_SERVICE_REF = new AbstractPushServiceRef<TimerPushService>()
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected TimerPushService lookupService()
		{
			return TimerPushService.get();
		}
	};

	public static TimerPushService get()
	{
		return get(Application.get());
	}

	public static TimerPushService get(final Application application)
	{
		Args.notNull(application, "application");

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

	/**
	 * @return a serializable service reference
	 */
	public static IPushServiceRef<TimerPushService> getRef()
	{
		return PUSH_SERVICE_REF;
	}

	static void onApplicationShutdown(final Application application)
	{
		Args.notNull(application, "application");

		final TimerPushService srv = INSTANCES.remove(application);
		if (srv != null)
		{
			LOG.info("Shutting down {}...", srv);
			synchronized (srv._cleanupExecutor)
			{
				srv._cleanupFuture.cancel(false);
				srv._cleanupFuture = null;
				srv._cleanupExecutor.shutdownNow();
			}
		}
	}

	private Duration _defaultPollingInterval = Duration.seconds(2);

	private Duration _maxTimeLag = Duration.seconds(10);

	private final ConcurrentMap<TimerPushNode<?>, PushNodeState<?>> _nodeStates = new ConcurrentHashMap<TimerPushNode<?>, PushNodeState<?>>();
	private final ScheduledThreadPoolExecutor _cleanupExecutor = new ScheduledThreadPoolExecutor(1);
	private ScheduledFuture<?> _cleanupFuture = null;

	private final Runnable _cleanupTask = new Runnable()
	{
		public void run()
		{
			LOG.debug("Running timer push node cleanup task...");
			int count = 0;
			for (final PushNodeState<?> state : _nodeStates.values())
				synchronized (state)
				{
					if (state.isTimedOut())
					{
						onDisconnect(state.node);
						count++;
					}
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
		Args.notNull(component, "component");
		Args.notNull(handler, "handler");
		Args.notNull(pollingInterval, "pollingInterval");

		TimerPushBehavior behavior = _findPushBehaviour(component);
		if (behavior != null && behavior.isStopped())
		{
			component.remove(behavior);
			behavior = null;
		}
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
		Args.notNull(node, "node");

		if (node instanceof TimerPushNode)
		{
			final PushNodeState<?> state = _nodeStates.get(node);
			if (state == null)
				return false;

			synchronized (state)
			{
				if (state.isTimedOut())
				{
					onDisconnect(state.node);
					return false;
				}
			}
			return true;
		}
		LOG.warn("Unsupported push node type {}", node);
		return false;
	}

	void onDisconnect(final TimerPushNode<?> node)
	{
		if (_nodeStates.remove(node) != null)
		{
			LOG.debug("Timer push node {} disconnected.", node);

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

	@SuppressWarnings("unchecked")
	<EventType> List<TimerPushEventContext<EventType>> pollEvents(
		final TimerPushNode<EventType> node)
	{
		final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
		if (state == null)
		{
			LOG.debug("Reconnecting push node {}...", node);
			_onConnect(node);
			return EMPTY_LIST;
		}

		synchronized (state)
		{
			state.lastPolledAt = Time.now();

			if (state.queuedEvents.size() == 0)
				return EMPTY_LIST;

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
		Args.notNull(channel, "channel");

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
					synchronized (state)
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
		Args.notNull(node, "node");

		if (node instanceof TimerPushNode)
		{
			if (isConnected(node))
			{
				@SuppressWarnings("unchecked")
				final PushNodeState<EventType> state = (PushNodeState<EventType>)_nodeStates.get(node);
				if (state != null)
					synchronized (state)
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
	 * Sets the interval in which the clean up task will be executed that removes information about
	 * disconnected push nodes. Default is 60 seconds.
	 */
	public void setCleanupInterval(final Duration interval)
	{
		Args.notNull(interval, "interval");

		synchronized (_cleanupExecutor)
		{
			if (_cleanupFuture != null)
				_cleanupFuture.cancel(false);
			if (!_cleanupExecutor.isShutdown())
				_cleanupFuture = _cleanupExecutor.scheduleAtFixedRate(_cleanupTask,
					interval.getMilliseconds(), interval.getMilliseconds(), TimeUnit.MILLISECONDS);
		}
	}

	public void setDefaultPollingInterval(final Duration defaultPollingInterval)
	{
		Args.notNull(defaultPollingInterval, "defaultPollingInterval");

		_defaultPollingInterval = defaultPollingInterval;
	}

	public void setMaxTimeLag(final Duration maxTimeLag)
	{
		Args.notNull(maxTimeLag, "maxTimeLag");

		_maxTimeLag = maxTimeLag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void uninstallNode(final Component component, final IPushNode<?> node)
	{
		Args.notNull(component, "component");
		Args.notNull(node, "node");

		if (node instanceof TimerPushNode)
		{
			final TimerPushBehavior behavior = _findPushBehaviour(component);
			if (behavior == null)
				return;
			if (behavior.removeNode(node) == 0)
				behavior.stop(RequestCycle.get().find(AjaxRequestTarget.class));
		}
		else
			LOG.warn("Unsupported push node type {}", node);
	}
}
