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
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test for {@link DefaultMethodResolver}.
 * 
 * @author svenmeier
 */
public class DefaultMethodResolverTest {

	private DefaultMethodResolver resolver = new DefaultMethodResolver();

	@Test
	public void methodWithoutArgs() throws Exception {

		Method method = Foo.class.getMethod("method");

		assertEquals("method()", resolver.getId(method));
	}

	@Test
	public void methodWithPrimitiveArgs() throws Exception {

		Method method = Foo.class.getMethod("methodWithPrimitiveArgs",
				int.class, float.class, double.class, long.class, byte.class,
				short.class);

		assertEquals("methodWithPrimitiveArgs(i,f,d,l,b,s)",
				resolver.getId(method));
	}

	@Test
	public void methodWithPrimitiveArrayArgs() throws Exception {

		Method method = Foo.class.getMethod("methodWithPrimitiveArrayArgs",
				int[].class, float[].class, double[].class, long[].class,
				byte[].class, short[].class);

		assertEquals("methodWithPrimitiveArrayArgs([i,[f,[d,[l,[b,[s)",
				resolver.getId(method));
	}

	@Test
	public void methodWithArgs() throws Exception {

		Method method = Foo.class.getMethod("methodWithArgs", Integer.class,
				Float.class, Double.class, Long.class, Byte.class, Short.class,
				Object.class, String.class, Foo.class);

		assertEquals("methodWithArgs(I,F,D,L,B,S,O,S,F)",
				resolver.getId(method));
	}

	@Test
	public void methodWithArrayArgs() throws Exception {

		Method method = Foo.class.getMethod("methodWithArrayArgs",
				Integer[].class, Float[].class, Double[].class, Long[].class,
				Byte[].class, Short[].class, Object[].class, String[].class,
				Foo[].class);

		assertEquals("methodWithArrayArgs([I,[F,[D,[L,[B,[S,[O,[S,[F)",
				resolver.getId(method));
	}

	@Test
	public void getter() throws Exception {

		Method method = Foo.class.getMethod("getBar");

		assertEquals("bar", resolver.getId(method));
	}

	@Test
	public void boolGetter() throws Exception {

		Method method = Foo.class.getMethod("isBaz");

		assertEquals("baz", resolver.getId(method));
	}

	@Test
	public void booleanGetter() throws Exception {

		Method method = Foo.class.getMethod("isBaz2");

		assertEquals("baz2", resolver.getId(method));
	}

	@Test
	public void noGetter() throws Exception {

		Method method = Foo.class.getMethod("getQuux");

		assertEquals("getQuux()", resolver.getId(method));
	}

	@Test
	public void noGetter2() throws Exception {

		Method method = Foo.class.getMethod("getQuux2", String.class);

		assertEquals("getQuux2(S)", resolver.getId(method));
	}

	@Test
	public void setterForGet() throws Exception {

		Method getter = Foo.class.getMethod("getBar");

		Method setter = resolver.getSetter(getter);

		assertEquals(Foo.class.getMethod("setBar", Object.class), setter);
	}

	@Test
	public void setterForOverridenGet() throws Exception {

		Method getter = ExtendedFoo.class.getMethod("getBar");

		Method setter = resolver.getSetter(getter);

		assertEquals(Foo.class.getMethod("setBar", Object.class), setter);
	}

	@Test
	public void setterForIs() throws Exception {

		Method getter = Foo.class.getMethod("isBaz");

		Method setter = resolver.getSetter(getter);

		assertEquals(Foo.class.getMethod("setBaz", Boolean.TYPE), setter);
	}

	@Test
	public void setterForGetterWithParameter() throws Exception {

		Method getter = Foo.class.getMethod("getQuux2", String.class);

		Method setter = resolver.getSetter(getter);

		assertEquals(
				Foo.class.getMethod("setQuux2", String.class, String.class),
				setter);
	}

	@Test
	public void getterWithoutSetter() throws Exception {

		Method getter = Foo.class.getMethod("getQuux");

		try {
			resolver.getSetter(getter);
			
			fail();
		} catch (Exception ex) {
			assertEquals("no setter for class org.wicketstuff.lazymodel.reflect.DefaultMethodResolverTest$Foo#setQuux", ex.getMessage());
		}
	}

	@Test
	public void syntheticMethodOnAnonymousClass() {

		// this nested construct results in the generation of a synthetic accessor for {@code accessed},
		// whose first parameter type (the anonymous outer subclass) has {@code #getSimpleName()} returning an empty String
		@SuppressWarnings("unused")
		Object outer = new Object()
		{
			private boolean accessed;
			
			private Object inner = new Object() {
				{
					accessed = true;
				}
			};
		};

		try {
			// check that resolver does not fail on the synthetic method
			resolver.getMethod(outer.getClass(), "notExistingMethod");
			
			// there is no method "notExistingMethod"
			fail();
		} catch (IllegalArgumentException expected) {
		}
	}
	
	public static class Foo {
		
		public Object method() {
			return null;
		}

		public Object methodWithPrimitiveArgs(int i, float f, double d, long l,
				byte b, short s) {
			return null;
		}

		public Object methodWithPrimitiveArrayArgs(int[] i, float[] f,
				double[] d, long[] l, byte[] b, short[] s) {
			return null;
		}

		public Object methodWithArgs(Integer i, Float f, Double d, Long l,
				Byte b, Short s, Object object, String string, Foo foo) {
			return null;
		}

		public Object methodWithArrayArgs(Integer[] i, Float[] f, Double[] d,
				Long[] l, Byte[] b, Short[] s, Object[] object,
				String[] string, Foo[] foo) {
			return null;
		}

		public Object getBar() {
			return null;
		}

		public void setBar(Object bar) {
		}

		public boolean isBaz() {
			return false;
		}

		public void setBaz(boolean baz) {
		}

		public Boolean isBaz2() {
			return null;
		}

		public void getQuux() {
		}

		public String getQuux2(String key) {
			return key;
		}

		public void setQuux2(String key, String value) {
		}
	}
	
	public static class ExtendedFoo extends Foo {
		@Override
		public Object getBar() {
			return super.getBar();
		}
	}
}
