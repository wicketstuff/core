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
package com.googlecode.wicket.kendo.ui.widget.splitter;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI splitter behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SplitterBehavior extends KendoUIBehavior implements IJQueryAjaxAware, ISplitterListener
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoSplitter";

	private JQueryAjaxBehavior onExpandAjaxBehavior;
	private JQueryAjaxBehavior onCollapseAjaxBehavior;

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 */
	public SplitterBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SplitterBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onExpandAjaxBehavior = this.newOnExpandAjaxBehavior(this);
		component.add(this.onExpandAjaxBehavior);

		this.onCollapseAjaxBehavior = this.newOnCollapseAjaxBehavior(this);
		component.add(this.onCollapseAjaxBehavior);
	}

	/**
	 * Expands the specified pane<br/>
	 * <b>Note: </b> Invoking the method will not trigger an expand event.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param pane the pane selector (ie: "#bottom")
	 */
	public void expand(AjaxRequestTarget target, String pane)
	{
		target.appendJavaScript(String.format("$('%s').data('%s').expand('%s');", this.selector, METHOD, pane));
	}

	/**
	 * Collapses the specified pane<br/>
	 * <b>Note: </b> Invoking the method will not trigger a collapse event.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param pane the pane selector (ie: "#bottom")
	 */
	public void collapse(AjaxRequestTarget target, String pane)
	{
		target.appendJavaScript(String.format("$('%s').data('%s').collapse('%s');", this.selector, METHOD, pane));
	}

	// Properties //

	@Override
	public boolean isExpandEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isCollapseEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("expand", this.onExpandAjaxBehavior.getCallbackFunction());
		this.setOption("collapse", this.onCollapseAjaxBehavior.getCallbackFunction());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ExpandEvent)
		{
			this.onExpand(target, ((ExpandEvent) event).getPaneId());
		}

		if (event instanceof CollapseEvent)
		{
			this.onCollapse(target, ((CollapseEvent) event).getPaneId());
		}
	}

	@Override
	public void onExpand(AjaxRequestTarget target, String paneId)
	{
		// noop
	}

	@Override
	public void onCollapse(AjaxRequestTarget target, String paneId)
	{
		// noop
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'expand' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnExpandAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnExpandAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnExpandAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'collapse' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnCollapseAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCollapseAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnCollapseAjaxBehavior(source);
	}

	// Ajax classes //

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
					CallbackParameter.resolved("id", "e.pane.id") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ExpandEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'collapse' event
	 */
	protected static class OnCollapseAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnCollapseAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("id", "e.pane.id") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CollapseEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnExpandAjaxBehavior} callback
	 */
	protected static class ExpandEvent extends JQueryEvent
	{
		private final String paneId;

		public ExpandEvent()
		{
			super();

			this.paneId = RequestCycleUtils.getQueryParameterValue("id").toString("");
		}

		/**
		 * Gets the html-id
		 *
		 * @return the html-id
		 */
		public String getPaneId()
		{
			return this.paneId;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnCollapseAjaxBehavior} callback
	 */
	protected static class CollapseEvent extends JQueryEvent
	{
		private final String paneId;

		public CollapseEvent()
		{
			super();

			this.paneId = RequestCycleUtils.getQueryParameterValue("id").toString("");
		}

		/**
		 * Gets the html-id
		 *
		 * @return the html-id
		 */
		public String getPaneId()
		{
			return this.paneId;
		}
	}
}
