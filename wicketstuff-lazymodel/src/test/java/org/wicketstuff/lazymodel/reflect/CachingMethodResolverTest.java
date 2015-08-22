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
package org.wicketstuff.lazymodel.reflect;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test for {@link CachingMethodResolver}.
 * 
 * @author svenmeier
 */
public class CachingMethodResolverTest {

	@Test
	public void test() throws Exception {
		final Method method = getClass().getMethod("test");
		final Serializable id = "";
		final int[] count = new int[1];

		IMethodResolver resolver = new CachingMethodResolver(
				new IMethodResolver() {
					@Override
					public Method getSetter(Method getter) {
						count[0]++;

						return getter;
					}

					@Override
					public Method getMethod(Class<?> owner, Serializable id) {
						count[0]++;

						return method;
					}

					@Override
					public Serializable getId(Method method) {
						count[0]++;

						return id;
					}
				});

		resolver.getId(method);
		resolver.getId(method);
		assertEquals(1, count[0]);

		resolver.getMethod(getClass(), id);
		resolver.getMethod(getClass(), id);
		assertEquals(2, count[0]);

		resolver.getSetter(method);
		resolver.getSetter(method);
		assertEquals(3, count[0]);
	}
}
