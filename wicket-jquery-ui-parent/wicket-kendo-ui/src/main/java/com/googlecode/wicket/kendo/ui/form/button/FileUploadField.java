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
package com.googlecode.wicket.kendo.ui.form.button;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI upload field based on the built-in FileUploadField
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class FileUploadField extends org.apache.wicket.markup.html.form.upload.FileUploadField implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	
	public static final String METHOD = "kendoUpload";

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public FileUploadField(String id)
	{
		super(id);
	}

	// Properties //

	/**
	 * Gets the icon being displayed in the button
	 *
	 * @return {@link KendoIcon#NONE} by default
	 */
	protected String getIcon()
	{
		return KendoIcon.FILE; // used in #onConfigure
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		final String icon = this.getIcon();

		if (!KendoIcon.isNone(icon))
		{
			behavior.setOption("icon", Options.asString(icon));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public KendoUIBehavior newWidgetBehavior(String selector)
	{
		return new KendoUIBehavior(selector, METHOD); // TODO FileUploadFieldBehavior
	}
}
