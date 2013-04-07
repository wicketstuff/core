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
package com.googlecode.wicket.jquery.core.ajax;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Base class for implementing AJAX POST calls on JQuery {@link Component}<br/>
 * This behavior post a {@link FormComponent} so the receiver of the event can get the component back.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class JQueryAjaxPostBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private final FormComponent<?>[] components;

	/**
	 * Constructor
	 * @param source {@link Component} to which the event returned by {@link #newEvent()} will be broadcasted.
	 * @param components the form components to post.
	 */
	public JQueryAjaxPostBehavior(IJQueryAjaxAware source, FormComponent<?>... components)
	{
		super(source);

		this.components = components;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		attributes.setMethod(Method.POST);

		if (this.components.length > 0)
		{
			StringBuilder serialize = new StringBuilder("var result = [];");

			for (FormComponent<?> component: this.components)
			{
				serialize.append("result = result.concat(Wicket.Form.serializeElement(Wicket.$('").append(component.getMarkupId()).append("')));");
			}

			serialize.append("return result;");

			List<CharSequence> dynamicParameters = attributes.getDynamicExtraParameters();
			dynamicParameters.add(serialize);
		}
	}
}
