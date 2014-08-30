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

	private JQueryAjaxBehavior onExpandBehavior;
	private JQueryAjaxBehavior onCollapseBehavior;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public SplitterBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
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

		component.add(this.onExpandBehavior = this.newExpandBehavior());
		component.add(this.onCollapseBehavior = this.newCollapseBehavior());
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

		this.setOption("expand", this.onExpandBehavior.getCallbackFunction());
		this.setOption("collapse", this.onCollapseBehavior.getCallbackFunction());
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
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'expand' callback
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newExpandBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("e"),
						CallbackParameter.resolved("id", "e.pane.id") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ExpandEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'collapse' callback
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newCollapseBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("e"),
						CallbackParameter.resolved("id", "e.pane.id") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new CollapseEvent();
			}
		};
	}

	// Events classes //

	/**
	 * An event object that will be broadcasted when a panes expands
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
	 * An event object that will be broadcasted when a panes collapses
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
