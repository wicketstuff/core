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

import static org.junit.Assert.assertTrue;
import static org.wicketstuff.lazymodel.LazyModel.from;
import static org.wicketstuff.lazymodel.LazyModel.model;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.IProvider;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;
import org.junit.Ignore;
import org.junit.Test;
import org.wicketstuff.lazymodel.LazyModelTest.A;
import org.wicketstuff.lazymodel.LazyModelTest.B;
import org.wicketstuff.lazymodel.LazyModelTest.C;

/**
 * Performance comparison between {@link LazyModel} and {@link PropertyModel}.
 * 
 * @author svenmeier
 */
@Ignore
public class PropertyModelComparison {

	private A a;

	public PropertyModelComparison() {
		a = new A();
		a.b = new B();
		a.b.cs.add(new C());
	}

	@Test
	public void size() {
		long propertyModelSpace = measureSize(new PropertyModel<B>(a, "b"));

		long lazyModelSpace = measureSize(model(from(a).getB()));

		assertTrue("LazyModel 2x bigger",
				lazyModelSpace < propertyModelSpace * 2);
	}

	private long measureSize(IModel<?> model) {
		long size = WicketObjects.sizeof(model);

		System.out
				.println(String.format("PropertyModelComparison %s: %s bytes",
						model.getClass(), size));

		return size;
	}

	@Test
	public void time() {
		Duration propertyModelDuration = measureTime(new IProvider<IModel<?>>() {
			@Override
			public IModel<?> get() {
				return new PropertyModel<C>(a, "b.cs[0].string");
			}
		});

		Duration lazyModelDuration = measureTime(new IProvider<IModel<?>>() {
			@Override
			public IModel<?> get() {
				return model(from(a).getB().getCs().get(0).getString());
			}
		});

		assertTrue("LazyModel is 2 times slower",
				lazyModelDuration.getMilliseconds() < propertyModelDuration
						.getMilliseconds() * 2.1);
	}

	@Test
	public void cachedTime() {
		Duration propertyModelDuration = measureTime(new IProvider<IModel<?>>() {
			@Override
			public IModel<?> get() {
				return new PropertyModel<C>(a, "b.cs[0].string");
			}
		});

		final LazyModel<String> C = model(from(a).getB().getCs().get(0)
				.getString());
		Duration lazyModelDuration = measureTime(new IProvider<IModel<?>>() {
			@Override
			public IModel<?> get() {
				return C.bind(a);
			}
		});

		assertTrue("Cached LazyModel is slower",
				lazyModelDuration.getMilliseconds() < propertyModelDuration
						.getMilliseconds() * 1.1);
	}

	private Duration measureTime(IProvider<IModel<?>> provider) {
		Time start = Time.now();

		for (int i = 0; i < 100000; i++) {
			final IModel<?> m = provider.get();
			for (int j = 0; j < 10; j++) {
				m.getObject();
			}
		}

		Duration duration = Time.now().subtract(start);

		System.out.println(String.format(
				"PropertyModelComparison %s: %s seconds", provider.get()
						.getClass(), duration.seconds()));

		return duration;
	}
}
