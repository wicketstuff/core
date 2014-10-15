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
 */package org.wicketstuff.lazymodel.reflect;

import static org.junit.Assert.assertEquals;
import static org.wicketstuff.lazymodel.LazyModel.from;
import static org.wicketstuff.lazymodel.LazyModel.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.wicketstuff.lazymodel.LazyModel;

/**
 * Test for {@link TypeIterator}.
 */
public class TypeIteratorTest
{

	@Test
	public void single()
	{
		Single m = new Single();
		
		LazyModel<String> model = model(from(m).getT().s());

		assertEquals("Q", model.getObject());

		assertEquals(String.class, model.getObjectClass());
	}

	@Test
	public void multiple()
	{
		Multiple m = new Multiple();

		LazyModel<String> model = model(from(m).getT().get(0).s());

		assertEquals("Q", model.getObject());

		assertEquals(String.class, model.getObjectClass());
	}
	
	@Test
	public void mega()
	{
		Mega m = new Mega();

		LazyModel<String> model = model(from(m).getT().getT().get(0).s());

		assertEquals("Q", model.getObject());

		assertEquals(String.class, model.getObjectClass());
	}
	
	public static class Q
	{
		public String s()
		{
			return "Q";
		}
	}

	public static class L<T>
	{
		private T t;

		public L(T t)
		{
			this.t = t;
		}

		public T getT()
		{
			return t;
		}
	}

	public static class Single extends L<Q>
	{
		public Single()
		{
			super(new Q());
		}
	}

	public static class Multiple extends L<List<Q>>
	{
		public Multiple()
		{
			super(Arrays.asList(new Q()));
		}
	}

	public static class Mega extends L<Multiple>
	{
		public Mega()
		{
			super(new Multiple());
		}
	}
}
