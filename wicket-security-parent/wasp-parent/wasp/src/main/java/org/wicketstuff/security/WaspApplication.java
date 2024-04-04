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
package org.wicketstuff.security;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.WaspActionFactory;
import org.wicketstuff.security.strategies.StrategyFactory;

/**
 * Interface over Application to get the factories. All implementations should extend the
 * {@link Application} class or a subclass thereof. Implementations of {@link WebApplication} should
 * also cleanup the factories with a call to destroy when the time has come.
 * 
 * @see Application
 * @see ActionFactory
 * @see StrategyFactory
 * @author marrink
 */
public interface WaspApplication
{

	/**
	 * Returns the factory that will be used to create strategies for each session. There is only
	 * one factory for each application. Can not be null.
	 * 
	 * @return a factory
	 */
	public StrategyFactory getStrategyFactory();

	/**
	 * Returns factory for action mapping. There is only one factory for each application. Can not
	 * be null.
	 * 
	 * @return a factory.
	 */
	public WaspActionFactory getActionFactory();

	/**
	 * The Page to redirect to when the user is not authenticated. This is the primary login page.
	 * if an application requires a multi-level login, an ISecurityCheck could throw an
	 * {@link RestartResponseAtInterceptPageException} to redirect to the desired secondary login
	 * page.
	 * 
	 * @return a page.
	 */
	public Class<? extends Page> getLoginPage();

}