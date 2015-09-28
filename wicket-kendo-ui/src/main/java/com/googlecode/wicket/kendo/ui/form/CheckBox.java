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
package com.googlecode.wicket.kendo.ui.form;

import java.io.Serializable;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;

/**
 * Provides a {@link org.apache.wicket.markup.html.form.CheckBox} with the Kendo-ui style<br/>
 * <br/>
 * <b>Note:</b> {@link CheckBox} is not a {@link IJQueryWidget} (no corresponding widget is supplied by Kendo UI)<br/>
 * It means that required Kendo UI dependencies (javascript/stylesheet) are not automatically added.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CheckBox extends org.apache.wicket.markup.html.form.CheckBox
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public CheckBox(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public CheckBox(String id, IModel<Boolean> model)
	{
		super(id, model);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.append("class", "k-checkbox", " ");
	}

	/**
	 * Provides the label for the {@link CheckBox}.<br/>
	 * It should be applied on a {@code label} tag
	 */
	public static class Label extends org.apache.wicket.markup.html.basic.Label
	{
		private static final long serialVersionUID = 1L;

		private final CheckBox checkbox;

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 */
		public Label(String id, CheckBox checkbox)
		{
			super(id);

			this.checkbox = checkbox;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param label the label
		 */
		public Label(String id, Serializable label, CheckBox checkbox)
		{
			super(id, label);

			this.checkbox = checkbox;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param model the label model
		 */
		public Label(String id, IModel<?> model, CheckBox checkbox)
		{
			super(id, model);

			this.checkbox = checkbox;
		}

		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);

			tag.put("for", this.checkbox.getMarkupId());
			tag.append("class", "k-checkbox-label", " ");
		}
	}
}
