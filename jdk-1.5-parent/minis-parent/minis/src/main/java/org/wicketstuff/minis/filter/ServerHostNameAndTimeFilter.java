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

import java.net.InetAddress;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.response.filter.IResponseFilter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.JavaScriptUtils;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.time.Duration;

/**
 * Displays server host name (combination of name, ipaddress and unique id, which is either based)
 * and time it took to handle the request in the browser's status bar like this:
 * <code>window.defaultStatus = 'Host: myhost/192.168.1.66/someid, handled in: 0.01s'</code>
 * 
 * @author eelco hillenius
 * @author David Bernard
 */
public class ServerHostNameAndTimeFilter implements IResponseFilter
{
	private String host;

	/**
	 * Construct, trying system property 'examples.hostname' for the server id or else current time
	 * milis.
	 */
	public ServerHostNameAndTimeFilter() throws Exception
	{
		this(System.getProperty("hostid"));
	}

	/**
	 * Construct with an id.
	 * 
	 * @param hostId
	 *            a unique id indentifying this server instance
	 */
	public ServerHostNameAndTimeFilter(final String hostId) throws Exception
	{
		final InetAddress localMachine = InetAddress.getLocalHost();
		final String hostName = localMachine.getHostName();
		final String address = localMachine.getHostAddress();
		host = (!Strings.isEmpty(hostName) ? hostName + "/" : "") + address + "/" + hostId;
		if (Strings.isEmpty(hostId))
			host = "<unknown>";
	}

	/**
	 * @see org.apache.wicket.IResponseFilter#filter(AppendingStringBuffer)
	 */
	public AppendingStringBuffer filter(final AppendingStringBuffer responseBuffer)
	{
		final int index = responseBuffer.indexOf("<head>");
		final long timeTaken = System.currentTimeMillis() - RequestCycle.get().getStartTime();
		if (index != -1)
		{
			final AppendingStringBuffer script = new AppendingStringBuffer(75).append("\n")
				.append(JavaScriptUtils.SCRIPT_OPEN_TAG)
				.append("\n\twindow.defaultStatus='")
				.append("Host: ")
				.append(host)
				.append(", handled in: ")
				.append(Duration.milliseconds(timeTaken))
				.append("';\n")
				.append(JavaScriptUtils.SCRIPT_CLOSE_TAG)
				.append("\n");
			responseBuffer.insert(index + 6, script);
		}
		return responseBuffer;
	}
}