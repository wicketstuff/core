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
package org.wicketstuff.security.hive.authentication;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Default implementation of a Subject.Targeted for wicket applications. By default it authenticates
 * all classes, components and models. This makes it an ideal candidate for single login
 * applications.
 * 
 * @author marrink
 * @see WicketSubject
 */
public class DefaultSubject extends BaseSubject implements WicketSubject
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.wicketstuff.security.hive.authentication.WicketSubject#isClassAuthenticated(java.lang.Class)
	 */
	public boolean isClassAuthenticated(Class<?> class1)
	{
		return true;
	}

	/**
	 * @see org.wicketstuff.security.hive.authentication.WicketSubject#isComponentAuthenticated(org.apache.wicket.Component)
	 */
	public boolean isComponentAuthenticated(Component component)
	{
		return true;
	}

	/**
	 * @see org.wicketstuff.security.hive.authentication.WicketSubject#isModelAuthenticated(org.apache.wicket.model.IModel,
	 *      org.apache.wicket.Component)
	 */
	public boolean isModelAuthenticated(IModel<?> model, Component component)
	{
		return true;
	}
}
