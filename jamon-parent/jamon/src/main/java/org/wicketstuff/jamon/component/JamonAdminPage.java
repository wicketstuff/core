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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.jamon.monitor.AlwaysHitedMonitorSpecification;


/**
 * Main page of the JAMon admin interface. The monitors are shown in a Pageable table.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminPage extends WebPage
{

	public static final String PARAM_ITEMS = "items";

	public static final String PATH_TO_STATISTICS_TABLE = "jamonStatistics";

	public static final String PATH_TO_MONITOR_DETAILS = "monitorDetails";

	public JamonAdminPage()
	{
		this(null);
	}

	/**
	 * Construct.
	 * 
	 * You are able to set rows per page by page parameter "items". Otherwise
	 * {@link JamonMonitorTable#DEFAULT_ROWS_PER_PAGE default} size is used.
	 * 
	 * @param rowsPerPage
	 *            How many monitors per page should be rendered?
	 */
	public JamonAdminPage(PageParameters parameters)
	{
		super(parameters);

		int rowsPerPage = getPageParameters().get(PARAM_ITEMS).toInt(DEFAULT_ROWS_PER_PAGE);

		add(new JamonAdminPanel("adminPanel"));
		add(new JamonMonitorTable(PATH_TO_STATISTICS_TABLE,
			new AlwaysHitedMonitorSpecification(), rowsPerPage));
		add(new EmptyMarkupContainer(PATH_TO_MONITOR_DETAILS));
	}

}