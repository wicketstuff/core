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

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;
import org.apache.wicket.security.examples.customactions.MyApplication;
import org.apache.wicket.security.examples.customactions.authorization.DepartmentLinkCheck;
import org.apache.wicket.security.examples.customactions.authorization.DepartmentModel;
import org.apache.wicket.security.examples.customactions.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.customactions.entities.Department;

/**
 * Page for showing the departments in our organization.
 * 
 * @author marrink
 */
public class DepartmentsPage extends SecurePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public DepartmentsPage()
	{
		add(new ButtonContainer("buttoncontainer", ButtonContainer.BUTTON_DEPARTMENTS));
		add(new ListView<Department>("departments", generateData())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Department> item)
			{
				item.add(new Label("name"));
				item.add(new Label("description"));
				if (item.getIndex() % 2 == 0)
					item.add(new SimpleAttributeModifier("class", "outside halfhour"));
				// Having a custom securitycheck is a bit of overkill,
				// especially because we are already filtering out the secure
				// departments. However should you replace the DepartmentModel
				// below for a regular CompoundPropertyModel (and thus showing
				// all departments), then the
				// securitycheck of the link kicks in preventing you from
				// clicking on the secure departments.
				SecurePageLink<Void> link = new SecurePageLink<Void>("link", new IPageLink()
				{
					private static final long serialVersionUID = 1L;

					public Page getPage()
					{
						return new DepartmentPage(item.getModelObject());
					}

					public Class< ? extends Page> getPageIdentity()
					{
						return DepartmentPage.class;
					}
				});
				link.setSecurityCheck(new DepartmentLinkCheck(link, DepartmentPage.class, item
					.getModelObject()));
				item.add(link);

			}

			@Override
			protected IModel<Department> getListItemModel(
					IModel< ? extends List<Department>> listViewModel, int index)
			{
				// by using a securemodel we are preventing the secure
				// departments from showing up.
				return new DepartmentModel(super.getListItemModel(listViewModel, index));
				// return new
				// CompoundPropertyModel(super.getListItemModel(listViewModel,
				// index));
			}
		});
	}

	/**
	 * Generate some random data
	 * 
	 * @return
	 */
	private List<Department> generateData()
	{
		return ((MyApplication) Application.get()).DEPARTMENTS;
	}
}
