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
package com.googlecode.wicket.jquery.ui.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Base class for implementing AJAX POST on JQuery Form (submit)<br/>
 *
 * @author Sebastien Briquet - sebfz1
 * @deprecated will probably be never used as it post the whole form. It means that source is interested by receiving several {@link FormComponent}<code>s</code>
 *
 */
@Deprecated
public abstract class JQueryAjaxFormBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final Form<?> form;

	/**
	 *
	 * @param source {@link Component} to which the event returned by {@link #newEvent(AjaxRequestTarget)} will be broadcasted.
	 * @param form the {@link Form} to post. If null, the behavior fallback to an AJAX GET callback script
	 */
	public JQueryAjaxFormBehavior(Component source, Form<?> form)
	{
		super(source);

		this.form = form;
	}

	@Override
	public CharSequence getCallbackScript()
	{
		if (this.form != null)
		{
			final CharSequence script = String.format("wicketSubmitFormById('%s', '%s', null", this.form.getMarkupId(), this.getCallbackUrl());
			return this.generateCallbackScript(script) + "return false;";
		}

		return super.getCallbackScript();
	}
}
