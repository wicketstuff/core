/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.stateless.demo;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.stateless.behaviors.StatelessAjaxFormComponentUpdatingBehavior;
import org.wicketstuff.stateless.components.StatelessAjaxFallbackLink;
import org.wicketstuff.stateless.components.StatelessAjaxSubmitLink;
import org.wicketstuff.stateless.components.StatelessIndicatingAjaxButton;
import org.wicketstuff.stateless.components.StatelessIndicatingAjaxFallbackLink;

/**
 * For testing only
 */
@StatelessComponent
public class HomePage extends WebPage {

	private static final String COUNTER_PARAM = "counter";

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		final Label c2 = new Label("c2", new AbstractReadOnlyModel<Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject() {
				final String counter = getParameter(parameters, COUNTER_PARAM);
				return counter != null ? Integer.parseInt(counter) : 0;
			}

		});
		final Link<?> c2Link = new StatelessAjaxFallbackLink<Void>("c2-link") {

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (target != null) {
					Integer counter = (Integer) c2.getDefaultModelObject();
					updateParams(getPageParameters(), counter);
					target.add(c2);
				}
			}
		};

		add(c2Link);
		add(c2.setOutputMarkupId(true));

		final String _a = getParameter(parameters, "a");
		final String _b = getParameter(parameters, "b");
		
		final TextField<String> a = new TextField<String>("name",
			new Model<String>(_a));
		final TextField<String> b = new TextField<String>("surname",
			new Model<String>(_b));

		final Form<String> form = new StatelessForm<String>("inputForm") {

			@Override
			protected void onSubmit() {
				
			}
			
		};
		final DropDownChoice<String> select = new DropDownChoice<String>("select",
				new Model<String>("2"), Arrays.asList(new String[] { "1", "2",
						"3" }));
		final Label selectedValue = new Label("selectedValue", "");
		add(selectedValue.setOutputMarkupId(true));
		
		select.add(new StatelessAjaxFormComponentUpdatingBehavior("change") 
		{

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				final String value = select.getModelObject();
				selectedValue.setDefaultModelObject("Selected value: " + value);
				target.add(selectedValue);
			}
		});
		
		form.add(a.setRequired(true));
		form.add(b.setRequired(true));
		
		final Component feedback = new FeedbackPanel("feedback");
		final Label submittedValues = new Label("submittedValues", "");
		
		form.add(feedback.setOutputMarkupId(true));
		form.add(submittedValues.setOutputMarkupId(true));
		
		form.add(new StatelessAjaxSubmitLink("submit"){
			@Override
			protected void onError(AjaxRequestTarget target)
			{
				super.onError(target);
				target.add(feedback);
			}
			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{		
				super.onSubmit(target);
				String values = "Your name is: " + a.getModelObject() +
					" " + b.getModelObject();
				submittedValues.setDefaultModelObject(values);
				target.add(feedback, submittedValues);
			}
		});
		
		add(form);

		add(select);
		
		
		add(new StatelessIndicatingAjaxFallbackLink("indicatingLink"){
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				try {
				    Thread.sleep(5000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		});
		
		StatelessForm indicatingForm = new StatelessForm("indicatingForm");
		
		add(indicatingForm);
		add(new StatelessIndicatingAjaxButton("indicatingButton", indicatingForm){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				try {
				    Thread.sleep(5000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		});
		
	}

	private String getParameter(final PageParameters parameters,
			final String key) {
		final StringValue value = parameters.get(key);

		if (value.isNull() || value.isEmpty()) {
			return null;
		}

		return value.toString();
	}
	
	protected final void updateParams(final PageParameters pageParameters, final int counter) {
		pageParameters.set(COUNTER_PARAM, Integer.toString(counter + 1));
	}
}
