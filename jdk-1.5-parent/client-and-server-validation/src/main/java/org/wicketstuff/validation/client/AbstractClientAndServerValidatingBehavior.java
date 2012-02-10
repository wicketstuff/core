/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 *
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
package org.wicketstuff.validation.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.JavaScriptUtils;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;

/**
 * Abstract validating behavior that all ClientAndServer...Behaviors can extend to provide the base
 * functionality needed to validate on the client side where possible.
 * 
 * @author Jeremy Thomerson
 */
public abstract class AbstractClientAndServerValidatingBehavior<T> extends Behavior
{
	private static final long serialVersionUID = 1L;

	private FormComponent<T> mComponent;
	private Form<?> mForm;

	public AbstractClientAndServerValidatingBehavior(Form<?> form)
	{
		if (form == null)
		{
			throw new IllegalArgumentException(
				"ClientAndServer validating behaviors require a non-null form");
		}
		mForm = form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void bind(Component component)
	{
		super.bind(component);
		checkComponentIsFormComponent(component);
		mComponent = (FormComponent<T>)component;

		addServerSideValidator(mComponent);

		// make sure that both IDs are rendered
		mComponent.setOutputMarkupId(true);
		mForm.setOutputMarkupId(true);
	}

	protected final void checkComponentIsFormComponent(Component component)
	{
		if (component instanceof FormComponent == false)
		{
			throw new IllegalArgumentException("This behavior [" + Classes.simpleName(getClass()) +
				"] can only be added to a FormComponent");
		}
	}


	@Override
	public final void renderHead(Component c, IHeaderResponse response)
	{
		super.renderHead(c, response);

		// add our validation javascript file
		response.renderJavaScriptReference(new PackageResourceReference(getClass(), "validation.js"));

		// add a trigger that will add our validation to the forms' onSubmit methods
		response.renderOnLoadJavaScript("ClientAndServerValidator.addFormOnloadEvents();");

		CharSequence formID = jsEscape(mForm.getMarkupId());
		CharSequence compID = jsEscape(mComponent.getMarkupId());
		String message = Application.get()
			.getResourceSettings()
			.getLocalizer()
			.getString(getResourceKey(), mComponent);
		Map<String, Object> vars = variablesMap(mForm, mComponent);
		boolean thrExc = Application.get()
			.getResourceSettings()
			.getThrowExceptionOnMissingResource();
		MapVariableInterpolator mvi = new MapVariableInterpolator(message, vars, thrExc);
		CharSequence escapedMessage = jsEscape(mvi.toString());

		String validator = createValidatorConstructorJavaScript(formID, compID, escapedMessage);
		String js = "ClientAndServerValidator.registerValidator(" + validator + ");";
		response.renderOnDomReadyJavaScript(js.toString());
	}

	protected final CharSequence jsEscape(CharSequence js)
	{
		// TODO: this may need more escaping
		return JavaScriptUtils.escapeQuotes(js);
	}

	protected String createValidatorConstructorJavaScript(CharSequence formID, CharSequence compID,
		CharSequence escapedMessage)
	{
		return "new " + getValidatorJSClassName() + "('" + formID + "', '" + compID + "', '" +
			escapedMessage + "')";
	}

	protected String getValidatorJSClassName()
	{
		return Classes.simpleName(getClass());
	}

	protected Map<String, Object> variablesMap(Form<?> form, FormComponent<T> component)
	{
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("form", form.getMarkupId());
		vars.put("label", component.getMarkupId());
		vars.put("input", "{VAL}");
		return vars;
	}

	/**
	 * @return the resource key where the 'failed' message for this validator can be found
	 */
	protected String getResourceKey()
	{
		return Classes.simpleName(getClass());
	}

	protected abstract void addServerSideValidator(FormComponent<T> component);

}
