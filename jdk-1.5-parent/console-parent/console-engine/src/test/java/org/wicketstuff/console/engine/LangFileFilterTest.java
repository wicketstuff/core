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

package org.wicketstuff.console.engine;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing {@link LangFileFilter}.
 * 
 * @author cretzel
 */
public class LangFileFilterTest
{

	private LangFileFilter groovyFileFilter;
	private LangFileFilter clojureFileFilter;

	@Before
	public void setup()
	{
		groovyFileFilter = LangFileFilter.create(Lang.GROOVY);
		clojureFileFilter = LangFileFilter.create(Lang.CLOJURE);
	}

	@Test
	public void test_accept_groovy() throws Exception
	{

		assertTrue(groovyFileFilter.accept(null, "Main.groovy"));
		assertTrue(groovyFileFilter.accept(null, "Main.GROOVY"));
		assertTrue(groovyFileFilter.accept(null, "/home/files/Main.GROOVY"));

		assertFalse(groovyFileFilter.accept(null, "Main.clj"));
		assertFalse(groovyFileFilter.accept(null, "Main.TXT"));
		assertFalse(groovyFileFilter.accept(null, "/home/files/groovy"));

	}

	@Test
	public void test_accept_clojure() throws Exception
	{
		assertTrue(clojureFileFilter.accept(null, "Main.clj"));
		assertTrue(clojureFileFilter.accept(null, "Main.CLJ"));
		assertTrue(clojureFileFilter.accept(null, "/home/files/Main.clj"));

		assertFalse(clojureFileFilter.accept(null, "Main.groovy"));
		assertFalse(clojureFileFilter.accept(null, "Main.TXT"));
		assertFalse(clojureFileFilter.accept(null, "/home/files/clj"));

	}
}
