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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.ComponentDetachableModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.jamon.monitor.MonitorSpecification;

import com.jamonapi.Monitor;

/**
 * {@link DefaultDataTable} that will create a row for each {@link Monitor}.
 * 
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonMonitorTable extends DefaultDataTable<Monitor, String>
{
	public static final int DEFAULT_ROWS_PER_PAGE = 40;

	public JamonMonitorTable(String id, MonitorSpecification specification, long maxRowsPerPage)
	{
		super(id, createColumns(), new JamonProvider(specification), (int)maxRowsPerPage);
		setOutputMarkupId(true);
		setMarkupId(id);
	}

	private static List<IColumn<Monitor, String>> createColumns()
	{
		List<IColumn<Monitor, String>> cols = new ArrayList<IColumn<Monitor, String>>();
		cols.add(createColumnWithLinkToDetail("label", "label"));
		cols.add(createColumn("hits", "hits"));
		cols.add(createColumn("average", "avg"));
		cols.add(createColumn("total", "total"));
		cols.add(createColumn("stdDev", "stdDev"));
		cols.add(createColumn("lastValue", "lastValue"));

		cols.add(createColumn("min", "min"));
		cols.add(createColumn("max", "max"));

		cols.add(createColumn("active", "active"));
		cols.add(createColumn("avgActive", "avgActive"));
		cols.add(createColumn("maxActive", "maxActive"));

		cols.add(createDateColumn("firstAccess", "firstAccess"));
		cols.add(createDateColumn("lastAccess", "lastAccess"));

		return cols;
	}

	@Override
	protected Item<Monitor> newRowItem(String id, int index, IModel<Monitor> model)
	{
		Item<Monitor> rowItem = super.newRowItem(id, index, model);
		return IndexBasedMouseOverMouseOutSupport.add(rowItem, rowItem.getIndex());
	}

	private static PropertyColumn<Monitor, String> createColumn(String resourceKey,
		String propertyName)
	{
		return new PropertyColumn<Monitor, String>(getResourceModelForKey(resourceKey),
			propertyName, propertyName);
	}

	private static PropertyColumn<Monitor, String> createColumnWithLinkToDetail(String resourceKey,
		String propertyName)
	{
		return new PropertyColumn<Monitor, String>(getResourceModelForKey(resourceKey),
			propertyName, propertyName)
		{
			@Override
			public void populateItem(Item<ICellPopulator<Monitor>> item, String componentId,
				IModel<Monitor> model)
			{
				item.add(new LinkToDetailPanel(componentId, getDataModel(model)));
			}

		};
	}

	private static PropertyColumn<Monitor, String> createDateColumn(String resourceKey,
		final String propertyName)
	{
		return new PropertyColumn<Monitor, String>(getResourceModelForKey(resourceKey),
			propertyName, propertyName)
		{
			@Override
			public IModel<?> getDataModel(IModel<Monitor> rowModel)
			{
				return new DateFormatModel(new PropertyModel<Date>(rowModel, propertyName));
			}
		};
	}

	private static ResourceModel getResourceModelForKey(String resourceKey)
	{
		return new ResourceModel(String.format("wicket.jamon.%s", resourceKey));
	}

	public static class DateFormatModel extends ComponentDetachableModel<String> {
		private final IModel<Date> date;

		public DateFormatModel(IModel<Date> date)
		{
			this.date = date;
		}
		
		@Override
		protected String getObject(Component component)
		{
			return new SimpleDateFormat("yyyy-MM.dd HH:mm:ss.SSS", component.getLocale())
			.format(date.getObject());
		}
		
		@Override
		public void detach()
		{
			date.detach();
			super.detach();
		}
	}
}