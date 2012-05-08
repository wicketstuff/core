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
package com.googlecode.wicket.jquery.ui.dialog;

import java.util.Arrays;
import java.util.List;

/**
 * Button set to be used in a {@link MessageDialog} or {@link MessageFormDialog}
 *
 */
public enum DialogButtons
{
	OK(AbstractDialog.BTN_OK),
	OK_CANCEL(AbstractDialog.BTN_OK, AbstractDialog.BTN_CANCEL),

	YES_NO(AbstractDialog.BTN_YES, AbstractDialog.BTN_NO),
	YES_NO_CANCEL(AbstractDialog.BTN_YES, AbstractDialog.BTN_NO, AbstractDialog.BTN_CANCEL);

	private final String[] buttons;

	private DialogButtons(String... buttons)
	{
		this.buttons = buttons;
	}

	public List<String> toList()
	{
		return Arrays.asList(this.buttons);
	}
}
