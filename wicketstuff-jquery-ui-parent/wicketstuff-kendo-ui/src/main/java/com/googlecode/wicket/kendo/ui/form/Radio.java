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
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;

/**
 * Provides a {@link org.apache.wicket.markup.html.form.Radio} with the Kendo-ui style<br>
 * <br>
 * <b>Note:</b> {@link Radio} is not a {@link IJQueryWidget} (no corresponding widget is supplied by Kendo UI)<br>
 * It means that required Kendo UI dependencies (javascript/stylesheet) are not automatically added.
 *
 * @param <T> the model object type
 * @author Tedlabs46
 *
 */
public class Radio<T> extends org.apache.wicket.markup.html.form.Radio<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Radio(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Radio(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param group parent {@link RadioGroup} of this radio
	 */
	public Radio(String id, RadioGroup<T> group)
	{
		super(id, group);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param group parent {@link RadioGroup} of this radio
	 */
	public Radio(String id, IModel<T> model, RadioGroup<T> group)
	{
		super(id, model, group);
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

		tag.append("class", "k-radio", " ");
	}

	/**
	 * Provides the label for the {@link Radio}.<br>
	 * It should be applied on a {@code label} tag
	 */
	public static class Label extends org.apache.wicket.markup.html.basic.Label
	{
		private static final long serialVersionUID = 1L;

		private final Radio<?> radio;

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param radio the {@link Radio}
		 */
		public Label(String id, Radio<?> radio)
		{
			super(id);

			this.radio = radio;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param label the label
		 * @param radio the {@link Radio}
		 */
		public Label(String id, Serializable label, Radio<?> radio)
		{
			super(id, label);

			this.radio = radio;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param model the label model
		 * @param radio the {@link Radio}
		 */
		public Label(String id, IModel<?> model, Radio<?> radio)
		{
			super(id, model);

			this.radio = radio;
		}

		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);

			tag.put("for", this.radio.getMarkupId());
			tag.append("class", "k-radio-label", " ");
		}
	}
}
