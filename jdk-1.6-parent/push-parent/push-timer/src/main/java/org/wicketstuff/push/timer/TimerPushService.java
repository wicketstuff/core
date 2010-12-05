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
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushChannelDisconnectedListener;
import org.wicketstuff.push.IPushEventHandler;
import org.wicketstuff.push.IPushService;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class TimerPushService implements IPushService
{
	private static final class PushChannelState
	{
		protected final TimerPushChannel<?> channel;
		protected Time lastPolledAt = Time.now();
		protected List<Object> queuedEvents = new ArrayList<Object>(2);
		protected final Object queuedEventsLock = new Object();

		protected PushChannelState(final TimerPushChannel<?> channel)
		{
			this.channel = channel;
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

	private final ConcurrentMap<TimerPushChannel<?>, PushChannelState> _channelStates = new ConcurrentHashMap<TimerPushChannel<?>, PushChannelState>();
	private final Set<IPushChannelDisconnectedListener> _disconnectListeners = new CopyOnWriteArraySet<IPushChannelDisconnectedListener>();

	private final ScheduledThreadPoolExecutor _cleanupExecutor = new ScheduledThreadPoolExecutor(1);
	private ScheduledFuture<?> _cleanupFuture = null;
	private final Runnable _cleanupTask = new Runnable()
	{
		@Override
		public void run()
		{
			LOG.debug("Running timer push channel cleanup task...");
			final Time now = Time.now();
			int count = 0;
			for (final PushChannelState state : _channelStates.values())
				if (now.subtract(state.lastPolledAt).greaterThan(_maxTimeLag))
				{
					onDisconnect(state.channel);
					count++;
				}
			LOG.debug("Cleaned up {} timer push channels.", count);
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

	private void _onConnect(final TimerPushChannel<?> pushChannel)
	{
		_channelStates.put(pushChannel, new PushChannelState(pushChannel));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPushChannelDisconnectedListener(final IPushChannelDisconnectedListener listener)
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
	public <EventType> TimerPushChannel<EventType> installPush(final Component component,
		final IPushEventHandler<EventType> pushEventHandler, final Duration pollingInterval)
	{
		TimerPushBehavior behavior = _findPushBehaviour(component);
		if (behavior == null)
		{
			behavior = new TimerPushBehavior(pollingInterval);
			component.add(behavior);
		}
		final TimerPushChannel<EventType> pushChannel = behavior.addPushChannel(pushEventHandler,
			pollingInterval);
		_onConnect(pushChannel);
		return pushChannel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <EventType> TimerPushChannel<EventType> installPushChannel(final Component component,
		final IPushEventHandler<EventType> pushEventHandler)
	{
		return installPush(component, pushEventHandler, _defaultPollingInterval);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(final IPushChannel<?> pushChannel)
	{
		if (pushChannel instanceof TimerPushChannel)
		{
			final PushChannelState state = _channelStates.get(pushChannel);
			if (state == null)
				return false;

			if (Time.now().subtract(state.lastPolledAt).greaterThan(_maxTimeLag))
			{
				onDisconnect(state.channel);
				return false;
			}
			return true;
		}
		LOG.warn("Unsupported push channel type {}", pushChannel);
		return false;
	}

	void onApplicationShutdown()
	{
		LOG.info("Shutting down timer push service...");
		_cleanupFuture.cancel(false);
		_cleanupFuture = null;
		_cleanupExecutor.shutdown();
		INSTANCES.remove(this);
	}

	void onDisconnect(final TimerPushChannel<?> pushChannel)
	{
		if (_channelStates.remove(pushChannel) != null)
		{
			LOG.debug("Timer push channel {} disconnected.", pushChannel);

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

	@SuppressWarnings("unchecked")
	<EventType> List<EventType> pollEvents(final TimerPushChannel<EventType> pushChannel)
	{
		final PushChannelState state = _channelStates.get(pushChannel);
		if (state == null)
		{
			LOG.debug("Reconnecting push channel {}...", pushChannel);
			_onConnect(pushChannel);
			return Collections.EMPTY_LIST;
		}

		state.lastPolledAt = Time.now();

		if (state.queuedEvents.size() == 0)
			return Collections.EMPTY_LIST;

		synchronized (state.queuedEventsLock)
		{
			final List<EventType> events = (List<EventType>)state.queuedEvents;
			state.queuedEvents = new ArrayList<Object>(2);
			return events;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <EventType> void publish(final IPushChannel<EventType> pushChannel, final EventType event)
	{
		if (pushChannel instanceof TimerPushChannel)
		{
			if (isConnected(pushChannel))
			{
				final PushChannelState state = _channelStates.get(pushChannel);
				if (state == null)
					return;

				synchronized (state.queuedEventsLock)
				{
					state.queuedEvents.add(event);
				}
			}
		}
		else
			LOG.warn("Unsupported push channel type {}", pushChannel);
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
	 * Sets the interval in which the clean up task will be executed that removes information about
	 * disconnected push channels. Default is 60 seconds.
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
	@Override
	public void uninstallPushChannel(final Component component, final IPushChannel<?> pushChannel)
	{
		if (pushChannel instanceof TimerPushChannel)
		{
			final TimerPushBehavior behavior = _findPushBehaviour(component);
			if (behavior == null)
				return;
			if (behavior.removePushChannel(pushChannel) == 0)
				component.remove(behavior);
		}
		else
			LOG.warn("Unsupported push channel type {}", pushChannel);
	}
}
