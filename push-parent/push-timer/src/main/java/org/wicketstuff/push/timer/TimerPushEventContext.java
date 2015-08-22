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

import org.wicketstuff.push.AbstractPushEventContext;
import org.wicketstuff.push.IPushChannel;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
final class TimerPushEventContext<EventType> extends AbstractPushEventContext<EventType>
{
	private final TimerPushService pushService;

	TimerPushEventContext(final EventType event, final IPushChannel<EventType> channel,
		final TimerPushService pushService)
	{
		super(event, channel);
		this.pushService = pushService;
	}

	/**
	 * {@inheritDoc}
	 */
	public TimerPushService getService()
	{
		return pushService;
	}
}
