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
package org.wicketstuff.html5.eventsource;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;

/**
 * A reference to a custom implementation of EventSourceResource.
 * Sends five messages with decreasing value of a counter and closes the connection.
 *
 * @since 6.0
 */
public class EventSourceResourceReference extends ResourceReference
{
	public EventSourceResourceReference()
	{
		super("EventSource");
	}

	@Override
	public IResource getResource()
	{
		return new EventSourceResource() {

			@Override
			protected void respond(EventSource eventSource)
			{
				int i = 5;
				while (i >= 0)
				{
					eventSource.data("{\"counter\": "+i+"}").end();
					i--;
					Duration.milliseconds(2000).sleep();
				}
				eventSource.close();
			}
		};
	}
}
