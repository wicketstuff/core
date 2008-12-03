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

import java.util.Map;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * Validates that the form component value has a length between the min and the max specified (inclusive).
 * 
 * @author Jeremy Thomerson
 */
public class ClientAndServerLengthBetweenValidatingBehavior extends AbstractClientAndServerValidatingBehavior {

	private static final long serialVersionUID = 1L;

	private final int mMin;
	private final int mMax;
	
	public ClientAndServerLengthBetweenValidatingBehavior(Form<?> form, int min, int max) {
		super(form);
		mMin = min;
		mMax = max;
	}

	protected void addServerSideValidator(FormComponent component) {
		component.add(StringValidator.lengthBetween(mMin, mMax));
	}
	
	@Override
	protected String createValidatorConstructorJavaScript(CharSequence formID, CharSequence compID, CharSequence escapedMessage) {
		String js = super.createValidatorConstructorJavaScript(formID, compID, escapedMessage);
		js = js + ".setMinimumLength(" + mMin + ").setMaximumLength(" + mMax + ")";
		return js;
	}
	
	@Override
	protected Map<String, Object> variablesMap(Form<?> form, FormComponent<?> component) {
		Map<String, Object> map = super.variablesMap(form, component);
		map.put("minimum", mMin);
		map.put("maximum", mMax);
		return map;
	}
	
	@Override
	protected String getResourceKey() {
		return "StringValidator.range";
	}
	
	@Override
	protected String getValidatorJSClassName() {
		return "StringLengthBetweenValidator";
	}

}
