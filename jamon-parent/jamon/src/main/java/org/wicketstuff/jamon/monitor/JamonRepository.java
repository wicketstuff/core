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
package org.wicketstuff.jamon.monitor;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * <p>
 * Repository around the Jamon API. This repository is a small wrapper around the
 * {@link MonitorFactory}.
 * </p>
 * <p>
 * Since Wicket is an unmanaged framework this repository is implemented as a singleton. All
 * monitors are kept in memory anyway so that shouldn't pose a problem. One can use the
 * {@link #clear()} method to remove all {@link Monitor}s from this repository.
 * </p>
 * 
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonRepository implements Serializable, MonitoringRepository
{
	public JamonRepository()
	{
	}

	/**
	 * Returns all the {@link Monitor}s in this repository.
	 * 
	 * @return List of all {@link Monitor}s or an empty list if there aren't any.
	 */
	List<Monitor> getAll()
	{
		Monitor[] monitors = MonitorFactory.getRootMonitor().getMonitors();

		if (monitors != null)
		{
			return Arrays.asList(monitors);
		}
		else
		{
			return new ArrayList<Monitor>(0);
		}
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.jamon.monitor.MonitoringRepository#count()
	 */
	public int count()
	{
		return getAll().size();
	}

	/**
	 * Removes all the {@link SerializableMonitor}s in this repository. This also propagates to the
	 * {@link MonitorFactory}.
	 */
	public static void clear()
	{
		MonitorFactory.getFactory().reset();
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.jamon.monitor.MonitoringRepository#findMonitorByLabel(java.lang.String)
	 */
	public Monitor findMonitorByLabel(String monitorLabel)
	{
		if (monitorLabel == null)
		{
			throw new IllegalArgumentException("monitorLabel is null");
		}
		List<Monitor> monitors = find(new MonitorLabelSpecification(monitorLabel));

		if (monitors.size() > 1)
		{
			throw new IllegalStateException(
				format("More than one monitor with label '%s' found", monitorLabel));
		}
		return monitors.isEmpty() ? null : monitors.get(0);
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.jamon.monitor.MonitoringRepository#find(org.wicketstuff.jamon.monitor.MonitorSpecification)
	 */
	public List<Monitor> find(MonitorSpecification specification)
	{
		List<Monitor> result = new ArrayList<Monitor>();
		for (Monitor monitor : getAll())
		{
			if (specification.isSatisfiedBy(monitor))
			{
				result.add(monitor);
			}
		}
		return result;
	}

}
