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
package org.wicketstuff.security.swarm.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IWrapModel;
import org.wicketstuff.security.models.SecureCompoundPropertyModel;

/**
 * Swarm version of {@link SecureCompoundPropertyModel}. Because of the wrappedmodel it was not as
 * easy as slapping an implements SwarmModel on this class, now it is as easy as providing an
 * implementation for {@link SwarmModel#getSecurityId(Component)}
 * 
 * @author marrink
 */
public abstract class SwarmCompoundPropertyModel<T> extends SecureCompoundPropertyModel<T>
	implements SwarmModel<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param object
	 */
	public SwarmCompoundPropertyModel(T object)
	{
		super(object);
	}

	/**
	 * @see org.wicketstuff.security.models.SecureCompoundPropertyModel#wrapOnInheritance(org.apache.wicket.Component)
	 */
	@Override
	public <C> IWrapModel<C> wrapOnInheritance(Component component)
	{
		return new AttachedSwarmCompoundPropertyModel<C>(component);
	}

	/**
	 * A wrapping model delegating all security calls to the {@link SwarmCompoundPropertyModel}
	 * instance.
	 * 
	 * @author marrink
	 */
	protected class AttachedSwarmCompoundPropertyModel<Y> extends
		AttachedSecureCompoundPropertyModel<Y> implements SwarmModel<Y>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * Construct.
		 * 
		 * @param owner
		 */
		public AttachedSwarmCompoundPropertyModel(Component owner)
		{
			super(owner);
		}

		/**
		 * 
		 * @see org.wicketstuff.security.swarm.models.SwarmModel#getSecurityId(org.apache.wicket.Component)
		 */
		public String getSecurityId(Component component)
		{
			return SwarmCompoundPropertyModel.this.getSecurityId(component != null ? component
				: getOwner());
		}

	}

}
