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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.wicketstuff.lazymodel.LazyModel.from;
import static org.wicketstuff.lazymodel.LazyModel.path;
import static org.wicketstuff.lazymodel.LazyModel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;
import org.wicketstuff.lazymodel.reflect.Generics;

/**
 * Test for {@link LazyModel}.
 * 
 * @author svenmeier
 */
public class LazyModelTest {

	@Test
	public void loadableDetachable() {
		final int[] got = new int[1];

		IModel<String> string = new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				got[0]++;

				return null;
			}
		};

		IModel<String> model = model(from(string)).loadableDetachable();
		model.getObject();
		model.getObject();
		assertEquals(1, got[0]);

		model.detach();

		model.getObject();
		model.getObject();
		assertEquals(2, got[0]);
	}

	@Test
	public void nullFails() {
		try {
			from((A) null);

			fail();
		} catch (Exception ex) {
		}
	}

	@Test
	public void getTarget() {
		A a = new A();

		LazyModel<A> model = model(from(a));

		assertEquals(A.class, model.getObjectType());
		assertEquals("", model.getPath());

		assertEquals(a, model.getObject());
	}

	@Test
	public void getTargetNotBoundFails() {
		LazyModel<String> model = model(from(String.class));
	
		try {
			model.getObject();
	
			fail();
		} catch (Exception ex) {
		}
	
	}

	@Test
	public void setTargetFails() {
		A a = new A();

		LazyModel<A> model = model(from(a));

		assertEquals(A.class, model.getObjectType());
		assertEquals("", model.getPath());

		try {
			model.setObject(new A());

			fail();
		} catch (UnsupportedOperationException expected) {
		}
	}

	@Test
	public void getInt() {
		A a = new A();

		a.integer = 42;

		LazyModel<Integer> model = model(from(a).getInteger());

		assertEquals(Integer.TYPE, model.getObjectType());
		assertEquals("integer", model.getPath());

		assertEquals(Integer.valueOf(42), model.getObject());
	}

	@Test
	public void setInt() {
		A a = new A();

		LazyModel<Integer> model = model(from(a).getInteger());

		assertEquals(Integer.TYPE, model.getObjectType());
		assertEquals("integer", model.getPath());

		model.setObject(Integer.valueOf(42));

		assertEquals(42, a.integer);
	}

	@Test
	public void getBoolean() {
		A a = new A();

		a.bool = true;

		LazyModel<Boolean> model = model(from(a).isBool());

		assertEquals(Boolean.TYPE, model.getObjectType());
		assertEquals("bool", model.getPath());

		assertEquals(Boolean.TRUE, model.getObject());
	}

	@Test
	public void setBoolean() {
		A a = new A();

		LazyModel<Boolean> model = model(from(a).isBool());

		assertEquals(Boolean.TYPE, model.getObjectType());
		assertEquals("bool", model.getPath());

		model.setObject(Boolean.TRUE);

		assertEquals(true, a.bool);
	}

	@Test
	public void setIntToNullFails() {
		A a = new A();

		LazyModel<Integer> model = model(from(a).getInteger());

		assertEquals(Integer.TYPE, model.getObjectType());
		assertEquals("integer", model.getPath());

		try {
			model.setObject(null);

			fail();
		} catch (WicketRuntimeException expected) {
		}
	}

	@Test
	public void getObject() {
		A a = new A();

		a.b = new B();

		LazyModel<B> model = model(from(a).getB());

		assertEquals(B.class, model.getObjectType());
		assertEquals("b", model.getPath());

		assertEquals(a.b, model.getObject());
	}

	@Test
	public void setObject() {
		A a = new A();

		LazyModel<B> model = model(from(a).getB());

		assertEquals(B.class, model.getObjectType());
		assertEquals("b", model.getPath());

		B b = new B();
		model.setObject(b);

		assertEquals(b, a.b);
	}

	@Test
	public void getInterface() {
		A a = new A();

		a.b = new B();

		LazyModel<I<C>> model = model(from(a).getI());

		assertEquals(I.class, Generics.getClass(model.getObjectType()));
		assertEquals("i", model.getPath());

		assertEquals(a.b, model.getObject());
	}

	@Test
	public void getGenericListFromInterface() {
		A a = new A();

		a.b = new B();

		LazyModel<List<C>> model = model(from(a).getI().getTs());

		assertEquals(List.class, Generics.getClass(model.getObjectType()));
		assertEquals("i.ts", model.getPath());

		assertEquals(a.b.cs, model.getObject());
	}

	@Test
	public void getRawList() {
		B b = new B();
		b.cs.add(new C());

		LazyModel<Object> model = model(from(b).getRs().get(0));

		assertEquals(Object.class, Generics.getClass(model.getObjectClass()));
		assertEquals("rs.get(i)", model.getPath());
		
		assertEquals(b.cs.get(0), model.getObject());
	}

	@Test
	public void getGenericMap() {
		A a = new A();

		a.b = new B();

		LazyModel<Map<String, String>> model = model(from(a).getB()
				.getStrings());

		assertEquals(Map.class, Generics.getClass(model.getObjectType()));
		assertEquals("b.strings", model.getPath());

		assertEquals(a.b.strings, model.getObject());
	}

	@Test
	public void setGenericMap() {
		A a = new A();

		a.b = new B();

		LazyModel<Map<String, String>> model = model(from(a).getB()
				.getStrings());

		assertEquals(Map.class, Generics.getClass(model.getObjectType()));
		assertEquals("b.strings", model.getPath());

		Map<String, String> strings = new HashMap<String, String>();
		model.setObject(strings);
		assertEquals(strings, a.b.strings);
	}

	@Test
	public void getIntFromNull() {
		A a = new A();

		LazyModel<Character> model = model(from(a).getB().getCharacter());

		assertEquals(Character.TYPE, model.getObjectType());
		assertEquals("b.character", model.getPath());

		assertEquals(null, model.getObject());
	}

	@Test
	public void setCharacterOnNullFails() {
		A a = new A();

		LazyModel<Character> model = model(from(a).getB().getCharacter());

		assertEquals(Character.TYPE, model.getObjectType());
		assertEquals("b.character", model.getPath());

		try {
			model.setObject('c');

			fail();
		} catch (NullPointerException expected) {
		}
	}

	@Test
	public void getValueFromGenericMap() {
		A a = new A();

		a.b = new B();

		a.b.strings.put("key", "value");

		LazyModel<String> model = model(from(a).getB().getStrings()
				.get("key"));

		assertEquals(String.class, model.getObjectType());
		assertEquals("b.strings.get(O)", model.getPath());

		assertEquals("value", model.getObject());
	}

	@Test
	public void setValueToGenericMap() {
		A a = new A();

		a.b = new B();

		LazyModel<String> model = model(from(a).getB().getStrings()
				.get("key"));

		assertEquals(String.class, model.getObjectType());
		assertEquals("b.strings.get(O)", model.getPath());

		model.setObject("value");

		assertEquals("value", a.b.strings.get("key"));
	}

	@Test
	public void getGenericListFromNull() {
		A a = new A();

		LazyModel<List<C>> model = model(from(a).getB().getCs());

		assertEquals(List.class, Generics.getClass(model.getObjectType()));
		assertEquals("b.cs", model.getPath());

		assertEquals(null, model.getObject());
	}

	@Test
	public void getEntryFromGenericList() {
		final List<C> cs = new ArrayList<C>();

		C c = new C();
		cs.add(c);

		IModel<List<C>> target = new AbstractReadOnlyModel<List<C>>() {
			@Override
			public List<C> getObject() {
				return cs;
			}
		};

		LazyModel<C> model = model(from(target).get(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("get(i)", model.getPath());

		assertEquals(c, model.getObject());
	}

	@Test
	public void setEntryToGenericList() {
		final List<C> cs = new ArrayList<C>();
		cs.add(new C());

		IModel<List<C>> target = new AbstractReadOnlyModel<List<C>>() {
			@Override
			public List<C> getObject() {
				return cs;
			}
		};

		LazyModel<C> model = model(from(target).get(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("get(i)", model.getPath());

		C c = new C();
		model.setObject(c);

		assertEquals(c, cs.get(0));
	}

	@Test
	public void getGenericList() {
		A a = new A();
		a.b = new B();

		LazyModel<List<C>> model = model(from(a).getB().getCs());

		assertEquals(List.class, Generics.getClass(model.getObjectType()));
		assertEquals("b.cs", model.getPath());

		assertEquals(a.b.cs, model.getObject());
	}

	@Test
	public void setGenericList() {
		A a = new A();
		a.b = new B();

		LazyModel<List<C>> model = model(from(a).getB().getCs());

		assertEquals(List.class, Generics.getClass(model.getObjectType()));
		assertEquals("b.cs", model.getPath());

		List<C> cs = new ArrayList<C>();
		model.setObject(cs);

		assertEquals(cs, a.b.cs);
	}

	@Test
	public void getObjectByIndex() {
		A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		c.string = "string";

		LazyModel<C> model = model(from(a).getB().getC(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("b.getC(i)", model.getPath());

		assertEquals(c, model.getObject());
	}

	@Test
	public void getObjectFromGenericList() {
		A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		c.string = "string";

		LazyModel<C> model = model(from(a).getB().getCs().get(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("b.cs.get(i)", model.getPath());

		assertEquals(c, model.getObject());
	}

	@Test
	public void getObjectFromEntryFromGenericList() {
		A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		c.string = "string";

		LazyModel<String> model = model(from(a).getB().getC(0).getString());

		assertEquals(String.class, model.getObjectType());
		assertEquals("b.getC(i).string", model.getPath());

		assertEquals("string", model.getObject());
	}

	@Test
	public void setObjectFromEntryFromGenericList() {
		A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		LazyModel<String> model = model(from(a).getB().getC(0).getString());

		assertEquals(String.class, model.getObjectType());
		assertEquals("b.getC(i).string", model.getPath());

		model.setObject("string");

		assertEquals("string", c.string);
	}

	@Test
	public void detach() {
		final boolean[] detached = { false };

		IModel<A> target = new IModel<A>() {
			@Override
			public A getObject() {
				return null;
			}

			@Override
			public void setObject(A a) {
			}

			@Override
			public void detach() {
				detached[0] = true;
			}
		};

		LazyModel<A> model = model(from(target));

		model.detach();

		assertTrue(detached[0]);
	}

	@Test
	public void setTargetInModel() {
		final A[] as = new A[] { new A() };

		IModel<A> target = new IModel<A>() {

			@Override
			public A getObject() {
				return as[0];
			}

			@Override
			public void setObject(A a) {
				as[0] = a;
			}

			@Override
			public void detach() {
			}
		};

		LazyModel<A> model = model(from(target));

		assertEquals(A.class, model.getObjectType());
		assertEquals("", model.getPath());

		A a = new A();

		model.setObject(a);

		assertEquals(a, as[0]);
	}

	@Test
	public void getObjectFromObjectClassAwareModel() {
		A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		PropertyModel<B> target = new PropertyModel<B>(a, "b");

		LazyModel<C> model = model(from(target).getC(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("getC(i)", model.getPath());

		assertEquals(a.b.cs.get(0), model.getObject());
	}

	/**
	 * A {@link IObjectClassAwareModel} doesn't provide generic information,
	 * thus the type of the model result is {@code List<Object>} only.
	 */
	@Test(expected=ClassCastException.class)
	public void getEntryFromGenericListInObjectClassAwareModelFails() {
		B b = new B();
		b.cs.add(new C());

		PropertyModel<List<C>> target = new PropertyModel<List<C>>(b, "cs");

		model(from(target).get(0).getString());
		
		fail();
	}

	@Test
	public void getWithNestedLazyModel() {
		A a = new A();
		a.integer = 0;

		B b = new B();

		C c = new C();
		b.cs.add(c);

		LazyModel<C> model = model(from(b).getC(from(a).getInteger()));

		assertEquals(C.class, model.getObjectType());
		assertEquals("getC(i)", model.getPath());

		assertEquals(c, model.getObject());
	}

	@Test
	public void setWithNestedLazyModel() {
		A a = new A();
		a.integer = 0;

		B b = new B();

		C c = new C();

		LazyModel<C> model = model(from(b).getC(from(a).getInteger()));

		assertEquals(C.class, model.getObjectType());
		assertEquals("getC(i)", model.getPath());

		model.setObject(c);

		assertEquals(c, b.cs.get(0));
	}

	@Test
	public void getObjectFromGenericModel() {
		final A a = new A();
		a.b = new B();

		C c = new C();
		a.b.cs.add(c);

		IModel<A> target = new AbstractReadOnlyModel<A>() {
			@Override
			public A getObject() {
				return a;
			}
		};

		LazyModel<C> model = model(from(target).getB().getC(0));

		assertEquals(C.class, model.getObjectType());
		assertEquals("b.getC(i)", model.getPath());

		assertEquals(a.b.cs.get(0), model.getObject());
	}

	@Test
	public void getFinal() {
		final A a = new A();
		a.f = new F();

		LazyModel<F> model = model(from(a).getF());

		assertEquals(F.class, model.getObjectType());
		assertEquals("f", model.getPath());

		assertEquals(a.f, model.getObject());
	}

	@Test
	public void getStrinFromFinalfails() {
		final A a = new A();
		a.f = new F();
		a.f.string = "string";

		try {
			from(a).getF().getString();

			fail();
		} catch (NullPointerException expected) {
		}
	}

	@Test
	public void noBindFails() {
		LazyModel<B> model = model(from(A.class).getB());

		try {
			model.getObject();

			fail();
		} catch (Exception ex) {
		}
	}

	@Test
	public void bindAndGet() {
		LazyModel<B> model = model(from(A.class).getB());

		final A a = new A();
		a.b = new B();

		assertEquals(a.b, model.bind(a).getObject());
	}

	@Test
	public void bindAndSet() {
		LazyModel<B> model = model(from(A.class).getB());

		final A a = new A();

		B b = new B();

		model.bind(a).setObject(b);

		assertEquals(b, a.b);
	}

	@Test
	public void getPath() {
		assertEquals("b.cs.size()", path(from(A.class).getB().getCs().size()));
	}
	
	@Test
	public void bindUnboundLazyModelToNonIObjectClassAwareModel() {
		LazyModel<B> model = model(from(A.class).getB());
		
		final A a = new A();
		
		B b = new B();
		
		model.bind(Model.of(b));
		
		assertEquals(B.class, ((IObjectClassAwareModel<B>) model.bind(Model.of(a))).getObjectClass());
	}
	
	public static class A implements Serializable {

		B b;

		F f;

		int integer;

		boolean bool;

		public F getF() {
			return f;
		}

		public B getB() {
			return b;
		}

		public void setB(B b) {
			this.b = b;
		}

		public I<C> getI() {
			return b;
		}

		public int getInteger() {
			return integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}

		public boolean isBool() {
			return bool;
		}
	}

	public static class B implements I<C>, Serializable {

		char character;

		Map<String, String> strings = new HashMap<String, String>();

		List<C> cs = new ArrayList<C>();

		B() {
		}

		public char getCharacter() {
			return character;
		}

		public void setCharacter(char character) {
			this.character = character;
		}

		public Map<String, String> getStrings() {
			return strings;
		}

		public void setStrings(Map<String, String> strings) {
			this.strings = strings;
		}

		public List<C> getCs() {
			return cs;
		}

		public void setCs(List<C> cs) {
			this.cs = cs;
		}

		public C getC(int index) {
			return cs.get(index);
		}

		public void setC(int index, C c) {
			if (index == cs.size()) {
				cs.add(c);
			} else {
				cs.set(index, c);
			}
		}

		@Override
		public List<C> getTs() {
			return cs;
		}
		
		@SuppressWarnings("rawtypes")
		public List getRs() {
			return cs;
		}
	}

	public static class C implements Serializable {

		String string;

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}
	}

	public static interface I<T> {
		public List<T> getTs();
	}

	public static final class F {

		public String string;

		public String getString() {
			return string;
		}
	}
}
