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
package org.wicketstuff.kendo.ui.datatable;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.core.util.lang.PropertyResolverConverter;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.convert.ConversionException;
import org.wicketstuff.jquery.core.behavior.AjaxCallbackBehavior;
import org.wicketstuff.kendo.ui.datatable.column.IColumn;
import org.wicketstuff.kendo.ui.datatable.column.PropertyColumn;
import org.wicketstuff.kendo.ui.utils.PropertyUtils;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

/**
 * Provides the {@link DataTable} data source {@link AjaxCallbackBehavior}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public class DataProviderBehavior<T> extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String ASC = "asc";

	/** The max number of filtered column */
	private static final int COLS = 20;

	private final IDataProvider<T> provider;
	private final IModel<List<IColumn>> columns;

	/**
	 * Constructor
	 *
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 */
	public DataProviderBehavior(final IModel<List<IColumn>> columns, final IDataProvider<T> provider)
	{
		this.columns = columns;
		this.provider = provider;
	}

	// Methods //

	@SuppressWarnings("unchecked")
	protected void setSort(String property, SortOrder order)
	{
		ISortStateLocator<String> locator = (ISortStateLocator<String>) this.provider;
		ISortState<String> sortState = locator.getSortState();

		if (property != null)
		{
			sortState.setPropertySortOrder(property, order);
		}
		else if (sortState instanceof SingleSortState<?>)
		{
			((SingleSortState<?>) sortState).setSort(null);
		}
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final int first = parameters.getParameterValue("skip").toInt(0);
		final int count = parameters.getParameterValue("take").toInt(Short.MAX_VALUE);

		// ISortStateLocator //
		if (this.provider instanceof ISortStateLocator<?>)
		{
			String property = parameters.getParameterValue("sort[0][field]").toOptionalString();
			String direction = parameters.getParameterValue("sort[0][dir]").toOptionalString();

			this.setSort(PropertyUtils.unescape(property), direction == null ? SortOrder.NONE : ASC.equals(direction) ? SortOrder.ASCENDING : SortOrder.DESCENDING);
		}

		// IFilterStateLocator //
		if (this.provider instanceof IFilterStateLocator<?>)
		{
			@SuppressWarnings("unused")
			String logicPattern = "filter[logic]";
			String fieldPattern = "filter[filters][%d][field]";
			String valuePattern = "filter[filters][%d][value]";

			@SuppressWarnings("unused")
			String operatorPattern = "filter[filters][%d][operator]";
			// TODO: implement logic & operator (new IFilterStateLocator interface?)

			@SuppressWarnings("unchecked")
			T object = ((IFilterStateLocator<T>) this.provider).getFilterState();
			PropertyResolverConverter converter = this.newPropertyResolverConverter();

			for (int i = 0; i < COLS; i++)
			{
				String field = parameters.getParameterValue(String.format(fieldPattern, i)).toOptionalString();
				String value = parameters.getParameterValue(String.format(valuePattern, i)).toOptionalString();

				if (field != null)
				{
					PropertyResolver.setValue(PropertyUtils.unescape(field), object, value, converter);
				}
				else
				{
					break;
				}
			}
		}

		// response //
		final JSONArray results = new JSONArray();
		final Iterator<? extends T> iterator = this.provider.iterator(first, count);

		if (iterator != null)
		{
			while (iterator.hasNext())
			{
				results.put(this.newJsonRow(iterator.next()));
			}
		}

		JSONObject object = new JSONObject();
		object.put("results", results);
		object.put("__count", this.provider.size());

		return object.toString();
	}

	@Override
	public void detach(Component component)
	{
		super.detach(component);

		this.provider.detach();
	}

	// Factories //

	/**
	 * Get a new {@link PropertyResolverConverter}
	 *
	 * @return a new {@code PropertyResolverConverter}
	 */
	protected PropertyResolverConverter newPropertyResolverConverter()
	{
		return new PropertyResolverConverter(Application.get().getConverterLocator(), Session.get().getLocale());
	}

	/**
	 * Gets a new JSON object from the bean
	 *
	 * @param bean T object
	 * @return a new JSON object
	 */
	protected JSONObject newJsonRow(T bean)
	{
		JSONObject object = new JSONObject();

		try
		{
			for (IColumn column : this.columns.getObject())
			{
				if (column instanceof PropertyColumn)
				{
					PropertyColumn pc = (PropertyColumn) column;
					object.put(pc.getField(), pc.getValue(bean));
				}
			}
		}
		catch (JSONException e)
		{
			throw new ConversionException(e);
		}

		return object;
	}
}
