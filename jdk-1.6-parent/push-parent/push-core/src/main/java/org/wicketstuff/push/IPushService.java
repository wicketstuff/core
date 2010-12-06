/*
 * Licensed to the Apache Softwimport org.apache.wicket.Component;
e
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
	void addPushChannelDisconnectedListener(IPushChannelDisconnectedListener listener);

	<EventType> IPushChannel<EventType> installPushChannel(final Component component,
		final IPushEventHandler<EventType> pushEventHandler);

	/**
	 * Determines if the client is still connected, otherwise clears all queued events.
	 */
	boolean isConnected(final IPushChannel<?> pushChannel);

	/**
	 * Queues the given event for later processing by the associated
	 * {@link AbstractPushEventHandler}. The method does nothing in case the client is no longer
	 * connected.
	 */
	<EventType> void publish(final IPushChannel<EventType> pushChannel, final EventType event);

	void removePushChannelDisconnectedListener(IPushChannelDisconnectedListener listener);

	void uninstallPushChannel(final Component component, final IPushChannel<?> pushChannel);

}