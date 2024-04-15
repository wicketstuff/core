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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Border Layout {@link WebMarkupContainer} based on vertical and horizontal {@link SplitterBehavior}{@code s}<br>
 * {@link #getVerticalPanes()} and {@link #getHorizontalPanes()} may be overridden to change the default layout<br>
 * <br>
 * <b>Note:</b> the {@link BorderLayout} IS a {@link WebMarkupContainer}. If you wish to apply a {@link BorderLayout} on an existing Page or an existing Panel, consider implementing the {@link IBorderLayout} interface.<br>
 * <br>
 * Alternatively, the HTML markup look like:
 *
 * <pre>
 * &lt;div wicket:id="layout"&gt;
 * 	&lt;div id="vertical"&gt;
 * 		&lt;div id="top"&gt;
 * 			&lt;br/&gt;
 * 		&lt;/div&gt;
 * 		&lt;div id="horizontal"&gt;
 * 			&lt;div id="left"&gt;
 * 				&lt;br/&gt;
 * 			&lt;/div&gt;
 * 			&lt;div id="center"&gt;
 * 				&lt;br/&gt;
 * 			&lt;/div&gt;
 * 			&lt;div id="right"&gt;
 * 				&lt;br/&gt;
 * 			&lt;/div&gt;
 * 		&lt;/div&gt;
 * 		&lt;div id="bottom"&gt;
 * 			&lt;br/&gt;
 * 		&lt;/div&gt;
 * 	&lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class BorderLayout extends WebMarkupContainer implements IBorderLayout, ISplitterListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public BorderLayout(String id)
	{
		super(id);
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

	// Methods //

	@Override
	public void addBorderLayout(MarkupContainer container)
	{
		container.add(this.newVerticalSplitterBehavior("#vertical", this.getVerticalPanes()));
		container.add(this.newHorizontalSplitterBehavior("#horizontal", this.getHorizontalPanes()));
	}

	/**
	 * Expands the specified pane<br>
	 * <b>Note: </b> Invoking the method will not trigger an expand event.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param selector the splitter selector (ie: "#vertical")
	 * @param pane the pane selector (ie: "#bottom")
	 */
	public void expand(IPartialPageRequestHandler handler, String selector, String pane)
	{
		handler.appendJavaScript(String.format("$('%s').data('%s').expand('%s');", selector, SplitterBehavior.METHOD, pane));
	}

	/**
	 * Collapses the specified pane<br>
	 * <b>Note: </b> Invoking the method will not trigger a collapse event.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param selector the splitter selector (ie: "#vertical")
	 * @param pane the pane selector (ie: "#bottom")
	 */
	public void collapse(IPartialPageRequestHandler handler, String selector, String pane)
	{
		handler.appendJavaScript(String.format("$('%s').data('%s').collapse('%s');", selector, SplitterBehavior.METHOD, pane));
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.addBorderLayout(this);
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

	/**
	 * Gets vertical panes in a JSON array
	 *
	 * @return by default: 15% - middle - 15%
	 */
	@Override
	public String getVerticalPanes()
	{
		return "[ { resizable: false, size: '15%' }, {  }, { collapsible: true, size: '15%' } ]";
	}

	/**
	 * Gets horizontal panes in a JSON array
	 *
	 * @return by default: 15% - center - 15%
	 */
	@Override
	public String getHorizontalPanes()
	{
		return "[ { size: '15%' }, { }, { size: '15%' } ]";
	}

	// Factories //

	/**
	 * Gets a new vertical {@link SplitterBehavior}
	 *
	 * @param selector the splitter's html selector (ie: "#myId")
	 * @param panes the vertical panes in a JSON array
	 * @return a new vertical {@link SplitterBehavior}
	 */
	protected SplitterBehavior newVerticalSplitterBehavior(String selector, String panes)
	{
		Options options = new Options();
		options.set("panes", panes);
		options.set("orientation", Options.asString("vertical"));

		return new SplitterBehavior(selector, options, this);
	}

	/**
	 * Gets a new horizontal {@link SplitterBehavior}
	 *
	 * @param selector the splitter's html selector (ie: "#myId")
	 * @param panes the horizontal panes in a JSON array
	 * @return a new horizontal {@link SplitterBehavior}
	 */
	protected SplitterBehavior newHorizontalSplitterBehavior(String selector, String panes)
	{
		Options options = new Options();
		options.set("panes", panes);

		return new SplitterBehavior(selector, options, this);
	}
}
