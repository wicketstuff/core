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

import java.util.ArrayList;
import java.util.List;

/**
 * Button set to be used in a {@link MessageDialog} or {@link MessageFormDialog}
 *
 */
public enum DialogButtons
{
	OK(new DialogButton(AbstractDialog.OK, AbstractDialog.LBL_OK)),
	OK_CANCEL(new DialogButton(AbstractDialog.OK, AbstractDialog.LBL_OK), new DialogButton(AbstractDialog.CANCEL, AbstractDialog.LBL_CANCEL)),

	YES_NO(new DialogButton(AbstractDialog.YES, AbstractDialog.LBL_YES), new DialogButton(AbstractDialog.NO, AbstractDialog.LBL_NO)),
	YES_NO_CANCEL(new DialogButton(AbstractDialog.YES, AbstractDialog.LBL_YES), new DialogButton(AbstractDialog.NO, AbstractDialog.LBL_NO), new DialogButton(AbstractDialog.CANCEL, AbstractDialog.LBL_CANCEL));

	private final List<DialogButton> buttons;

	private DialogButtons(DialogButton... buttons)
	{
		this.buttons = new ArrayList<DialogButton>();

		for (DialogButton button : buttons)
		{
			this.buttons.add(button);
		}
	}

	/**
	 * Gets the list of buttons
	 * 
	 * @return the {@link List} of {@link DialogButton}</code>s</code>
	 */

	public List<DialogButton> toList()
	{
		return this.buttons;
	}
}
