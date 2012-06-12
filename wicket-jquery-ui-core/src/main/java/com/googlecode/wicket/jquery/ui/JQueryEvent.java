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
package com.googlecode.wicket.jquery.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

/**
 * Base class for a jQuery event object that will be broadcasted by {@link JQueryAjaxBehavior}<code>s</code>.
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryEvent
{
	private final AjaxRequestTarget target;

	/**
	 * Constructor.
	 * @param target the {@link AjaxRequestTarget}
	 */
	public JQueryEvent(AjaxRequestTarget target)
	{
		this.target = target;
	}
	
	/**
	 * Get the {@link AjaxRequestTarget}
	 * @return the {@link AjaxRequestTarget}
	 */
	public AjaxRequestTarget getTarget()
	{
		return this.target;
	}
}
