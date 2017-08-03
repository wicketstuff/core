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
package org.wicketstuff.jamon.request.cycle;

import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.jamon.component.JamonAdminPage;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


/**
 * <p>
 * To use this class simply create an instance and register it to request cycle. As a shortcut to
 * register context in request cycle you can use {@link #registerTo(RequestCycle, boolean)}
 * </p>
 * <p>
 * The responsibility of the {@link JamonMonitoredRequestCycleContext} is to add a monitor for all
 * actions that will cause pages or parts of pages to be rendered. <br>
 * The labels of the {@link Monitor}s come in these formats:
 * 
 * <ul>
 * <li>"PageName" - When a user navigates directly to a Page, for instance the HomePage, or a
 * bookmarked Page.</li>
 * <li>"PageName.toNextPage -> NextPage" - When the component toNextPage on the PageName page causes
 * the user to navigate to the "NextPage" page <b>and</b> this
 * {@link JamonMonitoredRequestCycleContext} has its property
 * {@link #includeSourceNameInMonitorLabel} set to <code>true</code>.</li>
 * <li>"NextPage" - When the user navigates to the "NextPage" page from whatever page he was on
 * <b>and</b> this {@link JamonMonitoredRequestCycleContext} has its property
 * {@link #includeSourceNameInMonitorLabel} set to <code>false</code>.</li>
 * </ul>
 * 
 * Any navigations from or to the {@link JamonAdminPage} is excluded from the Monitors.
 * </p>
 * 
 * @author lars
 * 
 */
public class JamonMonitoredRequestCycleContext
{

	static final String UNIT = "ms.";

	/**
	 * At what time did the request start.
	 */
	private long startTimeRequest;

	/**
	 * The source from where the request originated. This will typically be in the form of
	 * PageName.component. Where component is the name of the component that was clicked.
	 * 
	 */
	private String source;

	/**
	 * The name of the target object that was rendered. This will be the page name in most cases.
	 */
	private String target;

	/**
	 * Should should the source name be included in the Monitors.
	 */
	private final boolean includeSourceNameInMonitorLabel;

	/**
	 * Should we ignore this request cycle as it involves the {@link JamonAdminPage} itself?
	 */
	private boolean dontMonitorThisRequest;

	/**
	 * Construct.
	 * 
	 * @param includeSourceNameInMonitorLabel
	 *            whether or not to include the name of the {@link #source} in the Monitors label.
	 */
	public JamonMonitoredRequestCycleContext(boolean includeSourceNameInMonitorLabel)
	{
		this.includeSourceNameInMonitorLabel = includeSourceNameInMonitorLabel;
		this.startTimeRequest = 0;
	}

	public static void registerTo(RequestCycle cycle, boolean includeSourceNameInMonitorLabel)
	{
		RequestCycle requestCycle = cycle == null ? RequestCycle.get() : cycle;
		requestCycle.setMetaData(JamonMonitoredRequestCycleContextKey.KEY,
			new JamonMonitoredRequestCycleContext(includeSourceNameInMonitorLabel));
	}

	public static JamonMonitoredRequestCycleContext get(RequestCycle cycle)
	{
		RequestCycle requestCycle = cycle == null ? RequestCycle.get() : cycle;
		return requestCycle.getMetaData(JamonMonitoredRequestCycleContextKey.KEY);
	}

	public final void startTimeRequest()
	{
		this.startTimeRequest = System.currentTimeMillis();
	}

	public final void stopTimeRequest()
	{
		calculateDurationAndAddToMonitor();
	}

	/**
	 * Sets the {@link #source}.
	 * 
	 * @param source
	 *            The name of the source where the request originated from.
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * Set the {@link #target}.
	 * 
	 * @param destination
	 *            The name of the page that was rendered.
	 */
	public void setTarget(Class<? extends IRequestablePage> destination)
	{
		this.target = destination.getSimpleName();
		this.dontMonitorThisRequest = (JamonAdminPage.class.isAssignableFrom(destination));
	}

	/**
	 * From which Page did the request come from? This is needed for creating the Monitor label.
	 * 
	 * @param clazz
	 */
	public void comesFromPage(Class<? extends IRequestablePage> clazz)
	{
		this.dontMonitorThisRequest = (JamonAdminPage.class.isAssignableFrom(clazz));
	}

	private void calculateDurationAndAddToMonitor()
	{
		if (this.startTimeRequest != 0 && !dontMonitorThisRequest)
		{
			long duration = System.currentTimeMillis() - startTimeRequest;
			if (includeSourceNameInMonitorLabel)
			{
				MonitorFactory.add(createLabel(), UNIT, duration);
			}
			else
			{
				MonitorFactory.add(String.format("%s", target), UNIT, duration);
			}
		}
	}

	private String createLabel()
	{
		if (source == null || source.equals(target))
		{
			return String.format("%s", target);
		}
		else
		{
			return String.format("%s -> %s", source, target);
		}
	}

	public final void dontMonitorThisRequest()
	{
		this.dontMonitorThisRequest = true;
	}
}
