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
package org.wicketstuff.kendo.ui.widget.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.IJQueryWidget.JQueryWidget;
import org.wicketstuff.kendo.ui.KendoUIBehavior;

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
		return new IModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return ComponentRenderer.renderComponent(tooltip).toString();
			}
		};
	}

	private final IModel<String> model;

	/**
	 * Constructor<br>
	 * The {@code title} attribute will serve as tooltip content
	 */
	public TooltipBehavior()
	{
		this(new Options());
	}

	/**
	 * Constructor<br>
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
	 * @param component a component that should be used as a tooltip
	 */
	public TooltipBehavior(final Component component)
	{
		this(asModel(component), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param component a component that should be used as a tooltip
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(final Component component, Options options)
	{
		this(asModel(component), options);
	}

	/**
	 * Constructor
	 *
	 * @param model a model providing the text that should be used as a tooltip
	 */
	public TooltipBehavior(IModel<String> model)
	{
		this(model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param model a model providing the text that should be used as a tooltip
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(IModel<String> model, Options options)
	{
		super(null, METHOD, options);

		this.model = model;
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
		String content = this.model.getObject();

		if (content != null)
		{
			this.setOption("content", Options.asString(content));
		}

		super.onConfigure(component);
	}
}
