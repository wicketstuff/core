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
package org.wicketstuff.lazymodel;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.wicketstuff.lazymodel.reflect.CachingMethodResolver;
import org.wicketstuff.lazymodel.reflect.CachingProxyFactory;
import org.wicketstuff.lazymodel.reflect.Evaluation;
import org.wicketstuff.lazymodel.reflect.IMethodResolver;
import org.wicketstuff.lazymodel.reflect.IProxyFactory;

/**
 * Initializer.
 * 
 * @author svenmeier
 */
public class Initializer implements IInitializer {

	@Override
	public void init(Application application) {
	}

	@Override
	public void destroy(Application application) {
		IMethodResolver resolver = LazyModel.methodResolver;
		if (resolver instanceof CachingMethodResolver) {
			((CachingMethodResolver) resolver).destroy(application);
		}

		IProxyFactory factory = Evaluation.proxyFactory;
		if (factory instanceof CachingProxyFactory) {
			((CachingProxyFactory) resolver).destroy(application);
		}
	}
}
