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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.wicketstuff.jamon.component.JamonTestUtil.MONITOR_PREFIX;
import static org.wicketstuff.jamon.component.JamonTestUtil.startThisManyMonitors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.jamon.monitor.JamonRepository;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class JamonRepositoryTest
{
	private JamonRepository jamonRepository;

	@Before
	public void setup()
	{
		jamonRepository = new JamonRepository();
		JamonRepository.clear();
	}

	@After
	public void clear()
	{
		MonitorFactory.getFactory().reset();
	}

	@Test
	public void testThatRepositoryReturnsMonitors_OneMonitor()
	{
		startThisManyMonitors(1);

		assertEquals(2, jamonRepository.count());
		assertEquals(2, jamonRepository.getAll().size());
	}

	@Test
	public void testThatRepositoryReturnsMonitors_TenMonitors()
	{
		startThisManyMonitors(9);

		assertEquals(10, jamonRepository.count());
		assertEquals(10, jamonRepository.getAll().size());
	}

	@Test
	public void testThatRepositoryReturnsMonitors_NoMonitors()
	{
		startThisManyMonitors(0);

		assertEquals(1, jamonRepository.count());
		assertEquals(1, jamonRepository.getAll().size());
		assertNotNull(jamonRepository.findMonitorByLabel(MonitorFactory.EXCEPTIONS_LABEL));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfTryingToFindMonitorWithNullLabel()
	{
		jamonRepository.findMonitorByLabel(null);
	}

	@Test
	public void shouldReturnNullIfNoMonitorIsFoundForACertainLabel()
	{
		startThisManyMonitors(1);
		assertNull(jamonRepository.findMonitorByLabel("non existing label"));
	}

	@Test
	public void shouldReturnMonitorWithGivenLabel()
	{
		startThisManyMonitors(2);
		Monitor actual = jamonRepository.findMonitorByLabel(MONITOR_PREFIX + "1");

		assertNotNull(actual);
		assertEquals(MONITOR_PREFIX + "1", actual.getLabel());
	}


}
