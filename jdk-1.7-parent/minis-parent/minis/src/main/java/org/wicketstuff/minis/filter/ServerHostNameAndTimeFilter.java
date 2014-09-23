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
package org.wicketstuff.minis.filter;

import static org.apache.wicket.core.util.string.JavaScriptUtils.SCRIPT_CLOSE_TAG;
import static org.apache.wicket.core.util.string.JavaScriptUtils.SCRIPT_OPEN_TAG;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.response.filter.IResponseFilter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.time.Duration;

/**
 * Displays an server identifier (combination of host name, IP address and unique id) and the time
 * it took to handle the request in the browser's status bar like this:
 * <code>window.defaultStatus = 'Host: myhost/192.168.1.66/someid, handled in: 0.01s'</code>
 * 
 * @author eelco hillenius
 * @author David Bernard
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class ServerHostNameAndTimeFilter implements IResponseFilter
{
	private String host;

	/**
	 * Construct, trying system property 'hostid' for the server instance.
	 */
	public ServerHostNameAndTimeFilter() throws Exception
	{
		this(System.getProperty("hostid"));
	}

	/**
	 * Construct with an id.
	 * 
	 * @param hostId
	 *            a unique id identifying this server instance
	 */
	public ServerHostNameAndTimeFilter(final String hostId)
	{
		try
		{
			final InetAddress localMachine = InetAddress.getLocalHost();
			host = localMachine.getHostName() + "/" + localMachine.getHostAddress() + "/" +
				(Strings.isEmpty(hostId) ? "<unknown>" : hostId);
		}
		catch (final UnknownHostException ex)
		{
			throw new WicketRuntimeException(ex);
		}
	}

	protected void addJavaScript(final AppendingStringBuffer sb)
	{
		sb.append("window.defaultStatus='");
		addMessage(sb);
		sb.append("'");
	}

	protected void addMessage(final AppendingStringBuffer sb)
	{
		final long timeTaken = System.currentTimeMillis() - RequestCycle.get().getStartTime();
		sb.append("Host: ")
			.append(host)
			.append(", handled in: ")
			.append(Duration.milliseconds(timeTaken));
	}

	/**
	 * {@inheritDoc}
	 */
	public AppendingStringBuffer filter(final AppendingStringBuffer responseBuffer)
	{
		final int index = responseBuffer.indexOf("</head>");
		if (index != -1)
		{
			final AppendingStringBuffer script = new AppendingStringBuffer(75);
			script.append("\n").append(SCRIPT_OPEN_TAG);
			addJavaScript(script);
			script.append(";").append(SCRIPT_CLOSE_TAG);
			responseBuffer.insert(index, script);
		}
		return responseBuffer;
	}
}