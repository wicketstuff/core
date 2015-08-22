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
package org.wicketstuff.minis.behavior.veil;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

/**
 * Behavior that shows a veil over a component if it is not enabled
 * 
 * @author Igor Vaynberg (ivaynberg)
 */
public class DisabledVeil extends Veil
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public DisabledVeil()
	{
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param cssClassName
	 *            name of css class that will be used for the veil
	 */
	public DisabledVeil(final String cssClassName)
	{
		super(cssClassName);
	}

	/**
	 * @see Behavior#isEnabled(org.apache.wicket.Component)
	 */
	@Override
	public boolean isEnabled(final Component component)
	{
		return super.isEnabled(component) && component.isEnabled() && component.isEnableAllowed();
	}


}
