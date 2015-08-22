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
package org.wicketstuff.security.models;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.wicketstuff.security.WaspApplication;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * A secure {@link CompoundPropertyModel}. Please note that this model does not enforce a security
 * on get or setObject as this is left to Wicket. Please provide an override on the inner class
 * {@link AttachedSecureCompoundPropertyModel} if you wish to do so
 * 
 * <pre>
 * <code>
 * public void setObject(Object obj)
 * {
 * 	if(isAuthorized(getOwner(),getActionFactory().getAction(Enable.class))
 * 		super.setObject(obj);
 * 	else
 * 		throw new UnauthorizedActionException(getOwner(),getActionFactory().getAction(Enable.class));
 * }
 * </code>
 * </pre>
 * 
 * and override {@link #wrapOnInheritance(Component)} to return your class. Also note that Wicket by
 * default only checks the setter and not the getter as that is generally equivalent to the render
 * check on the component. Note when setting this model on a page the model is shared with every
 * component on this page including the page itself, failing to grant enough rights to the page will
 * result in an {@link AccessDeniedPage}. Failing the 2nd will result in Wicket complaining about
 * missing components. Therefore it is best to set this model on a {@link Panel} or {@link Form}.
 * 
 * Notes for usage: When you have a model that enables the use of a form, note that you need to
 * enable the parent model if you want your form components enabled (like the textfield). The code
 * below shows (copied out the SecureCompoundPropertyModelTest) what you need to set in order to get
 * a form to work.
 * 
 * <pre>
 * <code>
 *       authorized.put("model:" + SecureModelPage.class.getName(), application.getActionFactory().getAction("render enable"));
 *       authorized.put("model:label", application.getActionFactory().getAction("render"));
 *       authorized.put("model:input", application.getActionFactory().getAction("render enable"));
 * </code>
 * </pre>
 * 
 * @author marrink
 */
public class SecureCompoundPropertyModel<T> extends CompoundPropertyModel<T> implements
	ISecureModel<T>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param object
	 */
	public SecureCompoundPropertyModel(T object)
	{
		super(object);
	}

	/**
	 * Shortcut to the {@link IAuthorizationStrategy}.
	 * 
	 * @return the strategy
	 */
	protected final WaspAuthorizationStrategy getStrategy()
	{
		return WaspAuthorizationStrategy.get();
	}

	/**
	 * Shortcut to the {@link ActionFactory}.
	 * 
	 * @return the factory
	 */
	protected final ActionFactory getActionFactory()
	{
		return ((WaspApplication)Application.get()).getActionFactory();
	}

	/**
	 * @see org.apache.wicket.model.CompoundPropertyModel#wrapOnInheritance(org.apache.wicket.Component)
	 */
	@Override
	public <C> IWrapModel<C> wrapOnInheritance(Component component)
	{
		return new AttachedSecureCompoundPropertyModel<C>(component);
	}

	/**
	 * @see org.wicketstuff.security.models.ISecureModel#isAuthenticated(org.apache.wicket.Component)
	 */
	public boolean isAuthenticated(Component component)
	{
		return getStrategy().isModelAuthenticated(this, component);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.models.ISecureModel#isAuthorized(org.apache.wicket.Component,
	 *      org.wicketstuff.security.actions.WaspAction)
	 */
	public boolean isAuthorized(Component component, WaspAction action)
	{
		return getStrategy().isModelAuthorized(this, component, action);
	}

	/**
	 * 
	 * @see org.apache.wicket.model.CompoundPropertyModel#toString()
	 */
	@Override
	public String toString()
	{
		return getClass().getName();
	}

	/**
	 * Component aware variation of the {@link SecureCompoundPropertyModel} that components that
	 * inherit the model get. Copy of AttachedCompoundPropertyModel.
	 * 
	 * @author ivaynberg
	 * @author marrink
	 */
	protected class AttachedSecureCompoundPropertyModel<Y> extends AbstractPropertyModel<Y>
		implements IWrapModel<Y>, ISecureModel<Y>
	{
		private static final long serialVersionUID = 1L;

		private final Component owner;

		/**
		 * Constructor
		 * 
		 * @param owner
		 *            component that this model has been attached to
		 */
		public AttachedSecureCompoundPropertyModel(Component owner)
		{
			super(SecureCompoundPropertyModel.this);
			this.owner = owner;
		}

		/**
		 * The component bound to this model.
		 * 
		 * @return the owing component of this model
		 */
		protected final Component getOwner()
		{
			return owner;
		}

		/**
		 * @see org.apache.wicket.model.AbstractPropertyModel#propertyExpression()
		 */
		@Override
		protected String propertyExpression()
		{
			return SecureCompoundPropertyModel.this.propertyExpression(owner);
		}

		/**
		 * @see org.apache.wicket.model.IWrapModel#getWrappedModel()
		 */
		public IModel<T> getWrappedModel()
		{
			return SecureCompoundPropertyModel.this;
		}

		/**
		 * @see org.apache.wicket.model.AbstractPropertyModel#detach()
		 */
		@Override
		public void detach()
		{
			super.detach();
			SecureCompoundPropertyModel.this.detach();
		}

		/**
		 * 
		 * @see org.wicketstuff.security.models.ISecureModel#isAuthenticated(org.apache.wicket.Component)
		 */
		public boolean isAuthenticated(Component component)
		{
			return SecureCompoundPropertyModel.this.isAuthenticated(component != null ? component
				: owner);
		}

		/**
		 * 
		 * @see org.wicketstuff.security.models.ISecureModel#isAuthorized(org.apache.wicket.Component,
		 *      org.wicketstuff.security.actions.WaspAction)
		 */
		public boolean isAuthorized(Component component, WaspAction action)
		{
			return SecureCompoundPropertyModel.this.isAuthorized(component != null ? component
				: owner, action);
		}

		/**
		 * 
		 * @see org.apache.wicket.model.AbstractPropertyModel#toString()
		 */
		@Override
		public String toString()
		{
			return SecureCompoundPropertyModel.this.toString() + ":" +
				(getOwner() != null ? getOwner().getId() : "null");
		}
	}

}
