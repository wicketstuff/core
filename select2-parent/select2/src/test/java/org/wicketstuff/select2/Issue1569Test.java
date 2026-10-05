/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Collection;

import org.apache.wicket.model.util.CollectionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Issue1569Test extends AbstarctSelect2Test {
	private Collection<String> selection;

	@BeforeEach
	void before() {
		selection = null;
	}

	@Test
	void checkInitModelPreFilled() throws Exception {
		// ARRANGE
		Issue1569Page page = new Issue1569Page() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(Collection<String> sel) {
				Issue1569Test.this.selection = sel;
			}

		};
		page.setModel(new CollectionModel<>(Issue1569Page.KNOWN_USERS));
		tester.startPage(page);

		// ACT
		tester.executeAjaxEvent("frm:sbmt", "click");

		// ASSERT
		assertThat(selection, containsInAnyOrder("bob", "alice", "evil"));
	}

	@Test
	void checkInitModelEmpty() throws Exception {
		// ARRANGE
		Issue1569Page page = new Issue1569Page() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(Collection<String> selection) {
				Issue1569Test.this.selection = selection;
			}

		};
		page.setModel(new CollectionModel<String>());
		tester.startPage(page);

		// ACT
		tester.getRequest().getPostParameters().addParameterValue("s2mc", "bob");
		tester.getRequest().getPostParameters().addParameterValue("s2mc", "evil");
		tester.executeAjaxEvent("frm:sbmt", "click");

		// ASSERT
		assertThat(selection, containsInAnyOrder("bob", "evil"));
	}
}
