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

import java.net.MalformedURLException;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.WaspActionFactory;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.config.PolicyFileHiveFactory;
import org.wicketstuff.security.hive.config.SwarmPolicyFileHiveFactory;
import org.wicketstuff.security.pages.MockHomePage;
import org.wicketstuff.security.pages.MockLoginPage;
import org.wicketstuff.security.strategies.StrategyFactory;
import org.wicketstuff.security.swarm.SwarmWebApplication;
import org.wicketstuff.security.swarm.actions.SwarmActionFactory;
import org.wicketstuff.security.swarm.strategies.SwarmStrategyFactory;

/**
 * Test if everything still works if we don't inherit from {@link SwarmWebApplication} but simply
 * implement all the required interfaces. This simply runs all the tests from {@link GeneralTest}
 * and if we don't get a {@link ClassCastException} or something like that everything works OK.
 * 
 * @author marrink
 */
public class ExtendsTest extends GeneralTest
{
	private static final class TestApplication extends WebApplication implements WaspApplication
	{

		private WaspActionFactory actionFactory;

		private StrategyFactory strategyFactory;

		/**
		 * 
		 */
		public TestApplication()
		{
			super();
		}

		/**
		 * 
		 * @return a key to retrieve the hive for this application.
		 */
		protected Object getHiveKey()
		{
			// if we were using servlet-api 2.5 we could get the contextpath
			// from the
			// servletcontext
			return "test";
		}

		protected void setUpHive()
		{
			PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
			try
			{
				factory.addPolicyFile(getServletContext().getResource("WEB-INF/policy.hive"));
				factory.setAlias("SimplePrincipal",
					"org.wicketstuff.security.hive.authorization.SimplePrincipal");
				factory.setAlias("myPackage", "org.wicketstuff.security.pages");
			}
			catch (MalformedURLException e)
			{
				log.error(e.getMessage(), e);
			}
			HiveMind.registerHive(getHiveKey(), factory);
		}

		@Override
		public Class<? extends Page> getHomePage()
		{
			return MockHomePage.class;
		}

		public Class<? extends Page> getLoginPage()
		{
			return MockLoginPage.class;
		}

		@Override
		public Session newSession(Request request, Response response)
		{
			return new WaspSession(this, request);
		}

		public WaspActionFactory getActionFactory()
		{
			return actionFactory;
		}

		public StrategyFactory getStrategyFactory()
		{
			return strategyFactory;
		}

		protected void setupActionFactory()
		{
			if (actionFactory == null)
				actionFactory = new SwarmActionFactory(getHiveKey());
			else
				throw new IllegalStateException("Can not initialize ActionFactory more then once");

		}

		protected void setupStrategyFactory()
		{
			if (strategyFactory == null)
				strategyFactory = new SwarmStrategyFactory(getHiveKey());
			else
				throw new IllegalStateException("Can not initialize StrategyFactory more then once");
		}

		/**
		 * triggers the setup of the factories and the hive. Please remember to call super.init()
		 * when you override this method.
		 * 
		 * @see org.wicketstuff.security.WaspWebApplication#init()
		 */
		@Override
		protected void init()
		{
			setupActionFactory();
			setUpHive();
			setupStrategyFactory();
		}

		/**
		 * Destroys the strategy factory and the action factory. In that order. If you override this
		 * method you must call super.destroy().
		 * 
		 * @see Application#onDestroy()
		 */
		@Override
		protected void onDestroy()
		{
			StrategyFactory factory = getStrategyFactory();
			if (factory != null)
				factory.destroy();
			ActionFactory factory2 = getActionFactory();
			if (factory2 != null)
				factory2.destroy();
		}
	}

	private static final Logger log = LoggerFactory.getLogger(ExtendsTest.class);

	@Override
	public void setUp()
	{
		mock = new WicketTester(application = new TestApplication(), "src/test/java/" +
			getClass().getPackage().getName().replace('.', '/'));
		mock.setExposeExceptions(false);
	}

}
