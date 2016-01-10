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

import static org.wicketstuff.jamon.component.JamonMonitorTable.DEFAULT_ROWS_PER_PAGE;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.jamon.monitor.AlwaysSatisfiedMonitorSpecification;


/**
 * Main page of the JAMon admin interface. The monitors are shown in a Pageable table.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminPage extends WebPage
{

	public static final String PATH_TO_STATISTICS_TABLE = "jamonStatistics";

	public static final String PATH_TO_MONITOR_DETAILS = "monitorDetails";

	/**
	 * Construct.
	 * 
	 * @param rowsPerPage
	 *            How many monitors per page should be rendered?
	 */
	public JamonAdminPage(int rowsPerPage)
	{
		add(new JamonAdminPanel("adminPanel"));
		add(new JamonMonitorTable(PATH_TO_STATISTICS_TABLE,
			new AlwaysSatisfiedMonitorSpecification(), rowsPerPage));
		add(new EmptyMarkupContainer(PATH_TO_MONITOR_DETAILS));
	}

	/**
	 * Construct. It will use the {@link JamonMonitorTable#DEFAULT_ROWS_PER_PAGE} for determining
	 * how many monitors on one page are shown.
	 */
	public JamonAdminPage()
	{
		this(DEFAULT_ROWS_PER_PAGE);
	}

}