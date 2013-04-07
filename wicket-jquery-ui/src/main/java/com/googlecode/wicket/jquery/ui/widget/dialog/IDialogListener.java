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

/**
 * TODO javadoc
 * @author Sebastien Briquet - sebfz1
 *
 */
interface IDialogListener
{
	/**
	 * Indicates whether the default close event (the click on the X-icon) is enabled
	 * If true, the {@link #onClose(AjaxRequestTarget, DialogButton)} event will be triggered, with a null {@link DialogButton}
	 *
	 * @return false by default
	 */
	boolean isDefaultCloseEventEnabled();

	/**
	 * Triggered when a button is clicked.
	 * This method may be overridden to handle button behaviors, but the dialog will not been closed until <code>super.onClick(event)</code> or {@link DialogBehavior#close(AjaxRequestTarget)} is called.
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button that closed the dialog
	 */
	void onClick(AjaxRequestTarget target, DialogButton button);
	//XXX: report as changes: public void onClick(DialogEvent event) -> onClick(AjaxRequestTarget target, DialogButton button);

	/**
	 * Triggered when the dialog closes.
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button that closed the dialog
	 */
	void onClose(AjaxRequestTarget target, DialogButton button);
}
