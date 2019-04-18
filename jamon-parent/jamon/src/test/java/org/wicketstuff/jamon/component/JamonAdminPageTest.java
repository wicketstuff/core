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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.jamon.monitor.JamonRepository;

import com.jamonapi.MonitorFactory;


class JamonAdminPageTest
{

	private WicketTester wicketTester;

	@BeforeEach
	void beforeEachTest()
	{
		wicketTester = new WicketTester(JamonAdminPage.class);
		wicketTester.getApplication().setMetaData(MonitoringRepositoryKey.KEY, new JamonRepository());
	}

	@AfterEach
	void after()
	{
		wicketTester.destroy();
		MonitorFactory.getFactory().reset();
	}

	@Test
	void shouldRenderStatisticsPageWithOneMonitor()
	{
		JamonTestUtil.startThisManyMonitors(1);
		wicketTester.startPage(JamonAdminPage.class);
		wicketTester.assertRenderedPage(JamonAdminPage.class);
		assertEquals(1, wicketTester.getTagsByWicketId("linkText").size());
		assertTrue(
			wicketTester.getTagsByWicketId("linkText").get(0).getValue().equals("mon0"));
	}

	@Test
	void shouldRenderStatisticsPageWithTwoMonitors()
	{
		JamonTestUtil.startThisManyMonitors(2);
		wicketTester.startPage(new JamonAdminPage());
		wicketTester.assertRenderedPage(JamonAdminPage.class);
		assertEquals(2, wicketTester.getTagsByWicketId("linkText").size());
		assertTrue(
			wicketTester.getTagsByWicketId("linkText").get(0).getValue().equals("mon0"));
		assertTrue(
			wicketTester.getTagsByWicketId("linkText").get(1).getValue().equals("mon1"));
	}
}
