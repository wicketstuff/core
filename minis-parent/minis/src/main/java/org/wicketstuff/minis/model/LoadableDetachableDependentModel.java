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
package org.wicketstuff.minis.model;

import java.util.Objects;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * {@link LoadableDetachableModel} that is dependent on another model. The dependent model is
 * detached when this model is detached.
 * 
 * @param <T>
 *            type of this model
 * @param <D>
 *            type of model this model is dependent on
 */
public abstract class LoadableDetachableDependentModel<T, D> extends LoadableDetachableModel<T>
{

	private IModel<D> dependentModel;

	/**
	 * Constructor.
	 * 
	 * @param dependentModel
	 *            non-null model this model depends on
	 */
	public LoadableDetachableDependentModel(IModel<D> dependentModel)
	{
		Objects.requireNonNull(dependentModel, "The dependent model cannot be null.");
		this.dependentModel = dependentModel;
	}

	/**
	 * Returns the model this model depends on.
	 * 
	 * @return the model this model depends on
	 */
	public IModel<D> getDependentModel()
	{
		return dependentModel;
	}

	@Override
	protected void onDetach()
	{
		super.onDetach();
		dependentModel.detach();
	}

}
