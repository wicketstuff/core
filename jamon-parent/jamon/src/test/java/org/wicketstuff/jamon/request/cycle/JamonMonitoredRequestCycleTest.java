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

import static org.junit.Assert.assertEquals;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wicketstuff.jamon.component.JamonAdminPage;
import org.wicketstuff.jamon.webapp.AjaxPage;
import org.wicketstuff.jamon.webapp.HomePage;
import org.wicketstuff.jamon.webapp.JamonWebApplication;

import com.jamonapi.MonitorFactory;

public class JamonMonitoredRequestCycleTest
{

	private WicketTester wicketTester;

	private JamonWebApplication jamonWebApplication;


	@Before
	public void setup()
	{
		jamonWebApplication = new JamonWebApplication();
		wicketTester = new WicketTester(jamonWebApplication);
		MonitorFactory.getFactory().reset();
	}

	@Test
	public void shouldCreateMonitorForPagesThatAreNavigatedTo()
	{
		wicketTester.startPage(HomePage.class);

		assertEquals(1, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
	}

	@Test
	public void shouldCreateTwoMonitorsForPagesThatAreNavigatedToTwice()
	{
		wicketTester.startPage(HomePage.class);
		wicketTester.startPage(HomePage.class);
		assertEquals(2, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
	}

	@Test
	public void shouldNotMonitorJamonAdminPageItSelf()
	{
		wicketTester.startPage(JamonAdminPage.class);
		assertEquals(0, MonitorFactory.getMonitor("JamonAdminPage", "ms.").getHits(), 0);
		FormTester formTester = wicketTester.newFormTester("adminPanel:adminForm");
		formTester.setValue("monitorLabel", "J");
		wicketTester.executeAjaxEvent("adminPanel:adminForm:monitorLabel", "keyup");
		assertEquals(0, MonitorFactory.getMonitor("JamonAdminPage", "ms.").getHits(), 0);
	}

	@Ignore// broken in Wicket 8.0. Needs debugging!
	@Test
	public void shouldCreateMonitorIfAjaxLinkIsClickedOnPage()
	{
		wicketTester.startPage(AjaxPage.class);

		wicketTester.clickLink("ajaxLink", true);
		wicketTester.clickLink("ajaxLink", true);

		assertEquals(2, MonitorFactory.getMonitor("AjaxPage.ajaxLink -> AjaxPage", "ms.").getHits(),
			0);

	}

	@Ignore// broken in Wicket 8.0. Needs debugging!
	@Test
	public void shouldCreateMonitorIfAjaxLinkIsClickedOnPageStartedWithClass()
	{
		wicketTester.startPage(AjaxPage.class);
		wicketTester.clickLink("ajaxLink");

		assertEquals(1, MonitorFactory.getMonitor("AjaxPage.ajaxLink -> AjaxPage", "ms.").getHits(),
			0);
	}

	@Test
	public void shouldCreateMonitorForPagesWithClassThatAreNavigatedTo()
	{
		wicketTester.startPage(HomePage.class);

		assertEquals(1, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);

	}

	@Test
	public void shouldCreateMonitorForPageInstanceThatAreNavigatedTo()
	{
		wicketTester.startPage(new HomePage());

		assertEquals(1, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
	}
}
