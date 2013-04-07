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
package com.googlecode.wicket.jquery.ui.form.palette;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.Button.ButtonBehavior;

/**
 * Provides a Palette, with jQuery UI icon-buttons
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class Palette<T> extends org.apache.wicket.extensions.markup.html.form.palette.Palette<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id Component id
	 * @param choicesModel Model representing collection of all available choices
	 * @param choiceRenderer Render used to render choices. This must use unique IDs for the objects, not the index.
	 * @param rows Number of choices to be visible on the screen with out scrolling
	 * @param allowOrder Allow user to move selections up and down
	 */
	public Palette(String id, IModel<? extends Collection<? extends T>> choicesModel, IChoiceRenderer<T> choiceRenderer, int rows, boolean allowOrder)
	{
		super(id, choicesModel, choiceRenderer, rows, allowOrder);
	}

	/**
	 * @param id Component id
	 * @param model Model representing collection of user's selections
	 * @param choicesModel Model representing collection of all available choices
	 * @param choiceRenderer Render used to render choices. This must use unique IDs for the objects, not the index.
	 * @param rows Number of choices to be visible on the screen with out scrolling
	 * @param allowOrder Allow user to move selections up and down
	 */
	public Palette(String id, IModel<? extends List<? extends T>> model, IModel<? extends Collection<? extends T>> choicesModel, IChoiceRenderer<T> choiceRenderer, int rows, boolean allowOrder)
	{
		super(id, model, choicesModel, choiceRenderer, rows, allowOrder);
	}

	@Override
	protected Component newAddComponent()
	{
		Component component = super.newAddComponent();
		component.add(new ButtonBehavior(JQueryWidget.getSelector(component), JQueryIcon.CARAT_1_E));
		return component;
	}

	@Override
	protected Component newRemoveComponent()
	{
		Component component = super.newRemoveComponent();
		component.add(new ButtonBehavior(JQueryWidget.getSelector(component), JQueryIcon.CARAT_1_W));
		return component;
	}

	@Override
	protected Component newUpComponent()
	{
		Component component = super.newUpComponent();
		component.add(new ButtonBehavior(JQueryWidget.getSelector(component), JQueryIcon.CARAT_1_N));
		return component;
	}

	@Override
	protected Component newDownComponent()
	{
		Component component = super.newDownComponent();
		component.add(new ButtonBehavior(JQueryWidget.getSelector(component), JQueryIcon.CARAT_1_S));
		return component;
	}
}
