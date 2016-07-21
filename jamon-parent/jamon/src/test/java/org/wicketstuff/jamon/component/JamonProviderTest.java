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

import static org.apache.commons.collections4.IteratorUtils.toList;
import static org.junit.Assert.assertEquals;
import static org.wicketstuff.jamon.component.JamonTestUtil.startThisManyMonitors;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.jamon.monitor.AlwaysSatisfiedMonitorSpecification;
import org.wicketstuff.jamon.monitor.JamonRepository;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class JamonProviderTest
{

	private JamonProvider jamonProvider;
	
	private WicketTester wicketTester;
	
	@Before
	public void setup()
	{
		wicketTester = new WicketTester(JamonAdminPage.class);
		wicketTester.getApplication().setMetaData(MonitoringRepositoryKey.KEY, new JamonRepository());
		
		jamonProvider = new JamonProvider(new AlwaysSatisfiedMonitorSpecification());
	}

	@After
	@Before
	public void reset()
	{
		MonitorFactory.getFactory().reset();
	}

	@Test
	public void shouldSupportPaging()
	{
		startThisManyMonitors(15);

		Iterator<Monitor> iterator = jamonProvider.iterator(0, 5);
		assertEquals(5, toList(iterator).size());

		iterator = jamonProvider.iterator(4, 5);
		assertEquals(5, toList(iterator).size());

		iterator = jamonProvider.iterator(10, 5);
		assertEquals(5, toList(iterator).size());

		iterator = jamonProvider.iterator(15, 5);
		assertEquals(1, toList(iterator).size());

		iterator = jamonProvider.iterator(20, 5);
		assertEquals(0, toList(iterator).size());

	}

	@Test
	public void shouldSupportSortingOfProperties()
	{
		startThisManyMonitors(3);
		jamonProvider.setSort("label", SortOrder.ASCENDING);

		Iterator<Monitor> ascendingIterator = jamonProvider.iterator(0, 3);
		assertEquals("mon0", ((Monitor)ascendingIterator.next()).getLabel());
		assertEquals("mon1", ((Monitor)ascendingIterator.next()).getLabel());
		assertEquals("mon2", ((Monitor)ascendingIterator.next()).getLabel());

		jamonProvider.setSort("label", SortOrder.DESCENDING);

		Iterator<Monitor> descendingIterator = jamonProvider.iterator(0, 3);
		assertEquals("mon2", ((Monitor)descendingIterator.next()).getLabel());
		assertEquals("mon1", ((Monitor)descendingIterator.next()).getLabel());
		assertEquals("mon0", ((Monitor)descendingIterator.next()).getLabel());
	}
}
