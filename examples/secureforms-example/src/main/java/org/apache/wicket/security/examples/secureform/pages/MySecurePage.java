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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.security.components.markup.html.form.SecureTextField;

/**
 * This Secure Page contains secure form components.
 * The secure form components are visible but disabled for default users.
 * The admin user has specific 'rights' (as defined in the secure-rules.hive) and has the ability
 * to enter data and submit the form. 
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

	/**
	 * @param parameters
	 */
	public MySecurePage(PageParameters parameters)
	{
		super(parameters);
        SecureForm sampleForm = new SecureForm("sampleForm", new CompoundPropertyModel(this)) {
            @Override
            protected void onSubmit() {
                super.onSubmit();
            }
        };
        sampleForm.add(new SecureTextField("name"));
        sampleForm.add(new CheckBox("information"));

        add(sampleForm);

        // add the link back to the homepage
        add(new Link("toHomePage"){
            public void onClick() {
                setResponsePage(HomePage.class);
            }
        });

        // add the link to the login page, it is possible to login as another user
        add(new Link("toLogin") {
            public void onClick() {
                setResponsePage(LoginPage.class);
            }
        });
	}

    /* getters and setters in order to support the compound property model */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getInformation() {
        return this.information;
    }

    public void setInformation(boolean information) {
        this.information = information;   
    }
}