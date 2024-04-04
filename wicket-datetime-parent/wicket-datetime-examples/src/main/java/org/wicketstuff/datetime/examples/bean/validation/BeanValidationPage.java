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
package org.wicketstuff.datetime.examples.bean.validation;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.wicketstuff.datetime.StyleDateConverter;
import org.wicketstuff.datetime.examples.dates.DatesPage;
import org.wicketstuff.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

public class BeanValidationPage extends WebPage
{
	private static final long serialVersionUID = 1L;
	Person person = new Person();

	public BeanValidationPage()
	{
		add(new Link<Void>("datesPage")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(DatesPage.class);
			}
		});
		add(new FeedbackPanel("feedbackErrors", new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

		Form<?> form = new Form<Void>("form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				super.onSubmit();
				info("Form successfully submitted!");
			}
		};
		
		add(form);

		form.add(new TextField<>("name", new PropertyModel<String>(this, "person.name")).add(new PropertyValidator<>()));
		form.add(new TextField<>("phone", new PropertyModel<String>(this, "person.phone")).add(new PropertyValidator<>()));
		form.add(new TextField<>("email", new PropertyModel<String>(this, "person.email")).add(new PropertyValidator<>()));
		form.add(new DateTextField("birthdate", new PropertyModel<>(this, "person.birthdate"),
			new StyleDateConverter("S-", true)).add(new PropertyValidator<>()));
		form.add(new TextField<>("password", new PropertyModel<String>(this, "person.password")).add(new PropertyValidator<>()));
		
		add(new FeedbackPanel("feedbackSuccess", new ExactLevelFeedbackMessageFilter(FeedbackMessage.INFO)));
	}
}
