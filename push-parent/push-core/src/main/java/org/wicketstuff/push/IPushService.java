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

import org.apache.wicket.Component;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public interface IPushService
{
	void addNodeDisconnectedListener(IPushNodeDisconnectedListener listener);

	/**
	 * Connects the given push node to the given push channel
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>channel</code> is unknown
	 */
	<EventType> void connectToChannel(IPushNode<EventType> node, IPushChannel<EventType> channel);

	/**
	 * Creates a new push channel with the given <code>label</code>.
	 * 
	 * @param label
	 *            the label to associate with the new push channel. (may be null)
	 *            <p>
	 *            <b>Note:</b> The <code>label</code> has only informative purpose. Creating two
	 *            push channels with the same label will not result in an error.
	 */
	<EventType> IPushChannel<EventType> createChannel(String label);

	<EventType> void disconnectFromChannel(IPushNode<EventType> node,
		IPushChannel<EventType> channel);

	<EventType> IPushNode<EventType> installNode(Component component,
		IPushEventHandler<EventType> handler);

	/**
	 * Determines if the node (client) is still connected, otherwise clears all queued events.
	 */
	boolean isConnected(IPushNode<?> node);

	<EventType> void publish(IPushChannel<EventType> channel, EventType event);

	/**
	 * Queues the given event for later processing by the associated {@link IPushEventHandler}. The
	 * method does nothing in case the node is no longer connected.
	 */
	<EventType> void publish(IPushNode<EventType> node, EventType event);

	<EventType> void removeChannel(IPushChannel<EventType> channel);

	void removeNodeDisconnectedListener(IPushNodeDisconnectedListener listener);

	void uninstallNode(Component component, IPushNode<?> node);
}