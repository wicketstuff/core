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
package com.googlecode.wicket.kendo.ui.widget.treeview;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.kendo.ui.KendoTemplateBehavior;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEventFactory;

/**
 * Provides the Kendo UI TreeView
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AjaxTreeView extends JQueryContainer implements ITreeViewListener
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private TreeViewModelBehavior modelBehavior; // load events

	// templates //
	private IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link TreeViewModel}
	 */
	public AjaxTreeView(String id, TreeViewModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link TreeViewModel}
	 * @param options the {@link Options}
	 */
	public AjaxTreeView(String id, TreeViewModel model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, AjaxTreeViewBehavior.METHOD);
	}

	/**
	 * Expand the {@link AjaxTreeView} to the specified path
	 * 
	 * @param response the {@link IHeaderResponse}
	 * @param path the path to the node, as an id-array, ie: [1, 2, 3]
	 * @see AjaxTreeView#renderHead(org.apache.wicket.Component, IHeaderResponse)
	 */
	public void expandPath(IHeaderResponse response, String path)
	{
		response.render(OnDomReadyHeaderItem.forScript(String.format("%s.expandPath(%s)", this.widget(), path)));
	}

	/**
	 * Expand the {@link AjaxTreeView} to the specified path
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param path the path to the node, as an id-array, ie: [1, 2, 3]
	 */

	public void expandPath(IPartialPageRequestHandler handler, String path)
	{
		handler.appendJavaScript(String.format("%s.expandPath(%s)", this.widget(), path));
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); $w.refresh(); }", this.widget()));
		// TODO verify if #refresh() is needed
		this.onRefresh(target);
	}

	// Properties //

	/**
	 * Gets the sheduler's model
	 *
	 * @return the {@link TreeViewModel}
	 */
	public TreeViewModel getModel()
	{
		return (TreeViewModel) this.getDefaultModel();
	}

	/**
	 * Gets the {@link TreeViewModelBehavior} callback url
	 * 
	 * @return the {@code TreeViewModelBehavior} callback url
	 */
	protected CharSequence getCallbackUrl()
	{
		return this.modelBehavior.getCallbackUrl();
	}

	@Override
	public boolean isSelectEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isExpandEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newTreeViewModelBehavior(this.getModel(), this.newTreeNodeFactory());
		this.add(this.modelBehavior);

		// templates //

		this.template = this.newTemplate();

		if (this.template != null)
		{
			this.templateBehavior = new KendoTemplateBehavior(this.template);
			this.add(this.templateBehavior);
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("dataUrlField", Options.asString(TreeNodeFactory.URL_FIELD)); // 'url'
		behavior.setOption("dataTextField", Options.asString(TreeNodeFactory.TEXT_FIELD)); // 'text'

		// set templates (if any) //
		if (this.templateBehavior != null)
		{
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.templateBehavior.getToken()));
		}
	}

	/**
	 * Configure the {@link TreeViewDataSource} with additional options
	 * 
	 * @param dataSource the {@link TreeViewDataSource}
	 */
	protected void onConfigure(TreeViewDataSource dataSource)
	{
		dataSource.set("schema", String.format("{ model: { id: '%s' } }", TreeNodeFactory.ID_FIELD));
	}

	/**
	 * Triggered when {@link #refresh(AjaxRequestTarget)} has been called
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onRefresh(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public void onExpand(AjaxRequestTarget target, int nodeId)
	{
		// noop
	}

	@Override
	public void onSelect(AjaxRequestTarget target, int nodeId, String nodePath)
	{
		// noop
	}

	// IJQueryWidget //

	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AjaxTreeViewBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return AjaxTreeView.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(TreeViewDataSource dataSource)
			{
				AjaxTreeView.this.onConfigure(dataSource);
			}
		};
	}

	// Factory methods //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the built-in edit window
	 * 
	 * @return null by default
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link SchedulerEventFactory}
	 * 
	 * @return a new {@code SchedulerEventFactory}
	 */
	protected TreeNodeFactory newTreeNodeFactory()
	{
		return new TreeNodeFactory();
	}

	/**
	 * Gets a new {@link TreeViewModelBehavior}
	 *
	 * @param model the {@link TreeViewModel}
	 * @return the {@link TreeViewModelBehavior}
	 */
	protected TreeViewModelBehavior newTreeViewModelBehavior(final TreeViewModel model, final TreeNodeFactory factory)
	{
		return new TreeViewModelBehavior(model, factory);
	}
}
