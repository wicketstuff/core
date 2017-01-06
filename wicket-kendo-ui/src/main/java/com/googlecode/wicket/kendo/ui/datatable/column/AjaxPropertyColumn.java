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
package com.googlecode.wicket.kendo.ui.datatable.column;

import java.util.Map;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;
import com.googlecode.wicket.jquery.core.resource.JavaScriptPackageHeaderItem;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides an property column for a {@link DataTable} which content will be ajax/lazy loaded.
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AjaxPropertyColumn extends PropertyColumn
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(String title, DataTable<?> datatable)
	{
		super(title);

		this.bind(datatable);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(String title, String property, DataTable<?> datatable)
	{
		super(title, property);

		this.bind(datatable);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(String title, int width, DataTable<?> datatable)
	{
		super(title, width);

		this.bind(datatable);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(String title, String property, int width, DataTable<?> datatable)
	{
		super(title, property, width);

		this.bind(datatable);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(IModel<String> title, String property, DataTable<?> datatable)
	{
		super(title, property);

		this.bind(datatable);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 */
	public AjaxPropertyColumn(IModel<String> title, String property, int width, DataTable<?> datatable)
	{
		super(title, property, width);

		this.bind(datatable);
	}

	// Methods //

	/**
	 * Bind this column to the given component.
	 * 
	 * @param component the {@link Component}
	 */
	private final void bind(Component component)
	{
		component.add(new AjaxTemplateBehavior());
	}

	@Override
	public final Boolean isEditable()
	{
		return false;
	}

	@Override
	public final String getTemplate()
	{
		return "#= loadAjaxPropertyColumn(data) #";
	}

	// Factories //

	/**
	 * Gets the {@link Component} that will be lazy loaded
	 * 
	 * @param id the markup id
	 * @param value the value corresponding to the column's field
	 * @return the new {@code Component}
	 */
	protected abstract Component getLazyComponent(String id, String value);

	// Classes //

	protected class AjaxTemplateBehavior extends AjaxCallbackBehavior
	{
		private static final long serialVersionUID = 1L;

		public AjaxTemplateBehavior()
		{
			super("text/html");
		}

		@Override
		public void renderHead(Component component, IHeaderResponse response)
		{
			super.renderHead(component, response);

			Map<String, Object> variables = Generics.newHashMap();
			variables.put("field", AjaxPropertyColumn.this.getField());
			variables.put("imageUrl", RequestCycle.get().urlFor(new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR)));
			variables.put("callbackUrl", this.getCallbackUrl());

			response.render(new JavaScriptPackageHeaderItem(AjaxPropertyColumn.class, variables));
		}

		@Override
		protected String getResponse(IRequestParameters parameters)
		{
			final String value = RequestCycleUtils.getQueryParameterValue("id").toString();
			final Component component = AjaxPropertyColumn.this.getLazyComponent(this.newMarkupId(), value);
			final CharSequence response = ComponentRenderer.renderComponent(component);

			return response != null ? response.toString() : "";
		}

		/**
		 * Gets a new random markup-id
		 * 
		 * @return a new random markup-id
		 */
		private String newMarkupId()
		{
			return "col-" + UUID.randomUUID();
		}
	}
}
