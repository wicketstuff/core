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
package org.wicketstuff.console.examples;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.wicketstuff.console.examples.clojure.ClojureEngineTestPage;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see org.wicketstuff.console.examples.Start#main(String[])
 */
public class WicketApplication extends WebApplication implements ApplicationContextAware
{
	private SessionFactory sessionFactory;
	private ApplicationContext applicationContext;

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<ClojureEngineTestPage> getHomePage()
	{
		return ClojureEngineTestPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		getComponentInstantiationListeners().add(
			new SpringComponentInjector(this, applicationContext));
	}

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public void setSessionFactory(final SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@Override
	public RuntimeConfigurationType getConfigurationType()
	{
		return RuntimeConfigurationType.DEPLOYMENT;
	}

	public void setApplicationContext(final ApplicationContext applicationContext)
		throws BeansException
	{
		this.applicationContext = applicationContext;
	}

}
