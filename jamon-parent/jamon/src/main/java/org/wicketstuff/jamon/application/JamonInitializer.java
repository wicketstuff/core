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
package org.wicketstuff.jamon.application;

import java.util.ServiceLoader;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.wicketstuff.jamon.component.MonitoringRepositoryKey;
import org.wicketstuff.jamon.monitor.JamonRepository;
import org.wicketstuff.jamon.monitor.MonitoringRepository;
import org.wicketstuff.jamon.request.cycle.JamonAwareRequestCycleListener;

/**
 * Add the jamon features to your application.
 * 
 * The feature and it's components require a data provider for monitoring data. This can be done by
 * registering a {@link MonitoringRepository} to the application meta data.
 * 
 * Optionally if you want to use the component monitoring provided, another
 * {@link IRequestCycleListener} is required.
 * 
 * To include this {@link IInitializer} in your application you need to register it by
 * {@link ServiceLoader}. So it is required to add a file called 'org.apache.wicket.IInitializer' to
 * folder 'META-INF/services'. File content needs to be the fully qualified classname of this
 * {@link IInitializer}
 * 
 * @author rene-d-menoto
 */
public class JamonInitializer implements IInitializer
{

	@Override
	public void destroy(Application application)
	{
		return;
	}

	@Override
	public void init(Application application)
	{
		application.setMetaData(MonitoringRepositoryKey.KEY, new JamonRepository());
		application.getRequestCycleListeners()
			.add(new JamonAwareRequestCycleListener(application, true));
	}

}