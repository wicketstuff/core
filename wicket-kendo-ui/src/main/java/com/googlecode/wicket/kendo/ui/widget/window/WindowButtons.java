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
package com.googlecode.wicket.kendo.ui.widget.window;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides default sets of {@link WindowButton}<code>s</code> to be used in {@link Window}
 *
 * @author Sebastien Briquet - sebfz1
 */
public enum WindowButtons
{
	OK(MessageWindow.LBL_OK), // lf
	OK_CANCEL(WindowButton.of(MessageWindow.LBL_OK, KendoIcon.TICK, true), WindowButton.of(MessageWindow.LBL_CANCEL, KendoIcon.CANCEL, false)), // lf

	YES_NO(MessageWindow.LBL_YES, MessageWindow.LBL_NO), // lf
	YES_NO_CANCEL(WindowButton.of(MessageWindow.LBL_YES, true), WindowButton.of(MessageWindow.LBL_NO, true), WindowButton.of(MessageWindow.LBL_CANCEL, false));

	private final List<WindowButton> buttons = new ArrayList<WindowButton>();

	/**
	 * Constructor
	 * 
	 * @param labels the button labels
	 */
	private WindowButtons(String... labels)
	{
		for (String label : labels)
		{
			this.buttons.add(new WindowButton(label));
		}
	}

	/**
	 * Constructor
	 * 
	 * @param buttons the buttons
	 */
	private WindowButtons(WindowButton... buttons)
	{
		for (WindowButton button : buttons)
		{
			this.buttons.add(button);
		}
	}

	/**
	 * Gets the list of buttons
	 * 
	 * @return the {@link List} of {@link WindowButton}</code>s</code>
	 */
	public List<WindowButton> toList()
	{
		return this.buttons;
	}
}
