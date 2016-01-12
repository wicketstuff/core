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
package org.apache.wicket.security.examples.customactions.components.navigation;

import java.util.ArrayList;

import org.apache.wicket.Page;
import org.apache.wicket.security.examples.customactions.pages.DepartmentsPage;
import org.apache.wicket.security.examples.customactions.pages.OverviewPage;

/**
 * Custom menu.
 * 
 * @author marrink
 */
public class ButtonContainer extends
		org.apache.wicket.security.examples.components.navigation.ButtonContainer
{

	private static final long serialVersionUID = 1L;

	/**
	 * A button.
	 */
	public static final Integer BUTTON_OVERVIEW = new Integer(0);

	/**
	 * A button.
	 */
	public static final Integer BUTTON_DEPARTMENTS = new Integer(1);

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param selectedButton
	 */
	public ButtonContainer(String id, Integer selectedButton)
	{
		super(id, selectedButton);
	}

	/**
	 * @see org.apache.wicket.security.examples.components.navigation.ButtonContainer#setupButtons()
	 */
	@Override
	protected void setupButtons()
	{
		BUTTONS = new Integer[] {BUTTON_OVERVIEW, BUTTON_DEPARTMENTS};
		NAMES = new String[] {"Overview", "Departments"};
		PAGES = new ArrayList<Class< ? extends Page>>();
		PAGES.add(OverviewPage.class);
		PAGES.add(DepartmentsPage.class);
	}

}
