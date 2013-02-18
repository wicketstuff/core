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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

import org.junit.Test;

/**
 * Test for {@link Generics}.
 * 
 * @author svenmeier
 */
public class GenericsTest {

	@Test
	public void variableType() throws Exception {

		Method method = Foo.class.getMethod("put", Object.class, Object.class);

		assertEquals(String.class, Generics.variableType(
				(ParameterizedType) Foo.class.getGenericSuperclass(),
				(TypeVariable<?>) method.getGenericParameterTypes()[0]));

		assertEquals(Integer.class, Generics.variableType(
				(ParameterizedType) Foo.class.getGenericSuperclass(),
				(TypeVariable<?>) method.getGenericParameterTypes()[1]));
	}

	public static class Foo extends HashMap<String, Integer> {
	}
}
