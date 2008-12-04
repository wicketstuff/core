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
package org.wicketstuff.push;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Defines an interface to trigger client updates on server side events.
 * <p>
 * This interface is very similar to the API of the {@link AjaxRequestTarget},
 * so users already familiar with {@link AjaxRequestTarget} should easily
 * find their way with this interface.
 * </p>
 * <p>
 * A {@link IChannelTarget} implementation can be used in {@link IChannelListener}
 * implementations.
 * </p>
 * 
 * @author Xavier Hanin
 * 
 * @see IChannelListener
 * @see IChannelService
 */
public interface IChannelTarget
{
	/**
	 * Adds a component to the list of components to be rendered
	 * 
	 * @param component
	 *            component to be rendered
	 */
	public void addComponent(Component component);
	
	/**
	 * Adds a component to the list of components to be rendered
	 * 
	 * @param markupId
	 *            id of client-side dom element that will be updated
	 * 
	 * @param component
	 *            component to be rendered
	 */
	public void addComponent(Component component, String markupId);
	
	/**
	 * Sets the focus in the browser to the given component.
	 * The markup id must be set.
	 * 
	 * @param component
	 * 			The component to get the focus.         
	 */
	public void focusComponent(Component component);
	
	/**
	 * Adds javascript that will be evaluated on the client side after
	 * components are replaced
	 * 
	 * @param javascript
	 */
	public void appendJavascript(String javascript);
	
	/**
	 * Adds javascript that will be evaluated on the client side before
	 * components are replaced
	 * 
	 * @param javascript
	 */
	public void prependJavascript(String javascript);
}
