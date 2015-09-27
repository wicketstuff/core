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
package com.googlecode.wicket.kendo.ui.widget.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior
 *
 * @since 6.20.0
 */
public class TooltipBehavior extends KendoUIBehavior
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoTooltip";

	/**
	 * Gets a new model that represent the content from the tooltip rendering
	 */
	private static IModel<String> asModel(final Component tooltip)
	{
		return new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return ComponentRenderer.renderComponent(tooltip).toString();
			}
		};
	}

	private final IModel<String> tooltip;

	/**
	 * Constructor<br/>
	 * The {@code title} attribute will serve as tooltip content
	 */
	public TooltipBehavior()
	{
		this(new Options());
	}

	/**
	 * Constructor<br/>
	 * The {@code title} attribute will serve as tooltip content
	 * 
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(Options options)
	{
		this(new Model<String>(null), options);
	}

	/**
	 * Constructor
	 *
	 * @param tooltip a component that should be used as a tooltip
	 */
	public TooltipBehavior(final Component tooltip)
	{
		this(asModel(tooltip), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param tooltip a component that should be used as a tooltip
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(final Component tooltip, Options options)
	{
		this(asModel(tooltip), options);
	}

	/**
	 * Constructor
	 *
	 * @param tooltip a model providing the text that should be used as a tooltip
	 */
	public TooltipBehavior(IModel<String> tooltip)
	{
		this(tooltip, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param tooltip a model providing the text that should be used as a tooltip
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(IModel<String> tooltip, Options options)
	{
		super(null, METHOD, options);

		this.tooltip = tooltip;
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// seems that kendo-ui tooltip only apply to the component it is bound to (applying to document doesn't have any effect)
		this.selector = JQueryWidget.getSelector(component);
	}

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		String content = this.tooltip.getObject();

		if (content != null)
		{
			this.setOption("content", Options.asString(content));
		}
	}
}
