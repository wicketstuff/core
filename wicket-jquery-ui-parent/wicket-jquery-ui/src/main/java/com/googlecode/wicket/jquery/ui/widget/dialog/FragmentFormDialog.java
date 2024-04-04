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

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;

/**
 * Provides a dialog having a content coming from a {@link Fragment}
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
public abstract class FragmentFormDialog<T extends Serializable> extends AbstractFormDialog<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public FragmentFormDialog(String id, String title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public FragmentFormDialog(String id, IModel<String> title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public FragmentFormDialog(String id, String title, IModel<T> model)
	{
		super(id, title, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public FragmentFormDialog(String id, IModel<String> title, IModel<T> model)
	{
		super(id, title, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentFormDialog(String id, String title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentFormDialog(String id, IModel<String> title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentFormDialog(String id, String title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentFormDialog(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	/**
	 * Initialize component
	 */
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newFragment("fragment"));
	}

	/**
	 * Factory method that returns a new {@link Fragment}
	 * @param id the component id to be used for the new fragment. ie: <code>new Fragment(id, "my-fragment", MyPage.this);</code>
	 * @return the {@link Fragment}
	 */
	protected abstract Fragment newFragment(String id);
}
