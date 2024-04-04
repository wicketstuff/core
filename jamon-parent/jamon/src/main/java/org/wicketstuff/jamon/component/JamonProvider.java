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
package org.wicketstuff.jamon.component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jamon.monitor.MonitorSpecification;
import org.wicketstuff.jamon.monitor.MonitoringRepository;

import com.jamonapi.Monitor;


/**
 * Jamon implementation of the {@link SortableDataProvider}.
 */
@SuppressWarnings("serial")
public class JamonProvider extends SortableDataProvider<Monitor, String>
{
	private static final Iterator<Monitor> EMPTY_ITERATOR = Collections.emptyIterator();

	private MonitoringRepository jamonRepository;

	private final MonitorSpecification specification;

	public JamonProvider(MonitorSpecification specification)
	{
		this.specification = specification;
		jamonRepository = Application.get().getMetaData(MonitoringRepositoryKey.KEY);
		setSort("label", SortOrder.ASCENDING);
	}

	public Iterator<Monitor> iterator(long first, long count)
	{
		List<Monitor> allMonitors = jamonRepository.find(specification);
		long toIndex = allMonitors.size() < (first + count) ? allMonitors.size() : (first + count);
		if (first > toIndex)
		{
			return EMPTY_ITERATOR;
		}
		else
		{
			List<Monitor> monitors = allMonitors.subList((int)first, (int)toIndex);
			final String sortProperty = this.getSort().getProperty();
			final boolean ascending = this.getSort().isAscending();
			Collections.sort(monitors, new PropertyModelObjectComparator(ascending, sortProperty));
			return monitors.iterator();
		}
	}

	public IModel<Monitor> model(Monitor object)
	{
		return new ThrowAwayModel<Monitor>(object);
	}

	public long size()
	{
		return jamonRepository.count();
	}
}
