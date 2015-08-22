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

import org.apache.wicket.authorization.Action;

/**
 * The actionFactory translates between the wicket actions, which are string based, and the
 * waspactions, which are based on something else (depending on the implementation). Swarm for
 * instance uses bitwise or to handle implies checks.
 * 
 * @author marrink
 */
public interface WaspActionFactory extends ActionFactory
{
	/**
	 * Translates a wicket action to a wasp action. If the input is already a WaspAction the same
	 * object is returned.
	 * 
	 * @param actions
	 *            a wicket action
	 * @return a WaspAction or null if there is no mapping possible or the input is null.
	 */
	public WaspAction getAction(Action actions);
}
