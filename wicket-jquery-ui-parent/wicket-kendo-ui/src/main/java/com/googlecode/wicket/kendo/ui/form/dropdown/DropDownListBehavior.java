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
package com.googlecode.wicket.kendo.ui.form.dropdown;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.event.ISelectionChangedListener;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior.ChangeEvent;

/**
 * Provides a {@value METHOD} {@link JQueryBehavior}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class DropDownListBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoDropDownList";

	private final ISelectionChangedListener listener;
	private JQueryAjaxBehavior onChangeAjaxBehavior = null;

	public DropDownListBehavior(String selector, ISelectionChangedListener listener)
	{
		this(selector, METHOD, listener);
	}

	public DropDownListBehavior(String selector, String method, ISelectionChangedListener listener)
	{
		super(selector, method);

		this.listener = Args.notNull(listener, "listener");
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.listener.isSelectionChangedEventEnabled())
		{
			this.onChangeAjaxBehavior = new OnChangeAjaxBehavior(this);
			component.add(this.onChangeAjaxBehavior);
		}
	}

	@Override
	public void onConfigure(Component component)
	{
		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			this.listener.onSelectionChanged(target);
		}
	}
}
