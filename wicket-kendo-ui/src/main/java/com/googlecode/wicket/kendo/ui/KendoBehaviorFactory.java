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
package com.googlecode.wicket.kendo.ui;

import org.apache.wicket.Component;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;

/**
 * Factory that provides {@link JQueryBehavior}{@code s}, which may be used in widgets.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public final class KendoBehaviorFactory
{
	/**
	 * Factory class
	 */
	private KendoBehaviorFactory()
	{
	}

	/**
	 * Shows the kendo-ui widget using its reference
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param component the {@link Component} reference
	 */
	public static void show(IPartialPageRequestHandler handler, Component component)
	{
		KendoBehaviorFactory.show(handler, JQueryWidget.getSelector(component));
	}

	/**
	 * Shows the kendo-ui widget using its selector
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param selector the html selector (ie: '#myId')
	 */
	public static void show(IPartialPageRequestHandler handler, String selector)
	{
		handler.appendJavaScript(KendoBehaviorFactory.getShowStatement(selector));
	}

	/**
	 * Hides the kendo-ui widget using its reference
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param component the {@link Component} reference
	 */
	public static void hide(IPartialPageRequestHandler handler, Component component)
	{
		KendoBehaviorFactory.hide(handler, JQueryWidget.getSelector(component));
	}

	/**
	 * Hides the kendo-ui widget using its selector
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param selector the html selector (ie: '#myId')
	 */
	public static void hide(IPartialPageRequestHandler handler, String selector)
	{
		handler.appendJavaScript(KendoBehaviorFactory.getHideStatement(selector));
	}

	/**
	 * Gets a new {@link JQueryBehavior} that shows the kendo-ui widget
	 *
	 * @param component the {@link Component}
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newShowBehavior(Component component)
	{
		return KendoBehaviorFactory.newShowBehavior(JQueryWidget.getSelector(component));
	}

	/**
	 * Gets a new {@link JQueryBehavior} that shows the kendo-ui widget
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newShowBehavior(String selector)
	{
		return new JQueryBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $()
			{
				return KendoBehaviorFactory.getShowStatement(this.selector);
			}
		};
	}

	/**
	 * Gets a new {@link JQueryBehavior} that hides the kendo-ui widget
	 *
	 * @param component the {@link Component}
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newHideBehavior(Component component)
	{
		return KendoBehaviorFactory.newHideBehavior(JQueryWidget.getSelector(component));
	}

	/**
	 * Gets a new {@link JQueryBehavior} that hides the kendo-ui widget
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newHideBehavior(String selector)
	{
		return new JQueryBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $()
			{
				return KendoBehaviorFactory.getHideStatement(this.selector);
			}
		};
	}

	/**
	 * Gets the jQuery 'show' statement for the component specified by the given selector
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @return the statement
	 */
	public static String getShowStatement(String selector)
	{
		return String.format("jQuery('%s').closest('.k-widget').show();", selector);
	}

	/**
	 * Gets the jQuery 'hide' statement for the component specified by the given selector
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @return the statement
	 */
	public static String getHideStatement(String selector)
	{
		return String.format("jQuery('%s').closest('.k-widget').hide();", selector);
	}
}
