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
package com.googlecode.wicket.jquery.ui.interaction.behavior;

import org.apache.wicket.Component;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a jQuery UI position behavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.11.0
 */
public class PositionBehavior extends JQueryUIBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "position";

	private final Object object;

	/**
	 * Constructor
	 *
	 * @param object the position json object
	 */
	public PositionBehavior(Object object)
	{
		super(null, METHOD);

		this.object = object;
	}

	// Properties //

	@Override
	public boolean isTemporary(Component component)
	{
		return true; //TODO: test this
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.selector = JQueryWidget.getSelector(component);
	}

	@Override
	protected String $()
	{
		return String.format("jQuery('%s').%s(%s);", this.selector, METHOD, this.object);
	}
}
