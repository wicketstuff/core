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
package com.googlecode.wicket.jquery.core;


import org.apache.wicket.Component;

/**
 * Specifies that the implementing {@link Component} class will use a {@link JQueryBehavior}<br/>
 * This interface is for best practice purpose only. Adding the {@link JQueryBehavior} to the {@link Component} is still required but the use of <code>JQueryWidget#newWidgetBehavior(Component)</code> can be made.<br/>
 * For instance:<br/>
 * <pre>
 *	public JQueryBehavior newWidgetBehavior(String selector)
 *	{
 *		return new JQueryBehavior(selector, "jquerymethod");
 *	}
 *
 *	protected void onInitialize()
 *	{
 *		super.onInitialize();
 *
 *		this.add(JQueryWidget.newWidgetBehavior(this));
 *	}
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IJQueryWidget
{
	/**
	 * Gets a new {@link JQueryBehavior} that should be bound to the {@link IJQueryWidget} object.
	 * @param selector the html selector (ie: '#myId')
	 * @return the {@link JQueryBehavior}
	 */
	JQueryBehavior newWidgetBehavior(String selector);

	public static class JQueryWidget
	{
		/**
		 * Helper method that returns the component's html selector (ie: '#myId').
		 * @param component the {@link Component}
		 * @return the html selector
		 */
		public static String getSelector(Component component)
		{
			return "#" + component.setOutputMarkupId(true).getMarkupId();
		}

		/**
		 * Factory method that gets a new {@link JQueryBehavior} by invoking {@link IJQueryWidget#newWidgetBehavior(String)} on the supplied widget.
		 * @param <T> the type
		 * @param widget the {@link IJQueryWidget} object
		 * @return the {@link JQueryBehavior}
		 */
		public static <T extends Component & IJQueryWidget> JQueryBehavior newWidgetBehavior(T widget)
		{
			return widget.newWidgetBehavior(JQueryWidget.getSelector(widget));
		}

		/**
		 * Factory method that gets a new {@link JQueryBehavior} by invoking {@link IJQueryWidget#newWidgetBehavior(String)} on the supplied widget.
		 * @param widget object implementing the {@link IJQueryWidget} interface.
		 * @param component the {@link Component} the selector will be calculated from.
		 * @return the {@link JQueryBehavior}
		 */
		public static JQueryBehavior newWidgetBehavior(IJQueryWidget widget, Component component)
		{
			return widget.newWidgetBehavior(JQueryWidget.getSelector(component));
		}

		/**
		 * Factory method that gets a new {@link JQueryBehavior} by invoking {@link IJQueryWidget#newWidgetBehavior(String)} on the supplied widget.
		 * @param widget object implementing the {@link IJQueryWidget} interface.
		 * @param selector the html selector (ie: '#myId')
		 * @return the {@link JQueryBehavior}
		 */
		public static JQueryBehavior newWidgetBehavior(IJQueryWidget widget, String selector)
		{
			return widget.newWidgetBehavior(selector);
		}
	}
}
