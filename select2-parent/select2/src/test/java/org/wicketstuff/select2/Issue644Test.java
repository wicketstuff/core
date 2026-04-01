package org.wicketstuff.select2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Collection;

import org.apache.wicket.model.util.CollectionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Issue644Test extends AbstarctSelect2Test {
	private Collection<String> selection;

	@BeforeEach
	void before() {
		selection = null;
	}

	@Test
	void checkInitModelPreFilled() throws Exception {
		// ARRANGE
		Issue644Page page = new Issue644Page() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(Collection<String> selection) {
				Issue644Test.this.selection = selection;
			}

		};
		page.setModel(new CollectionModel<>(Issue644Page.KNOWN_USERS));
		tester.startPage(page);

		// ACT
		tester.executeAjaxEvent("frm:sbmt", "click");

		// ASSERT
		assertThat(selection, containsInAnyOrder("bob", "alice"));
	}

	@Test
	void checkInitModelEmpty() throws Exception {
		// ARRANGE
		Issue644Page page = new Issue644Page() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(Collection<String> selection) {
				Issue644Test.this.selection = selection;
			}

		};
		page.setModel(new CollectionModel<String>());
		tester.startPage(page);

		// ACT
		tester.getRequest().getPostParameters().addParameterValue("s2mc", "bob");
		tester.getRequest().getPostParameters().addParameterValue("s2mc", "alice");
		tester.executeAjaxEvent("frm:sbmt", "click");

		// ASSERT
		assertThat(selection, containsInAnyOrder("bob", "alice"));
	}
}
