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
package org.wicketstuff.security.actions;

/**
 * Immutable mapping for {@link org.apache.wicket.authorization.Action} with added logic for implies
 * checks. These actions are instantiated by an {@link ActionFactory}.
 * 
 * @author marrink
 * @see org.apache.wicket.authorization.Action
 */
public interface WaspAction
{
	/**
	 * @return The name of this action
	 * @see org.apache.wicket.authorization.Action#getName()
	 */
	public String getName();

	/**
	 * Check if the supplied action is implied by this WaspAction.
	 * 
	 * @param other
	 *            the action to check
	 * 
	 * @return true if the action is implied, false otherwise.
	 */
	public boolean implies(WaspAction other);

	/**
	 * Creates a new WaspAction containing both the specified actions and the actions of this
	 * WaspAction. This method must return a new WaspAction unless this action can be returned
	 * unmodified.
	 * 
	 * @param other
	 *            the actions to add
	 * @return a new WaspAction containing all the actions
	 */
	public WaspAction add(WaspAction other);

	/**
	 * Creates a new WaspAction with all the actions of this action except those specified.
	 * 
	 * @param action
	 *            the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this
	 *         action.
	 */
	public WaspAction remove(WaspAction action);

	/**
	 * The actionFactory that created this action.
	 * 
	 * @return factory
	 */
	public ActionFactory getActionFactory();

}