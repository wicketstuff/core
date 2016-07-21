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
package org.wicketstuff.security.log;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.validation.IErrorMessageSource;

/**
 * {@link IErrorMessageSource} which also knows which {@link Component} (if any) triggered this
 * message and keeps track of variables that can be used by the message.
 * 
 * @author marrink
 */
public interface IAuthorizationMessageSource extends IErrorMessageSource, Serializable
{

	/**
	 * Returns the source component of the authorization denied situation.
	 * 
	 * @return component or null if unknown at this time
	 */
	public abstract Component getComponent();

	/**
	 * Sets component where the authorization denied originates from. Could be <code>null</code>.
	 * 
	 * @param component
	 *            component
	 */
	public abstract void setComponent(Component component);

	/**
	 * Adds, or overwrites a previously associated variable with that name, to this resource.
	 * 
	 * @param name
	 *            variable name
	 * @param value
	 *            variable value
	 */
	public abstract void addVariable(String name, Object value);

}