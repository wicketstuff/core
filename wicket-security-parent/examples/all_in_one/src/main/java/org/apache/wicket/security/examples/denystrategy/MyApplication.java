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
package org.apache.wicket.security.examples.denystrategy;

import java.net.MalformedURLException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.examples.denystrategy.factories.DenialFactory;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;

/**
 * This example re-uses almost everything from the tab example to show you how simple it
 * is to just replace the strategy. The effect we are trying to create is that the regular
 * user now has all the power of the super user and vice versa. Offcourse there are
 * simpler ways for doing that but then we would not have an example about using a custom
 * strategy now did we?
 * 
 * @author marrink
 */
public class MyApplication extends org.apache.wicket.security.examples.tabs.MyApplication
{

	/**
	 * Construct.
	 */
	public MyApplication()
	{
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#setupStrategyFactory()
	 */
	@Override
	protected void setupStrategyFactory()
	{
		setStrategyFactory(new DenialFactory(getHiveKey()));
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.MyApplication#getHiveKey()
	 */
	@Override
	protected Object getHiveKey()
	{
		// use a different hive because we need to make some changes to the
		// policy
		return "denial";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.MyApplication#setUpHive()
	 */
	@Override
	protected void setUpHive()
	{
		// create factory
		PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
		try
		{
			// this example uses 1 policy file but you can add as many as you
			// like
			factory.addPolicyFile(getServletContext().getResource("/WEB-INF/denial.hive"));
		}
		catch (MalformedURLException e)
		{
			throw new WicketRuntimeException(e);
		}
		// register factory
		HiveMind.registerHive(getHiveKey(), factory);
	}
}
