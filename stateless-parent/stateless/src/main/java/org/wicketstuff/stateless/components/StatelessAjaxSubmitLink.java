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
package org.wicketstuff.stateless.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.wicketstuff.stateless.behaviors.StatelessAjaxFormSubmitBehavior;

/**
 * Stateless version of AjaxSubmitLink.
 * 
 * @author Andrea Del Bene
 *
 */
public class StatelessAjaxSubmitLink extends AbstractLink
{

	public StatelessAjaxSubmitLink(String id)
	{
		super(id);
		setOutputMarkupId(true);
		
		add(new StatelessAjaxSubmittingLinkBehavior("click"));
	}
	
	@Override
	protected boolean getStatelessHint()
	{
		return true;
	}
	
	/**
	 * AJAX submit behavior for this component. Its main purpose is to invoke component's
	 * submit processing handlers. 
	 */
	class StatelessAjaxSubmittingLinkBehavior extends StatelessAjaxFormSubmitBehavior
	{

		public StatelessAjaxSubmittingLinkBehavior(String event)
		{
			super(event);
		}

		@Override
		protected void onAfterSubmit(AjaxRequestTarget target)
		{
			StatelessAjaxSubmitLink.this.onAfterSubmit(target);
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			StatelessAjaxSubmitLink.this.onSubmit(target);
		}

		@Override
		protected void onError(AjaxRequestTarget target)
		{
			StatelessAjaxSubmitLink.this.onError(target);
		}		
	}
	
	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>after</em> the form's onSubmit method.
	 */
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
		
	}
	
	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>before</em> the form's onSubmit method.
	 */
	protected void onSubmit(AjaxRequestTarget target)
	{
		
	}
	
	/**
	 * Listener method invoked when the form has been processed and errors occurred
	 * 
	 * @param target
	 */
	protected void onError(AjaxRequestTarget target)
	{
		
	}
}
