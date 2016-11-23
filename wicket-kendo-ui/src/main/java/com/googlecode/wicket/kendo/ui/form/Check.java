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
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;

/**
 * Provides a {@link org.apache.wicket.markup.html.form.Check} with the Kendo-ui style<br>
 * <br>
 * <b>Note:</b> {@link Check} is not a {@link IJQueryWidget} (no corresponding widget is supplied by Kendo UI)<br>
 * It means that required Kendo UI dependencies (javascript/stylesheet) are not automatically added.
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Check<T> extends org.apache.wicket.markup.html.form.Check<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Check(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Check(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param group parent {@link CheckGroup} of this check
	 */
	public Check(String id, CheckGroup<T> group)
	{
		super(id, group);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param group parent {@link CheckGroup} of this check
	 */
	public Check(String id, IModel<T> model, CheckGroup<T> group)
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

		tag.append("class", "k-checkbox", " ");
	}

	/**
	 * Provides the label for the {@link Check}.<br>
	 * It should be applied on a {@code label} tag
	 */
	public static class Label extends org.apache.wicket.markup.html.basic.Label
	{
		private static final long serialVersionUID = 1L;

		private final Check<?> check;

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 */
		public Label(String id, Check<?> check)
		{
			super(id);

			this.check = check;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param label the label
		 */
		public Label(String id, Serializable label, Check<?> check)
		{
			super(id, label);

			this.check = check;
		}

		/**
		 * Constructor
		 * 
		 * @param id the markup id
		 * @param model the label model
		 */
		public Label(String id, IModel<?> model, Check<?> check)
		{
			super(id, model);

			this.check = check;
		}

		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);

			tag.put("for", this.check.getMarkupId());
			tag.append("class", "k-checkbox-label", " ");
		}
	}
}
