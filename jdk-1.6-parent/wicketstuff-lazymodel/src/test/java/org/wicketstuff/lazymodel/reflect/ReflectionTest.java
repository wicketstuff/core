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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

import org.junit.Test;

/**
 * Test for {@link Reflection}.
 * 
 * @author svenmeier
 */
public class ReflectionTest {

	@Test
	public void variableType() throws Exception {
		Method method = Foo.class.getMethod("put", Object.class, Object.class);

		assertEquals(String.class, Reflection.variableType(
				(ParameterizedType) Foo.class.getGenericSuperclass(),
				(TypeVariable<?>) method.getGenericParameterTypes()[0]));

		assertEquals(Integer.class, Reflection.variableType(
				(ParameterizedType) Foo.class.getGenericSuperclass(),
				(TypeVariable<?>) method.getGenericParameterTypes()[1]));
	}

	@Test
	public void classForType() throws Exception {

		assertEquals(HashMap.class, Reflection.getClass(new Foo().getClass()
				.getGenericSuperclass()));

		Method method = Foo.class.getMethod("put", Object.class, Object.class);

		try {
			Reflection.getClass((TypeVariable<?>) method
					.getGenericParameterTypes()[0]);

			fail();
		} catch (IllegalArgumentException expected) {
		}
	}

	@Test
	public void getters() throws Exception {
		assertTrue(Reflection.isGetter(Foo.class.getMethod("getProperty")));
		assertFalse(Reflection.isGetter(Foo.class.getMethod("getNonProperty", String.class)));
		assertFalse(Reflection.isGetter(Foo.class.getMethod("getnonProperty")));
		assertFalse(Reflection.isGetter(Foo.class.getMethod("isNonProperty")));
		assertFalse(Reflection.isGetter(Foo.class.getMethod("nonProperty")));
		assertFalse(Reflection.isGetter(Foo.class.getMethod("getNonProperty")));
		assertTrue(Reflection.isGetter(Foo.class.getMethod("isProperty")));
	}

	public static class Foo extends HashMap<String, Integer> {
		private static final long serialVersionUID = 1L;

		public String getProperty() {
			return null;
		}

		public String getNonProperty(String arg) {
			return null;
		}

		public String getnonProperty() {
			return null;
		}

		public String isNonProperty() {
			return null;
		}

		public String nonProperty() {
			return null;
		}

		public void getNonProperty() {
		}

		public boolean isProperty() {
			return true;
		}
	}
}
