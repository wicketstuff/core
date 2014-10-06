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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Behavior that sets whether a component is visible based on the value of the dependent Boolean
 * model.
 */
public class VisibleModelBehavior extends DependentModelBehavior<Boolean>
{

	/**
	 * Constructor.
	 *
	 * @param dependentModel
	 *            model of whether the component should be visible
	 */
	public VisibleModelBehavior(final IModel<Boolean> dependentModel)
	{
		super(dependentModel);
	}

	@Override
	public void onConfigure(final Component component)
	{
		super.onConfigure(component);
		Boolean visible = getDependentModel().getObject();
		component.setVisible(visible != null && visible);
	}

}
