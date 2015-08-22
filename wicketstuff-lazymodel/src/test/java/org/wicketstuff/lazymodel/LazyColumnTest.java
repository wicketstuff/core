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
import static org.wicketstuff.lazymodel.LazyModel.from;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;
import org.wicketstuff.lazymodel.LazyModelTest.A;
import org.wicketstuff.lazymodel.LazyModelTest.B;

/**
 * Test for {@link LazyColumn}.
 * 
 * @author svenmeier
 */
public class LazyColumnTest {

	@Test
	public void bindToRow() {
		LazyColumn<A, Void, B> column = new LazyColumn<A, Void, B>(
				Model.of("test"), from(A.class).getB());

		A a1 = new A();
		a1.b = new B();
		IModel<A> rowModel1 = Model.of(a1);
		assertEquals(a1.b, column.getDataModel(rowModel1).getObject());

		A a2 = new A();
		a2.b = new B();
		IModel<A> rowModel2 = Model.of(a2);
		assertEquals(a2.b, column.getDataModel(rowModel2).getObject());
	}
}
