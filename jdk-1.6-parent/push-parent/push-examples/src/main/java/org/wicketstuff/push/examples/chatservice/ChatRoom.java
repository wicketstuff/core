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
package org.wicketstuff.push.examples.chatservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class ChatRoom
{
	private static final Logger LOG = LoggerFactory.getLogger(ChatRoom.class);

	private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

	private final List<Message> chatHistory = new ArrayList<Message>();
	private final Set<IChatListener> listeners = new CopyOnWriteArraySet<IChatListener>();

	private final String name;

	ChatRoom(final String name)
	{
		this.name = name;
	}

	private void _notify(final IChatListener listener, final Message message)
	{
		try
		{
			listener.onMessage(message);
		}
		catch (final Exception ex)
		{
			LOG.error("Failed to notify chat listener " + listener, ex);
		}
	}

	public void addListener(final IChatListener listener)
	{
		synchronized (listeners)
		{
			listeners.add(listener);
		}
	}

	public List<Message> getChatHistory()
	{
		return new ArrayList<Message>(chatHistory);
	}

	public String getName()
	{
		return name;
	}

	public void removeListener(final IChatListener listener)
	{
		synchronized (listeners)
		{
			listeners.remove(listener);
		}
	}

	/**
	 * sends the chat message asynchronously (in a background thread) to all listeners
	 */
	public void sendAsync(final String user, final String message)
	{
		final Message msg = new Message(user, message, name);
		chatHistory.add(msg);

		executorService.submit(new Runnable()
		{
			@Override
			public void run()
			{
				for (final IChatListener listener : listeners)
					_notify(listener, msg);
			}
		});
	}
}
