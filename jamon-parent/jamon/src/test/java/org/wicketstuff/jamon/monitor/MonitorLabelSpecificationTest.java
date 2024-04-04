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
package org.wicketstuff.jamon.monitor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;


public class MonitorLabelSpecificationTest
{
	private MonitorLabelSpecification specification;

	@Test
	public void shouldBeSatisfiedIfLabelIsSame()
	{
		specification = new MonitorLabelSpecification("mon1");
		assertThat(specification.isSatisfiedBy(new TestMonitor("mon1")), is(true));
		assertThat(specification.isSatisfiedBy(new TestMonitor("Mon1")), is(false));
		assertThat(specification.isSatisfiedBy(new TestMonitor("mon2")), is(false));
	}
}
