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

import org.apache.wicket.model.IModel;

/**
 * Boolean model that returns the opposite of the dependent Boolean model value. The value returned
 * when the dependent model value is null is configurable -- it defaults to true.
 */
public class NotModel extends LoadableDetachableDependentModel<Boolean, Boolean>
{

	private boolean nullValue;

	/**
	 * Constructor.
	 * 
	 * @param dependentModel
	 *            non-null model this model depends on
	 */
	public NotModel(IModel<Boolean> dependentModel)
	{
		this(dependentModel, true);
	}

	/**
	 * Constructor.
	 * 
	 * @param dependentModel
	 *            non-null model this model depends on
	 * @param nullValue
	 *            the value load() will return if the dependent model value is null
	 */
	public NotModel(IModel<Boolean> dependentModel, boolean nullValue)
	{
		super(dependentModel);
		this.nullValue = nullValue;
	}

	@Override
	protected Boolean load()
	{
		Boolean b = getDependentModel().getObject();
		return b == null ? nullValue : !b;
	}

}
