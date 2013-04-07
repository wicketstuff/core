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
package com.googlecode.wicket.jquery.ui.interaction.resizable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery UI resizable {@link JQueryPanel}.<br/>
 * <br/>
 * This class is marked as <code>abstract</code> because no markup is associated with this panel. It is up to the user to supply the corresponding markup.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class ResizablePanel extends JQueryPanel implements IResizableListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public ResizablePanel(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public ResizablePanel(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public ResizablePanel(String id, IModel<?> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public ResizablePanel(String id, IModel<?> model, Options options)
	{
		super(id, model, options);
	}

	// IResizableListener //
	@Override
	public boolean isResizeStartEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isResizeStopEventEnabled()
	{
		return false;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height)
	{
	}

	@Override
	public void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ResizableBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				ResizablePanel.this.onConfigure(this);
			}

			@Override
			public boolean isResizeStartEventEnabled()
			{
				return ResizablePanel.this.isResizeStartEventEnabled();
			}

			@Override
			public boolean isResizeStopEventEnabled()
			{
				return ResizablePanel.this.isResizeStopEventEnabled();
			}

			@Override
			public void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height)
			{
				ResizablePanel.this.onResizeStart(target, top, left, width, height);
			}

			@Override
			public void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height)
			{
				ResizablePanel.this.onResizeStop(target, top, left, width, height);
			}
		};
	}
}
