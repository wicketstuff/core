package org.wicketstuff.select2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Collection;

import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Issue644Test {

	private static WicketTester tester;

	private Collection<String> selection;

	@BeforeAll
	public static void beforeClass() {
		tester = new WicketTester();
	}

	@AfterAll
	public static void afterClass() {
		tester.destroy();
	}

	@BeforeEach
	public void before() {
		selection = null;
	}

	@Test
	public void checkInitModelPreFilled() throws Exception {
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
	public void checkInitModelEmpty() throws Exception {
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
