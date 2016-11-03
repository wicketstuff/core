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
package com.googlecode.wicket.jquery.core.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Payload object that supports {@link AjaxRequestTarget}
 * 
 * @author Sebastien Briquet - sebfz1
 * @see Component#send(org.apache.wicket.event.IEventSink, org.apache.wicket.event.Broadcast, Object)
 */
public class AjaxPayload
{
	private final AjaxRequestTarget target;

	/**
	 * Constructor
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public AjaxPayload(AjaxRequestTarget target)
	{
		this.target = target;
	}

	// Methods //

	/**
	 * Helper method that reloads a component<br/>
	 * Similar to {@link AjaxRequestTarget#add(Component...)}
	 * 
	 * @param components the {@link Component}{@code s} to add
	 */
	public void reload(Component... components)
	{
		this.getTarget().add(components);
	}

	// Properties //

	public AjaxRequestTarget getTarget()
	{
		return this.target;
	}
}
