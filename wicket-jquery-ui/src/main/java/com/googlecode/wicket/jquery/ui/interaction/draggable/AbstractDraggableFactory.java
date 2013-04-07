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
package com.googlecode.wicket.jquery.ui.interaction.draggable;

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.interaction.selectable.SelectableDraggableFactory;

/**
 * Provides the ability to create a {@link Draggable} that is related to another component (defined by its selector).<br/>
 * <br/>
 * The common use case is to have the ability to drag multiple {@link Draggable} object.<br/>
 * To achieve this, the role of the jQuery helper is important (see {@link #getHelper(String)} implementation in {@link SelectableDraggableFactory})<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AbstractDraggableFactory implements IClusterable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the helper that might be used by the {@link Draggable}.<br/>
	 * The returned value is supplied to the {@link #create(String, String, String)} method.
	 */
	protected abstract String getHelper(String selector);

	/**
	 * Creates the new {@link Draggable} for the given selector
	 * @param id the markup id
	 * @param selector the related component's selector
	 * @return the {@link Draggable} object
	 */
	public final Draggable<?> create(String id, String selector)
	{
		return this.create(id, selector, this.getHelper(selector));
	}

	/**
	 * Creates the new {@link Draggable} by using the selector and/or the helper.<br/>
	 * <br/>
	 * A typical implementation is:<br/>
	 * <pre>
	 * return new Draggable<String>(id) {
	 *
	 * 	protected void onConfigure(JQueryBehavior behavior)
	 * 	{
	 * 		super.onConfigure(behavior);
	 *
	 * 		behavior.setOption("helper", helper);
	 * 	}
	 * };
	 * </pre>
	 *
	 * @param id the markup id
	 * @param selector the related component's selector
	 * @param helper the jQuery helper
	 *
	 * @return the {@link Draggable} object
	 */
	protected abstract Draggable<?> create(String id, String selector, final String helper);
}
