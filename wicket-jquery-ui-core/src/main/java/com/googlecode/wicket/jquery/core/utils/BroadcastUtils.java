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
package com.googlecode.wicket.jquery.core.utils;

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;

import com.googlecode.wicket.jquery.core.ajax.AjaxPayload;

/**
 * Utility class for handling for broadcasting {@link AjaxPayload}{@code s}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class BroadcastUtils
{
	/**
	 * Utility class
	 */
	private BroadcastUtils()
	{
		// noop
	}

	/**
	 * Sends an {@link AjaxPayload} in {@link Broadcast#BREADTH} mode
	 * 
	 * @param component the sink {@link Component}, likely a page
	 * @param payload the {@link AjaxPayload}
	 */
	public static void breadth(Component component, AjaxPayload payload)
	{
		component.send(component, Broadcast.BREADTH, payload);
	}

	/**
	 * Sends an {@link AjaxPayload} in {@link Broadcast#BUBBLE} mode
	 * 
	 * @param component the sink {@link Component}
	 * @param payload the {@link AjaxPayload}
	 */
	public static void bubble(Component component, AjaxPayload payload)
	{
		component.send(component, Broadcast.BUBBLE, payload);
	}

	/**
	 * Sends an {@link AjaxPayload} in {@link Broadcast#DEPTH} mode
	 * 
	 * @param component the sink {@link Component}
	 * @param payload the {@link AjaxPayload}
	 */
	public static void depth(Component component, AjaxPayload payload)
	{
		component.send(component, Broadcast.DEPTH, payload);
	}

	/**
	 * Sends an {@link AjaxPayload} in {@link Broadcast#EXACT} mode
	 * 
	 * @param component the sink {@link Component}
	 * @param payload the {@link AjaxPayload}
	 */
	public static void exact(Component component, AjaxPayload payload)
	{
		component.send(component, Broadcast.EXACT, payload);
	}
}
