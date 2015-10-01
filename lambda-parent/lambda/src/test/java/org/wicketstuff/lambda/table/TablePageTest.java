package org.wicketstuff.lambda.table;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Test;

public class TablePageTest {

	private final WicketTester tester = new WicketTester();

	private static final List<String> NAMES = Arrays.asList("X", "Y", "Z");

	@After
	public void after() {
		tester.destroy();
	}

	@Test
	public void rendersSuccessfully() {
		tester.startPage(TablePage.class);
		tester.assertRenderedPage(TablePage.class);
		for (int i = 1; i <= NAMES.size(); i++) {
			tester.assertLabel(String.format("table:body:rows:%d:cells:1:cell", i), NAMES.get(i - 1));
			tester.assertLabel(String.format("table:body:rows:%d:cells:2:cell", i), NAMES.get(i - 1) + "B");
		}
	}

}
