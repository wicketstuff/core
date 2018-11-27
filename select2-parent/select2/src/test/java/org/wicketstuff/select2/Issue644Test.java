package org.wicketstuff.select2;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Issue644Test {

	private static WicketTester tester;

	private Collection<String> selection;

	@BeforeClass
	public static void beforeClass() {
		tester = new WicketTester();
	}

	@AfterClass
	public static void afterClass() {
		tester.destroy();
	}

	@Before
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
