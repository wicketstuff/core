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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.JQueryUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEventFactory;
import com.googlecode.wicket.kendo.ui.template.KendoTemplateBehavior;

/**
 * Provides the Kendo UI TreeView
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AjaxTreeView extends JQueryContainer implements ITreeViewListener
{
	private static final long serialVersionUID = 1L;

	private TreeViewModelBehavior modelBehavior; // load events

	// templates //
	private IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	protected final Options options;

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
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));

		this.onRefresh(handler);
	}

	/**
	 * Expand the {@link AjaxTreeView} to the specified path
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param path the path to the node, as an id-array, ie: [1, 2, 3]
	 */
	public void expandPath(IPartialPageRequestHandler handler, String path)
	{
		String statement = String.format("%s.expandPath(%s);", this.widget(), path);
		handler.appendJavaScript(JQueryUtils.trycatch(statement));
	}

	/**
	 * Expand the {@link AjaxTreeView} to all specified paths
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param paths the path list to the node, as id-arrays
	 * @see #expandPath(IPartialPageRequestHandler, String)
	 */
	public void expandPaths(IPartialPageRequestHandler handler, List<String> paths)
	{
		for (String path : paths)
		{
			this.expandPath(handler, path);
		}
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

	/**
	 * Gets the template script token/id
	 * 
	 * @return the template script token/id
	 */
	public String getTemplateToken()
	{
		if (this.templateBehavior != null)
		{
			return this.templateBehavior.getToken();
		}

		return null;
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

	@Override
	public boolean isDropEventEnabled()
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
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.getTemplateToken()));
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
	 * Triggered when {@link #refresh(IPartialPageRequestHandler)} has been called
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void onRefresh(IPartialPageRequestHandler handler)
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

	@Override
	public void onDrop(AjaxRequestTarget target, int nodeId, int parentId, String position)
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
	 * @param factory the {@link TreeNodeFactory}
	 * @return the {@link TreeViewModelBehavior}
	 */
	protected TreeViewModelBehavior newTreeViewModelBehavior(final TreeViewModel model, final TreeNodeFactory factory)
	{
		return new TreeViewModelBehavior(model, factory);
	}

	// Classes //

	/**
	 * Provides a databound behavior that extends node(s)
	 */
	public static class ExpandBehavior extends JQueryAbstractBehavior
	{
		private static final long serialVersionUID = 1L;

		private String widget;
		private final List<String> paths;

		/**
		 * Constructor
		 * 
		 * @param path the path to the node, as an id-array, ie: [1, 2, 3]
		 */
		public ExpandBehavior(String path)
		{
			this(Arrays.asList(path));
		}

		/**
		 * Constructor
		 * 
		 * @param paths the {@code List} of paths to the node, as an id-array list
		 */
		public ExpandBehavior(List<String> paths)
		{
			this.paths = paths;
		}

		// Methods //

		@Override
		public void bind(Component component)
		{
			super.bind(component);

			this.widget = KendoUIBehavior.widget(component, AjaxTreeViewBehavior.METHOD);
		}

		/**
		 * Gets the callback/handler to be triggered on 'dataBound' event
		 * 
		 * @return statement like function(e) {...}
		 */
		protected String getDataBoundCallback()
		{
			String statement = "";

			for (String path : this.paths)
			{
				statement += String.format("this.expandPath(%s);", path);
			}

			return statement;
		}

		@Override
		protected String $()
		{
			return String.format("var $w = %s; $w.bind('dataBound', function(e) { %s }); ", this.widget, this.getDataBoundCallback());
		}
	}
}
