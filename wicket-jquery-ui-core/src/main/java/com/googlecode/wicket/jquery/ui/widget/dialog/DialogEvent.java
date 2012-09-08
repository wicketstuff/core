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

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.JQueryEvent;

/**
 * Provides a dialog event that will be transmitted to the dialog {@link AbstractDialog}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class DialogEvent extends JQueryEvent
{
	private final DialogButton button;

	DialogEvent(AjaxRequestTarget target, DialogButton button)
	{
		super(target);
		
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
	
	/**
	 * Gets whether the specified button has been clicked 
	 * @param button
	 * @return true if the supplied button is not null and is equals to the button 
	 */
	public boolean isClicked(DialogButton button)
	{
		return (this.button != null) && this.button.equals(button);
	}
}
