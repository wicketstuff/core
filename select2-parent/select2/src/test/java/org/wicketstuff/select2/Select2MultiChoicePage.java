/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;

/**
 * @author lexx
 */
public class Select2MultiChoicePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	final TextField<String> city;

	final Select2MultiChoice<Country> country;

	final Form<Void> form;

	public Select2MultiChoicePage()
	{
		super();

		this.city = new TextField<>("city", new Model<String>());
		this.city.setRequired(true);

		this.country = new Select2MultiChoice<>("country", new CollectionModel<Country>(),
			new CountryChoiceProvider());
		this.country.setRequired(true);

		this.form = new Form<>("form");
		this.form.add(this.country);
		this.form.add(this.city);
		add(this.form);
	}
}
