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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;

/**
 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'click' event of {@link DialogButton}{@code s}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ButtonAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final DialogButton button;

	/**
	 * Constructor
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the {@link DialogButton} to attach to the {@link ClickEvent}
	 */
	public ButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
	{
		super(source);

		this.button = button;
	}

	/**
	 * Gets the {@link DialogButton}
	 * @return the {@link DialogButton}
	 */
	public DialogButton getButton()
	{
		return this.button;
	}

	@Override
	protected JQueryEvent newEvent()
	{
		return new ClickEvent(this.button);
	}


	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link ButtonAjaxBehavior} callback
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final DialogButton button;

		public ClickEvent(DialogButton button)
		{
			super();

			this.button = button;
		}

		/**
		 * Get the button that has been attached to this event object
		 * @return the button
		 */
		public DialogButton getButton()
		{
			return this.button;
		}
	}
}
