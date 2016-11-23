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
package com.googlecode.wicket.kendo.ui.datatable.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides a command button object that can be used in {@link DataTable} column
 *
 * @author Sebastien Briquet - sebfz1
 */
public class CommandButton extends AbstractButton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 */
	public CommandButton(String name)
	{
		super(name);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 */
	public CommandButton(String name, String property)
	{
		super(name, property);
	}

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param text the button's text
	 */
	public CommandButton(String name, IModel<String> text)
	{
		super(name, text);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 */
	public CommandButton(String name, IModel<String> text, String property)
	{
		super(name, text, property);
	}

	// Properties //

	/**
	 * {@inheritDoc}<br>
	 * 
	 * @see <a href="http://docs.telerik.com/kendo-ui/api/javascript/ui/grid#configuration-columns.command">http://docs.telerik.com/kendo-ui/api/javascript/ui/grid#configuration-columns.command</a>
	 */
	@Override
	public boolean isBuiltIn()
	{
		switch (this.getName())
		{
		case EDIT:
		case DESTROY:
			return true;
		default:
			break;
		}

		return false;
	}

	/**
	 * Gets the CSS class to be applied on the button
	 *
	 * @return the CSS class
	 */
	public String getCSSClass()
	{
		return "";
	}

	public String toString(JQueryAjaxBehavior behavior)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("{ ");
		BuilderUtils.append(builder, "name", this.getName());
		builder.append(", ");
		BuilderUtils.append(builder, "text", this.getText().getObject());

		String css = this.getCSSClass();

		if (!Strings.isEmpty(css)) /* important */
		{
			builder.append(", ");
			BuilderUtils.append(builder, "className", css);
		}

		if (behavior != null)
		{
			builder.append(", ");
			builder.append("'click': ").append(behavior.getCallbackFunction());
		}

		builder.append(" }");

		return builder.toString();
	}

	// Events //

	/**
	 * Triggered when the column-button is clicked
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param value the row's object value
	 */
	public void onClick(AjaxRequestTarget target, String value)
	{
		// noop
	}
}
