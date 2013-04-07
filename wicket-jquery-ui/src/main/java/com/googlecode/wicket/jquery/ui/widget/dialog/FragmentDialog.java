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
import org.apache.wicket.model.Model;

/**
 * Provides a dialog having a content coming from a {@link Fragment}
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
public abstract class FragmentDialog<T extends Serializable> extends AbstractDialog<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 */
	public FragmentDialog(String id, String title)
	{
		super(id, title);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 */
	public FragmentDialog(String id, IModel<String> title)
	{
		super(id, title);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param model the model to be used in the dialog.
	 */
	public FragmentDialog(String id, String title, Model<T> model)
	{
		super(id, title, model);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param model the model to be used in the dialog.
	 */
	public FragmentDialog(String id, IModel<String> title, Model<T> model)
	{
		super(id, title, model);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentDialog(String id, String title, boolean modal)
	{
		super(id, title, modal);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 */
	public FragmentDialog(String id, IModel<String> title, boolean modal)
	{
		super(id, title, modal);

		this.init();
	}

	/**
	 * Constructor
	 * @param id markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 * @param model the model to be used in the dialog
	 */
	public FragmentDialog(String id, String title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);

		this.init();
	}

	/**
	 * Constructor
	 * @param id markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 * @param model the model to be used in the dialog
	 */
	public FragmentDialog(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);

		this.init();
	}

	/**
	 * Initialize component
	 */
	private void init()
	{
		this.add(this.newFragment("fragment"));
	}

	/**
	 * Factory method that returns a new {@link Fragment}
	 * @param id the component id to be used for the new fragment. ie: <code>new Fragment(id, "my-fragment", MyPage.this);</code>
	 * @return the {@link Fragment}
	 */
	protected abstract Fragment newFragment(String id);
}
