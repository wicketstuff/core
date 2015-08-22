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
package org.apache.wicket.security.examples.secureform.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.security.components.markup.html.form.SecureTextField;
import org.apache.wicket.security.components.markup.html.links.SecureAjaxLink;

/**
 * This Secure Page contains secure form components. The secure form components are
 * visible but disabled for default users. The admin user has specific 'rights' (as
 * defined in the secure-rules.hive) and has the ability to enter data and submit the
 * form.
 * 
 * @author Olger Warnier
 * 
 */
public class MySecurePage extends SecureWebPage
{

	private static final long serialVersionUID = 1L;

	/* form properties */
	private String name;

	private boolean information;

	private SecureForm<MySecurePage> sampleForm;

	private SecureTextField<String> secureNameField;

	private CheckBox informationCheckBox;

	/**
	 * @param parameters
	 */
	public MySecurePage(PageParameters parameters)
	{
		super(parameters);
		sampleForm =
			new SecureForm<MySecurePage>("sampleForm",
				new CompoundPropertyModel<MySecurePage>(this))
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit()
				{
					super.onSubmit();
				}
			};
		sampleForm.setOutputMarkupId(true);
		secureNameField = new SecureTextField<String>("name");
		secureNameField.setOutputMarkupId(true);
		sampleForm.add(secureNameField);
		informationCheckBox = new CheckBox("information");
		informationCheckBox.setOutputMarkupId(true);
		sampleForm.add(informationCheckBox);

		// Add the enable / disable link for the form. This is a admin only feature.
		SecureAjaxLink<Void> disableLink = new SecureAjaxLink<Void>("disableLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (secureNameField.isEnabled())
				{
					secureNameField.setEnabled(false);
				}
				else
				{
					secureNameField.setEnabled(true);
				}
				if (informationCheckBox.isEnabled())
				{
					informationCheckBox.setEnabled(false);
				}
				else
				{
					informationCheckBox.setEnabled(true);
				}
				target.addComponent(sampleForm);
			}
		};
		sampleForm.add(disableLink);

		add(sampleForm);

		// add the link back to the homepage
		add(new Link<Void>("toHomePage")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(HomePage.class);
			}
		});

		// add the link to the login page, it is possible to login as another user
		add(new Link<Void>("toLogin")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(LoginPage.class);
			}
		});
	}

	/* getters and setters in order to support the compound property model */
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean getInformation()
	{
		return this.information;
	}

	public void setInformation(boolean information)
	{
		this.information = information;
	}
}