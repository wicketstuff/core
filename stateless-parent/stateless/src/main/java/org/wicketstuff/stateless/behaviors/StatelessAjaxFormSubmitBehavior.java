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
package org.wicketstuff.stateless.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Stateless version of AjaxFormSubmitBehavior.
 */
public class StatelessAjaxFormSubmitBehavior extends StatelessAjaxEventBehavior
{

	/** The default form processing flag. */
	private boolean defaultFormProcessing = true;

	/**
	 * Instantiates a new stateless ajax form submit behavior.
	 *
	 * @param event the event
	 */
	public StatelessAjaxFormSubmitBehavior(String event)
	{
		super(event);
	}

	@Override
	protected PageParameters getPageParameters()
	{
		return null;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		Form<?> form = findForm();
		attributes.setFormId(form.getMarkupId());

		String formMethod = form.getMarkupAttributes().getString("method");
		if (formMethod == null || "POST".equalsIgnoreCase(formMethod))
		{
			attributes.setMethod(Method.POST);
		}

		if (form.getRootForm().isMultiPart())
		{
			attributes.setMultipart(true);
			attributes.setMethod(Method.POST);
		}
		
		attributes.setPreventDefault(true);
	}
	
	/**
	 * Finds form that will be submitted.
	 *
	 * @return form to submit or {@code null} if none found
	 */
	protected Form<?> findForm()
	{
		// try to find form in the hierarchy of owning component
		Component component = getComponent();
		if (component instanceof Form<?>)
		{
			return (Form<?>)component;
		}
		else
		{
			return component.findParent(Form.class);
		}
	}
	
	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		findForm().getRootForm().onFormSubmitted(new AjaxFormSubmitter(this, target));
	}
	
	/**
	 * The Class AjaxFormSubmitter.
	 */
	class AjaxFormSubmitter implements IFormSubmitter
	{

		/** The submit behavior. */
		private final StatelessAjaxFormSubmitBehavior submitBehavior;
		
		/** The target. */
		private final AjaxRequestTarget target;
		
		/**
		 * Instantiates a new ajax form submitter.
		 *
		 * @param submitBehavior the submit behavior
		 * @param target the target
		 */
		public AjaxFormSubmitter(StatelessAjaxFormSubmitBehavior submitBehavior,
			AjaxRequestTarget target)
		{
			this.submitBehavior = submitBehavior;
			this.target = target;
		}

		@Override
		public Form<?> getForm()
		{
			return StatelessAjaxFormSubmitBehavior.this.findForm();
		}

		@Override
		public boolean getDefaultFormProcessing()
		{
			return StatelessAjaxFormSubmitBehavior.this.getDefaultFormProcessing();
		}

		@Override
		public void onSubmit()
		{
			StatelessAjaxFormSubmitBehavior.this.onSubmit(target);
		}

		@Override
		public void onAfterSubmit()
		{
			StatelessAjaxFormSubmitBehavior.this.onAfterSubmit(target);			
		}

		@Override
		public void onError()
		{
			StatelessAjaxFormSubmitBehavior.this.onError(target);
		}
		
	}
	
	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>after</em> the form's onSubmit method.
	 *
	 * @param target the target
	 */
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
	}

	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>before</em> the form's onSubmit method.
	 *
	 * @param target the target
	 */
	protected void onSubmit(AjaxRequestTarget target)
	{
	}

	/**
	 * Listener method invoked when the form has been processed and errors occurred.
	 *
	 * @param target the target
	 */
	protected void onError(AjaxRequestTarget target)
	{
	}
	
	/**
	 * Gets the default form processing.
	 *
	 * @return the default form processing
	 */
	public boolean getDefaultFormProcessing()
	{
		return defaultFormProcessing ;
	}

	/**
	 * Sets the default form processing.
	 *
	 * @param defaultFormProcessing the new default form processing
	 */
	public void setDefaultFormProcessing(boolean defaultFormProcessing)
	{
		this.defaultFormProcessing = defaultFormProcessing;
	}
}
