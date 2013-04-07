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
package com.googlecode.wicket.jquery.ui.widget.accordion;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery accordion based on a {@link JQueryContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.0
 * @deprecated use <code>new JQueryBehavior("#myAccordion", "accordion")</code>, {@link AccordionBehavior} or {@link AccordionPanel} instead
 *
 * XXX: remove in next version (6.8.0?)
 * XXX: report as deprecated
 */
@Deprecated
public class Accordion extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Accordion(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Accordion(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	// Events //
	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return Collections.emptyList();
			}

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				Accordion.this.onConfigure(this);
			}

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
			}
		};
	}
}
