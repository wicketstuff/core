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
package org.wicketstuff.kendo.ui.datatable.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONFunction;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.jquery.core.ajax.JQueryAjaxBehavior;
import org.wicketstuff.kendo.ui.datatable.DataTable;

import com.github.openjson.JSONObject;

/**
 * Provides a command button object that can be used in {@link DataTable} column
 *
 * @author Sebastien Briquet - sebfz1
 */
public class CommandButton extends AbstractButton
{
	private static final long serialVersionUID = 1L;

	/** default model property */
	private static final String PROPERTY = "id";

	/**
	 * Constructor for either built-in commands or linked to 'id' property (default)
	 *
	 * @param name the button's name
	 */
	public CommandButton(String name)
	{
		super(name, PROPERTY);
	}

	/**
	 * Constructor for either built-in commands or linked to 'id' property (default)
	 *
	 * @param name the button's name
	 * @param text the button's text
	 */
	public CommandButton(String name, IModel<String> text)
	{
		super(name, text, PROPERTY);
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

	public String toString(JQueryAjaxBehavior behavior)
	{
		JSONObject object = this.toJSONObject();

		if (this.isEnabled() && behavior != null)
		{
			object.put("click", new JSONFunction(behavior.getCallbackFunction()));
		}

		return object.toString();
	}

	@Override
	public JSONObject toJSONObject()
	{
		JSONObject object = new JSONObject();

		object.put("name", this.getName());
		object.put("text", this.getTextModel().getObject());

		// css //
		if (!Strings.isEmpty(this.getCSSClass())) /* important */
		{
			object.put("className", this.getCSSClass());
		}

		// icon //
		if (!Strings.isEmpty(this.getIconClass())) /* important */
		{
			object.put("iconClass", this.getIconClass());
		}

		return object;
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
