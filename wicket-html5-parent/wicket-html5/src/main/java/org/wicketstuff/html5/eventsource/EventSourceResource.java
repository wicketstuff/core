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

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.Duration;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A IResource that supports the response protocol of Server Side Events
 *
 * @since 6.0
 */
public abstract class EventSourceResource implements IResource
{
	private static final Logger LOG = LoggerFactory.getLogger(EventSourceResource.class);

	public final void respond(final Attributes attributes)
	{
		Response response = attributes.getResponse();

		HttpServletResponse containerResponse = (HttpServletResponse) response.getContainerResponse();

		EventSource eventSource = new EventSource(containerResponse, attributes.getParameters());

		respond(eventSource);
	}

	/**
	 * A callback method which is called when an EventSource makes a request to this resource
	 *
	 * @param eventSource
	 *      a helper that should be used to write back to the client
	 */
	protected abstract void respond(final EventSource eventSource);

	public static class EventSource
	{
		private static final Charset UTF8 = Charset.forName("UTF-8");

		private final HttpServletResponse containerResponse;
		private final OutputStream out;
		private final PageParameters parameters;
		
		private boolean isClosed = false;
		
		private EventSource(final HttpServletResponse containerResponse, final PageParameters parameters)
		{
			try{
				this.containerResponse = containerResponse;
				this.out = containerResponse.getOutputStream();
				this.parameters = parameters;
				containerResponse.setCharacterEncoding("UTF-8");
				containerResponse.setContentType("text/event-stream");
			}
			catch (IOException iox)
			{
				throw new WicketRuntimeException("An error occurred while getting servlet's response", iox);
			}
		}

		/**
		 * Switches the current event.
		 *
		 * @param eventName
		 *      the name of new event
		 * @return {@code this} object, for chaining
		 */
		public EventSource event(final String eventName)
		{
			Args.notNull(eventName, "eventName");
			write("event: " + eventName + "\n");
			return this;
		}

		/**
		 * Sends a {@code data} message.
		 * Must be followed by EventSource#end() call to finish the sending of data.
		 *
		 * @param data
		 *      the data of new event
		 * @return {@code this} object, for chaining
		 */
		public EventSource data(final String data)
		{
			Args.notNull(data, "data");

			write("data: " + data + "\n");
			return this;
		}

		/**
		 * Sends an {@code id} message.
		 *
		 * @param id
		 *      the new id
		 * @return {@code this} object, for chaining
		 */
		public EventSource id(final String id)
		{
			if (id != null)
			{
				write("id: " + id);
			}
			else
			{
				write("id");
			}
			end();
			return this;
		}

		/**
		 * Sends a {@code retry} message.
		 *
		 * @param retryTimeout
		 *      how many millis to wait before the client to try to reconnect
		 * @return {@code this} object, for chaining
		 */
		public EventSource retry(final Duration retryTimeout)
		{
			Args.notNull(retryTimeout, "retryTimeout");
			if (retryTimeout.equals(Duration.ZERO) == false)
			{
				write("retry: " + retryTimeout.toMillis());
				end();
			}
			return this;
		}

		/**
		 * Sends a raw data to the client.
		 *
		 * @param rawData
		 *      the raw data to send to the client
		 * @return {@code this} object, for chaining
		 */
		public EventSource raw(final String rawData)
		{
			if (rawData != null)
			{
				write(rawData);
			}
			return this;
		}

		/**
		 * Ends a data message.
		 *
		 * @return {@code this} object, for chaining
		 */
		public EventSource end()
		{
			write("\n");
			return this;
		}

		/**
		 * Closes the connection to the client.
		 */
		public void close()
		{
			try
			{
				isClosed = true;
				containerResponse.setStatus(HttpServletResponse.SC_GONE);
				containerResponse.setContentType("text/plain");
				out.flush();
				out.close();
			}
			catch (IOException iox)
			{
				// log as debug because the connection may be closed anytime from the
				// client side without notifying the server side
				LOG.debug("An error occurred while closing the connection", iox);
			}
		}

		/**
		 * @return the request parameters sent by the client when making the connection
		 */
		public final PageParameters getParameters()
		{
			return parameters;
		}

		/**
		 * Checks whether the connection to the client is closed..
		 *
		 * @return {@code true} if the connection is closed, {@code false} otherwise
		 */
		public final boolean isClosed()
		{
			return isClosed;
		}

		private void write(String data)
		{
			checkClosed();

			try
			{
				out.write(data.getBytes(UTF8));
				out.flush();
			}
			catch (IOException iox)
			{
				// log as debug because the connection may be closed anytime from the
				// client side without notifying the server side
				LOG.debug("An error occurred while writing the response", iox);
			}
		}

		private void checkClosed()
		{
			if (isClosed)
			{
				throw new WicketRuntimeException("The EventSource connection is already closed.");
			}
		}
	}
}
