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
import static org.junit.Assert.fail;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * Test for {@link LazyModel}.
 * 
 * @author svenmeier
 */
public class FormComponentTest {

	private WicketTester tester = new WicketTester();

	@Test
	public void targetObject() {
		IModel<Long> model = LazyModel.model(LazyModel.from(new Target())
				.getValue());

		TestPage<Long> page = tester.startPage(new TestPage<Long>(model));

		assertEquals(Long.class, page.text.getType());
		assertEquals("0", page.text.getDefaultModelObjectAsString());

		tester.getRequest().setParameter("text", "42");
		tester.submitForm(page.form);
		assertEquals(42l, page.text.getDefaultModelObject());
	}

	@Test
	public void targetInTypedModel() {
		IModel<Target> wrapper = new IObjectClassAwareModel<Target>() {

			private Target target = new Target();

			@Override
			public Target getObject() {
				return target;
			}

			@Override
			public void setObject(Target object) {
				target = object;
			}

			@Override
			public void detach() {
			}

			@Override
			public Class<Target> getObjectClass() {
				return Target.class;
			}
		};

		IModel<Long> model = LazyModel
				.model(LazyModel.from(wrapper).getValue());

		TestPage<Long> page = tester.startPage(new TestPage<Long>(model));

		assertEquals(Long.class, page.text.getType());
		assertEquals("0", page.text.getDefaultModelObjectAsString());

		tester.getRequest().setParameter("text", "42");
		tester.submitForm(page.form);
		assertEquals(42l, page.text.getDefaultModelObject());
	}

	@Test
	public void targetInTypeErasedModel() {
		IModel<Target> wrapper = new Model<Target>(new Target());

		IModel<Long> model = LazyModel.model(
				LazyModel.from(Target.class).getValue()).bind(wrapper);

		TestPage<Long> page = tester.startPage(new TestPage<Long>(model));

		assertEquals(Long.class, page.text.getType());
		assertEquals("0", page.text.getDefaultModelObjectAsString());

		tester.getRequest().setParameter("text", "42");
		tester.submitForm(page.form);
		assertEquals(42l, page.text.getDefaultModelObject());
	}

	@Test
	public void nullTargetInTypeErasedModel() {
		IModel<Target> wrapper = new Model<Target>(null);

		IModel<Long> model = LazyModel.model(
				LazyModel.from(Target.class).getValue()).bind(wrapper);

		TestPage<Long> page = tester.startPage(new TestPage<Long>(model));

		assertEquals(null, page.text.getType());
		assertEquals("", page.text.getDefaultModelObjectAsString());

		tester.getRequest().setParameter("text", "42");
		try {
			tester.submitForm(page.form);

			fail();
		} catch (Exception expected) {
		}
	}

	public static class Target implements Serializable {
		private Long value = Long.valueOf(0);

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
			this.value = value;
		}
	}
}
