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
package com.googlecode.wicket.kendo.ui.repeater.dataview;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoTemplateBehavior;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.behavior.DataBoundBehavior;
import com.googlecode.wicket.kendo.ui.repeater.listview.IListViewListener;
import com.googlecode.wicket.kendo.ui.repeater.listview.ListViewBehavior;

/**
 * Provides a DataView based on Kendo UI listView
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class DataView<T> extends WebMarkupContainer implements IJQueryWidget, IListViewListener
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private final long rows;

	/** the data-source provider */
	private final IDataProvider<T> provider;
	private AbstractAjaxBehavior providerBehavior;

	/** the data-source renderer */
	private final ITextRenderer<? super T> renderer;

	/** the template */
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataView(String id, final IDataProvider<T> provider, final long rows)
	{
		this(id, provider, rows, new TextRenderer<T>(), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param options the {@link Options}
	 */
	public DataView(String id, final IDataProvider<T> provider, final long rows, Options options)
	{
		this(id, provider, rows, new TextRenderer<T>(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param renderer the {@link ITextRenderer}
	 */
	public DataView(String id, final IDataProvider<T> provider, final long rows, ITextRenderer<? super T> renderer)
	{
		this(id, provider, rows, renderer, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param renderer the {@link ITextRenderer}
	 * @param options the {@link Options}
	 */
	public DataView(String id, final IDataProvider<T> provider, final long rows, ITextRenderer<? super T> renderer, Options options)
	{
		super(id);

		this.rows = rows;
		this.options = options;

		this.provider = provider;
		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, ListViewBehavior.METHOD);
	}

	/**
	 * Shows the {@link DataView}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void show(IPartialPageRequestHandler handler)
	{
		this.onShow(handler);

		KendoBehaviorFactory.show(handler, this);
	}

	/**
	 * Hides the {@link DataView}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void hide(IPartialPageRequestHandler handler)
	{
		KendoBehaviorFactory.hide(handler, this);

		this.onHide(handler);
	}

	/**
	 * Reloads the {@link DataView}<br/>
	 * Equivalent to {@code handler.add(table)}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void reload(IPartialPageRequestHandler handler)
	{
		handler.add(this);
	}

	/**
	 * Refreshes the {@link DataView} by reading from the datasource
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));
	}

	// Properties //

	/**
	 * Gets the {@link IDataProvider}
	 *
	 * @return the {@link IDataProvider}
	 */
	public IDataProvider<T> getDataProvider()
	{
		return this.provider;
	}

	/**
	 * Gets the number of rows per page to be displayed
	 *
	 * @return the number of rows per page to be displayed
	 */
	protected final long getRowCount()
	{
		return this.rows;
	}

	/**
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected final CharSequence getCallbackUrl()
	{
		return this.providerBehavior.getCallbackUrl();
	}
	
	@Override
	public boolean isSelectable()
	{
		return this.options.get("selectable") != null;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.providerBehavior = this.newDataProviderBehavior(this.getDataProvider(), this.renderer, this.template);
		this.add(this.providerBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward

		if (this.template != null)
		{
			this.templateBehavior = new KendoTemplateBehavior(this.template);
			this.add(this.templateBehavior);
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("autoBind", this.getBehaviors(DataBoundBehavior.class).isEmpty()); // false if DataBoundBehavior is added

		// set template (if any) //
		if (this.templateBehavior != null)
		{
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.templateBehavior.getToken()));
		}
	}

	/**
	 * Configure the {@link KendoDataSource} with additional options
	 * 
	 * @param dataSource the {@link KendoDataSource}
	 */
	protected void onConfigure(KendoDataSource dataSource)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	/**
	 * Triggered when the {@link DataView} shows
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onShow(IPartialPageRequestHandler handler)
	{
		// noop
	}

	/**
	 * Triggered when the {@link DataView} hides
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onHide(IPartialPageRequestHandler handler)
	{
		// noop
	}

	@Override
	public void onCreate(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}
	
	@Override
	public void onChange(AjaxRequestTarget target, List<JSONObject> objects)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ListViewBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected long getRowCount()
			{
				return DataView.this.getRowCount();
			}

			@Override
			protected CharSequence getProviderUrl()
			{
				return DataView.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				DataView.this.onConfigure(dataSource);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br/>
	 * The properties used in the template text (ie: ${data.name}) should be of the prefixed by "data." and should be identified in the list returned by {@link IJQueryTemplate#getTextProperties()} (without "data.")
	 *
	 * @return null by default
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link DataProviderBehavior}
	 *
	 * @param provider the {@link IDataProvider}
	 * @param renderer the {@link ITextRenderer}
	 * @param template the {@link IJQueryTemplate}
	 * @return the {@link AbstractAjaxBehavior}
	 */
	protected AbstractAjaxBehavior newDataProviderBehavior(IDataProvider<T> provider, ITextRenderer<? super T> renderer, IJQueryTemplate template)
	{
		return new DataProviderBehavior<T>(provider, renderer, template);
	}
}
