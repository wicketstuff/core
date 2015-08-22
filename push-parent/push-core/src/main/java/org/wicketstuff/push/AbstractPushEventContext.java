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
package org.wicketstuff.push;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.util.lang.Args;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public abstract class AbstractPushEventContext<EventType> implements IPushEventContext<EventType>
{
	private final long timestamp;
	private final IPushChannel<EventType> channel;
	private final EventType event;
	private final Map<Object, Object> properties = new ConcurrentHashMap<Object, Object>();

	protected AbstractPushEventContext(final EventType event, final IPushChannel<EventType> channel)
	{
		this.channel = channel;
		this.event = event;
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	public IPushChannel<EventType> getChannel()
	{
		return channel;
	}

	/**
	 * {@inheritDoc}
	 */
	public EventType getEvent()
	{
		return event;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getProperty(final Object key)
	{
		return properties.get(Args.notNull(key, "key"));
	}

	/**
	 * {@inheritDoc}
	 */
	public long getTimestamp()
	{
		return timestamp;
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractPushEventContext<EventType> setProperty(final Object key, final Object value)
	{
		properties.put(Args.notNull(key, "key"), value);
		return this;
	}
}
