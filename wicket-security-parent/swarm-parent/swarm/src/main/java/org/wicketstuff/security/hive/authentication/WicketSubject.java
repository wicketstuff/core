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
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * In addition to Subjects, WicketSubjects play an important part in multi-login scenario's as they
 * define what they authenticate. For example one subject might authenticate all subclasses of
 * BasicSecurePage where another might authenticate all subclasses of AdvancedSecurePage.
 * Effectively requiring a user to login twice if both type of pages are to be visited. If a Wicket
 * application encounters a regular Subject, that subject authenticates everything.
 * 
 * @author marrink
 */
public interface WicketSubject extends Subject
{
	/**
	 * Performs the authentication check on a class.
	 * 
	 * @param class1
	 * @return true if the class is authenticated, false otherwise.
	 * @see WaspAuthorizationStrategy#isClassAuthenticated(Class)
	 */
	public abstract boolean isClassAuthenticated(Class<?> class1);

	/**
	 * Performs the authentication check on a component.
	 * 
	 * @param component
	 * @return true if the component is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
	 */
	public abstract boolean isComponentAuthenticated(Component component);

	/**
	 * Performs the authentication check on a model.
	 * 
	 * @param model
	 * @param component
	 * @return true if the model is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isModelAuthenticated(IModel, Component)
	 */
	public abstract boolean isModelAuthenticated(IModel<?> model, Component component);
}
