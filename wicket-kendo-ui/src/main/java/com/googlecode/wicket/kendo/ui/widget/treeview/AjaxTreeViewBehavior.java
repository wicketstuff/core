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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource.HierarchicalDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides the Kendo UI TreeView behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AjaxTreeViewBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoTreeView";

	private final ITreeViewListener listener;
	private HierarchicalDataSource dataSource;

	private JQueryAjaxBehavior onChangeAjaxBehavior = null;
	private JQueryAjaxBehavior onExpandAjaxBehavior = null;
	private JQueryAjaxBehavior onDropAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link ITreeViewListener}
	 */
	public AjaxTreeViewBehavior(final String selector, ITreeViewListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link ITreeViewListener}
	 */
	public AjaxTreeViewBehavior(final String selector, Options options, ITreeViewListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data-source //
		this.dataSource = new HierarchicalDataSource(component);
		this.add(this.dataSource);

		// behaviors //

		if (this.listener.isChangeEventEnabled())
		{
			this.onChangeAjaxBehavior = this.newOnChangeAjaxBehavior(this);
			component.add(this.onChangeAjaxBehavior);
		}

		if (this.listener.isExpandEventEnabled())
		{
			this.onExpandAjaxBehavior = this.newOnExpandAjaxBehavior(this);
			component.add(this.onExpandAjaxBehavior);
		}

		if (this.listener.isDropEventEnabled())
		{
			this.onDropAjaxBehavior = this.newOnDropAjaxBehavior(this);
			component.add(this.onDropAjaxBehavior);
		}
	}

	// Properties //

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getDataSourceUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// options //
		this.setOption("autoBind", true);
		this.setOption("loadOnDemand", true); // ajax

		// events //

		if (this.onExpandAjaxBehavior != null)
		{
			this.setOption("expand", this.onExpandAjaxBehavior.getCallbackFunction());
		}

		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		if (this.onDropAjaxBehavior != null)
		{
			this.setOption("dragAndDrop", true);
			this.setOption("drop", this.onDropAjaxBehavior.getCallbackFunction());
		}

		// data-source //
		this.onConfigure(this.dataSource);
		this.setOption("dataSource", this.dataSource.getName());
		this.dataSource.setTransportReadUrl(this.getDataSourceUrl());
	}

	/**
	 * Configure the {@link HierarchicalDataSource} with additional options
	 * 
	 * @param dataSource the {@link HierarchicalDataSource}
	 */
	protected void onConfigure(HierarchicalDataSource dataSource)
	{
		// noop
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ExpandEvent)
		{
			this.listener.onExpand(target, ((ExpandEvent) event).getNodeId());
		}

		if (event instanceof ChangeEvent)
		{
			ChangeEvent payload = (ChangeEvent) event;
			this.listener.onChange(target, payload.getNodeId(), payload.getNodePath());
		}

		if (event instanceof DropEvent)
		{
			DropEvent payload = (DropEvent) event;
			this.listener.onDrop(target, payload.getNodeId(), payload.getParentId(), payload.getPosition());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'change' event, triggered when a node is selected
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code JQueryAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnChangeAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'expand' event, triggered when a node is expanded
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code JQueryAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnExpandAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnExpandAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'drop' event, triggered when a node is selected
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code JQueryAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDropAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDropAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'change' event
	 */
	protected static class OnChangeAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnChangeAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("nodeId", String.format("this.dataItem(this.select()).%s", TreeNodeFactory.ID_FIELD)), // lf
					CallbackParameter.resolved("nodePath", "path") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			// computes the node path
			// from http://jsfiddle.net/bZXnR/1/
			StringBuilder builder = new StringBuilder();
			builder.append("var $treeview = this;");
			builder.append("var $node = this.select();");
			builder.append("var items = jQuery($node).add(jQuery($node).parentsUntil('.k-treeview', '.k-item'));");
			builder.append("var paths = jQuery.map(items, function(item) { ");
			builder.append("    var node = jQuery(item).find('> div span.k-in');");
			builder.append("    return $treeview.dataItem(node).").append(TreeNodeFactory.ID_FIELD).append(";");
			builder.append("});");
			builder.append("var path = '[' + paths.join(',') + ']';");

			return builder.toString() + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ChangeEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'expand' event
	 */
	protected static class OnExpandAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnExpandAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("nodeId", String.format("this.dataItem(e.node).%s", TreeNodeFactory.ID_FIELD)) };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ExpandEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'drop' event
	 */
	protected static class OnDropAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDropAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("nodeId", String.format("this.dataItem(e.sourceNode).%s", TreeNodeFactory.ID_FIELD)), // lf
					CallbackParameter.resolved("parentId", String.format("this.dataItem(e.destinationNode).%s", TreeNodeFactory.ID_FIELD)), // lf
					CallbackParameter.resolved("dropPosition", "e.dropPosition") // lf
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DropEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnExpandAjaxBehavior} callback
	 */
	protected static class ExpandEvent extends JQueryEvent
	{
		private final int nodeId;

		public ExpandEvent()
		{
			this.nodeId = RequestCycleUtils.getQueryParameterValue("nodeId").toInt(0);
		}

		public int getNodeId()
		{
			return this.nodeId;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnChangeAjaxBehavior} callback
	 */
	protected static class ChangeEvent extends JQueryEvent
	{
		private final int nodeId;
		private final String nodePath;

		public ChangeEvent()
		{
			this.nodeId = RequestCycleUtils.getQueryParameterValue("nodeId").toInt(0);
			this.nodePath = RequestCycleUtils.getQueryParameterValue("nodePath").toString();
		}

		public int getNodeId()
		{
			return this.nodeId;
		}

		public String getNodePath()
		{
			return this.nodePath;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDropAjaxBehavior} callback
	 */
	protected static class DropEvent extends JQueryEvent
	{
		private final int nodeId;
		private final int parentId;
		private final String position;

		public DropEvent()
		{
			this.nodeId = RequestCycleUtils.getQueryParameterValue("nodeId").toInt(0);
			this.parentId = RequestCycleUtils.getQueryParameterValue("parentId").toInt(0);
			this.position = RequestCycleUtils.getQueryParameterValue("dropPosition").toString();
		}

		public int getNodeId()
		{
			return this.nodeId;
		}

		public int getParentId()
		{
			return this.parentId;
		}
		
		public String getPosition()
		{
			return this.position;
		}
	}
}
