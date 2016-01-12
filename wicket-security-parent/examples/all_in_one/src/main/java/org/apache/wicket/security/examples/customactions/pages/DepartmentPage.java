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
package org.apache.wicket.security.examples.customactions.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.security.checks.InverseSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.security.examples.customactions.authorization.EnableCheck;
import org.apache.wicket.security.examples.customactions.authorization.OrganizationCheck;
import org.apache.wicket.security.examples.customactions.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.customactions.entities.Department;

/**
 * Page for showing some department info. Only a user with organization rights is allowed
 * to edit.
 * 
 * @author marrink
 * 
 */
public class DepartmentPage extends SecurePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construct.
	 * 
	 * @param department
	 */
	public DepartmentPage(Department department)
	{
		add(new ButtonContainer("buttoncontainer", ButtonContainer.BUTTON_DEPARTMENTS));
		SecureForm<Department> form =
			new SecureForm<Department>("form", new CompoundPropertyModel<Department>(department));
		// make sure we have organization rights
		form.setSecurityCheck(new OrganizationCheck(form));
		add(form);
		// no need to secure the child components, the form will automatically
		// disable them
		// when required
		form.add(new TextField<String>("name"));
		form.add(new TextArea<String>("description"));
		form.add(new Button("button")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				setResponsePage(DepartmentsPage.class);
			}
		});
		Label label = new Label("label", "You do not have sufficient rights to make changes");
		// make the label show up when the form is disabled
		// notice the use of security check chaining.
		SecureComponentHelper.setSecurityCheck(label, new InverseSecurityCheck(new EnableCheck(form
			.getSecurityCheck())));
		form.add(label);

	}
}
