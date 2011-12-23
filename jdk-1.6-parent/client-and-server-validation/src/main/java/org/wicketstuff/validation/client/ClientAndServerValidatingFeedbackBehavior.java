/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 *
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
package org.wicketstuff.validation.client;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.resource.CoreLibrariesContributor;

/**
 * By adding this behavior to a feedback panel or other WebMarkupContainer, the client and server
 * validating engine will render the errors as if they were inserted into a feedback panel rather
 * than just using the default behavior, which is to use an alert message to notify the user of the
 * errors.
 * 
 * @author Jeremy Thomerson
 */
public class ClientAndServerValidatingFeedbackBehavior extends Behavior
{

	private static final long serialVersionUID = 1L;

	private WebMarkupContainer mContainer;
	private final Form<?> mForm;

	public ClientAndServerValidatingFeedbackBehavior(Form<?> form)
	{
		mForm = form;
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		checkComponentIsWebMarkupContainer(component);
		mContainer = (WebMarkupContainer)component;
		mContainer.setOutputMarkupId(true);
	}

	protected final void checkComponentIsWebMarkupContainer(Component component)
	{
		if ((component instanceof WebMarkupContainer) == false)
		{
			throw new IllegalArgumentException("This behavior [" + getClass().getSimpleName() +
				"] can only be added to a WebMarkupContainer");
		}
	}

	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		super.renderHead(c, response);
		CoreLibrariesContributor.contributeAjax(c.getApplication(), response);

		// add a trigger that will add our validation to the forms' onSubmit methods
		String formID = mForm.getMarkupId();
		String containerID = mContainer.getMarkupId();
		response.render(OnLoadHeaderItem.forScript("ClientAndServerValidator.registerFeedbackContainerForForm('" +
			formID + "', '" + containerID + "');"));
	}

}
