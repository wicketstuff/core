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
package com.googlecode.wicket.jquery.core.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Args;

/**
 * Specifies that a widget handles a selection-changed AJAX behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface ISelectionChangedListener extends IClusterable
{
	/**
	 * Indicates whether the 'change' event is enabled.<br/>
	 * If true, the {@link #onSelectionChanged(AjaxRequestTarget)} event will be triggered<br/>
	 *
	 * @return false by default
	 */
	boolean isSelectionChangedEventEnabled();

	/**
	 * Triggers when the selection has changed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	void onSelectionChanged(AjaxRequestTarget target);

	/**
	 * Wrapper/Delegate class for {@link ISelectionChangedListener}
	 *
	 * @author Sebastien Briquet - sebfz1
	 *
	 */
	class SelectionChangedListenerWrapper implements ISelectionChangedListener
	{
		private static final long serialVersionUID = 1L;

		private final ISelectionChangedListener listener;

		public SelectionChangedListenerWrapper(ISelectionChangedListener listener)
		{
			this.listener = Args.notNull(listener, "listener");
		}

		@Override
		public boolean isSelectionChangedEventEnabled()
		{
			return this.listener.isSelectionChangedEventEnabled();
		}

		@Override
		public void onSelectionChanged(AjaxRequestTarget target)
		{
			this.listener.onSelectionChanged(target);
		}
	}
}
