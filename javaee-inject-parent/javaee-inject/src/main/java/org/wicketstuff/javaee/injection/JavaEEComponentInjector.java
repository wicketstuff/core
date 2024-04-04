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
package org.wicketstuff.javaee.injection;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.javaee.naming.IJndiNamingStrategy;
import org.wicketstuff.javaee.naming.StandardJndiNamingStrategy;

/**
 * This injection must be initialized in the Wicket WebApplication in order to enable Java EE 5
 * resource injection in Wicket Pages Add the initialization in WebApplication's init() method, e.g.
 * <p/>
 * protected void init() { addComponentInstantiationListener(new JavaEEComponentInjector(this)); }
 * 
 * @author Filippo Diotalevi
 */
public class JavaEEComponentInjector extends Injector implements IComponentInstantiationListener
{

	IFieldValueFactory factory = null;

	/**
	 * Constructor
	 * 
	 * @param webapp
	 *            wicket web application
	 */
	public JavaEEComponentInjector(WebApplication webapp)
	{
		this(webapp, new StandardJndiNamingStrategy());
	}

	/**
	 * Constructor
	 * 
	 * @param webapp
	 *            - wicket web application
	 * @param namingStrategy
	 *            - a jndi naming strategy to lookup ejb references
	 */
	public JavaEEComponentInjector(WebApplication webapp, IJndiNamingStrategy namingStrategy)
	{
		bind(webapp);
		factory = new JavaEEProxyFieldValueFactory(namingStrategy);
	}

	@Override
	public void inject(Object object)
	{
		inject(object, factory);
	}

	@Override
	public void onInstantiation(Component component)
	{
		inject(component);
	}
}
