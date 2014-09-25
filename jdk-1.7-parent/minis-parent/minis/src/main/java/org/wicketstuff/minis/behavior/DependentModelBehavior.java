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
package org.wicketstuff.minis.behavior;

import java.util.Objects;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

/**
 * Behavior that uses the value of the dependent model. The dependent model is detached when the
 * behavior is detached.
 * 
 * @param <D>
 *            the type of the model this behavior depends on
 */
public abstract class DependentModelBehavior<D> extends Behavior
{

	private final IModel<D> dependentModel;

	/**
	 * Constructor.
	 *
	 * @param dependentModel
	 *            model the behavior is dependent on
	 */
	public DependentModelBehavior(final IModel<D> dependentModel)
	{
		Objects.requireNonNull(dependentModel, "The dependent model must not be null.");
		this.dependentModel = dependentModel;
	}

	@Override
	public void detach(final Component component)
	{
		super.detach(component);
		dependentModel.detach();
	}

	/**
	 * Returns the model the behavior depends on.
	 * 
	 * @return the model the behavior depends on
	 */
	protected IModel<D> getDependentModel()
	{
		return dependentModel;
	}

}
