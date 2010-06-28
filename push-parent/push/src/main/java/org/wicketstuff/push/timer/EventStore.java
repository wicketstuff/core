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
import java.util.List;

import org.wicketstuff.push.ChannelEvent;

/**
 * Here we are simulating a bus with this event store
 * It is an Internal class (volontary package)
 *
 *
 * @author Vincent Demay
 */
class EventStore
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private transient final List<EventStoreListener> listenerList =
	  new ArrayList<EventStoreListener>();

	private final static EventStore eventStore = new EventStore();


	public void add(final ChannelEvent value)
	{
	  for (final EventStoreListener listener : listenerList) {
			listener.EventTriggered(value.getChannel(), value.getData());
		}
	}

	public static EventStore get(){
		return eventStore;
	}

	/**
	 * Adds a listener to this list which will be notified whenever the list is modified
	 * @param listener the listener to add
	 */
	public void addEventStoreListener(final EventStoreListener listener)
	{
		listenerList.add(listener);
	}
}
