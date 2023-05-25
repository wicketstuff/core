package org.wicketstuff.foundation.blockgrid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

public class FoundationBlockGridTest {

	@Test
	public void testParagraph() {
		WicketTester tester = new WicketTester();
		List<String> list = Arrays.asList("foo", "bar", "baz");
		List<BlockGridOptions> optionsList = Arrays.asList(new BlockGridOptions(BlockGridType.SMALL_BLOCK_GRID, 3),
				new BlockGridOptions(BlockGridType.MEDIUM_BLOCK_GRID, 4), new BlockGridOptions(BlockGridType.LARGE_BLOCK_GRID, 5));
		FoundationBlockGrid<String> grid = new FoundationBlockGrid<String>("id", optionsList, list) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<String> model) {
				return new TestParagraphPanel(id, model);
			}
		};
		tester.startComponentInPage(grid);
		tester.dumpPage();
		TagTester container = tester.getTagByWicketId("container");
		assertTrue(container.getAttributeContains("class", "small-block-grid-3"));
		assertTrue(container.getAttributeContains("class", "medium-block-grid-4"));
		assertTrue(container.getAttributeContains("class", "large-block-grid-5"));
		assertEquals(3, tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("content").size());
		assertEquals(3, tester.getTagsByWicketId("parent").size());
		assertEquals(3, tester.getTagsByWicketId("paragraph").size());
	}

	@Test
	public void testImage() {
		WicketTester tester = new WicketTester();
		List<String> list = Arrays.asList("satelite.jpg", "space.jpg", "spacewalk.jpg");
		FoundationBlockGrid<String> grid = new FoundationBlockGrid<String>("id", new BlockGridOptions(BlockGridType.MEDIUM_BLOCK_GRID, 3), list) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<String> model) {
				return new TestImagePanel(id, model.getObject());
			}
		};
		tester.startComponentInPage(grid);
		tester.dumpPage();
		TagTester container = tester.getTagByWicketId("container");
		assertTrue(container.getAttributeIs("class", "medium-block-grid-3"));
		assertEquals(3,  tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("content").size());
		assertEquals(3, tester.getTagsByWicketId("img").size());
	}
}
