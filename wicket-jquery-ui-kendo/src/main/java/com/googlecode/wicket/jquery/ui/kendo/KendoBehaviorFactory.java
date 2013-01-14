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
package com.googlecode.wicket.jquery.ui.kendo;

import org.apache.wicket.Component;

import com.googlecode.wicket.jquery.ui.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;


/**
 * Factory that provides {@link JQueryBehavior}<code>s</code>, which may be used in widgets.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public final class KendoBehaviorFactory
{
	/**
	 * Gets a new {@link JQueryBehavior} that shows the widgets
	 *
	 * @param component the {@link Component}
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newShowBehavior(Component component)
	{
		return new JQueryBehavior(JQueryWidget.getSelector(component)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $()
			{
				return String.format("jQuery(function() { jQuery('%s').closest('.k-widget').show(); });", this.selector);
			}

		};
	}

	/**
	 * Gets a new {@link JQueryBehavior} that hides the widgets
	 *
	 * @param component the {@link Component}
	 * @return the {@link JQueryBehavior}
	 */
	public static JQueryBehavior newHideBehavior(Component component)
	{
		return KendoBehaviorFactory.newHideBehavior(JQueryWidget.getSelector(component));
	}

	/**
	 * Gets a new {@link JQueryBehavior} that hides the widgets
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
				return String.format("jQuery(function() { jQuery('%s').closest('.k-widget').hide(); });", this.selector);
			}
		};
	}

	/**
	 * Factory class
	 */
	private KendoBehaviorFactory()
	{
	}
}
