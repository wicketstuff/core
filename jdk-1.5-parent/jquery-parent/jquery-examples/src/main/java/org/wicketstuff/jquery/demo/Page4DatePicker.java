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
package org.wicketstuff.jquery.demo;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.jquery.datepicker.DatePickerBehavior;
import org.wicketstuff.jquery.datepicker.DatePickerOptions;

@SuppressWarnings("serial")
public class Page4DatePicker extends PageSupport
{

	private static final String DATE_PATTERN = "mm/dd/yyyy";

	protected Date date01;
	protected Date date02;

	public Page4DatePicker() throws Exception
	{
		// note: a Converter for Date.class is registered (see DemoApplication)


		final DateTextField date01TextField = new DateTextField("date01")
		{

			@Override
			public String getTextFormat()
			{
				return DATE_PATTERN;
			}
		};
		date01TextField.setMarkupId("date01");
		DatePickerOptions datePickerOptions = new DatePickerOptions().clickInput(true)
			.allowDateInPast(true, DATE_PATTERN);
		date01TextField.add(new DatePickerBehavior(datePickerOptions));

		final Form<Page4DatePicker> form = new Form<Page4DatePicker>("myForm",
			new CompoundPropertyModel<Page4DatePicker>(this))
		{
			@Override
			protected void onSubmit()
			{
				info("Date is set to :" + date01);
			}
		};

		add(form);

		form.add(date01TextField);
	}
}
