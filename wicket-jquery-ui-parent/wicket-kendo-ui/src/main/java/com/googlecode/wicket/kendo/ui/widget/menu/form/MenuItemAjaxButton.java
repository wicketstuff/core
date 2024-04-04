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
package com.googlecode.wicket.kendo.ui.widget.menu.form;

import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.EventPropagation;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

/**
 * A specialization of {@link AjaxButton} that could be used in {@link MenuItemForm}.
 * 
 * @author Martin Grigorov - martin-g
 * @author Sebastien Briquet - sebfz1
 * @since 6.20.0
 */
public class MenuItemAjaxButton extends AjaxButton
{
	private static final long serialVersionUID = 1L;

	public MenuItemAjaxButton(String id)
	{
		super(id);
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		attributes.setPreventDefault(false); // wicket7
		attributes.setEventPropagation(EventPropagation.BUBBLE);
	}
}
