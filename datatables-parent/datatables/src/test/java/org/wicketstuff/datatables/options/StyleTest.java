package org.wicketstuff.datatables.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.DecoratingHeaderResponse;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.agilecoders.wicket.webjars.WicketWebjars;

/**
 * Checks that the stylesheets rendered for a style and plugin exist in the webjars this module
 * depends on.
 */
class StyleTest
{
	private WicketTester tester;

	@BeforeEach
	void setUp()
	{
		tester = new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				WicketWebjars.install(this);
			}
		});
	}

	@AfterEach
	void tearDown()
	{
		tester.destroy();
	}

	@ParameterizedTest
	@CsvSource({
		"bootstrap5, dataTables",
		"bootstrap5, scroller",
		"bootstrap5, select",
		"dataTables, scroller",
		"dataTables, select",
		"foundation, dataTables",
		"foundation, select",
		"jqueryui, dataTables",
		"jqueryui, select",
		"bootstrap, dataTables",
		"bootstrap, select" })
	void renderedStylesheetExists(Options.Style style, String plugin)
	{
		List<HeaderItem> items = render(style, plugin);
		assertEquals(1, items.size());

		ResourceReference reference = ((CssReferenceHeaderItem)items.get(0)).getReference();
		String url = tester.getRequestCycle()
			.urlFor(new ResourceReferenceRequestHandler(reference))
			.toString();
		tester.executeUrl(url);

		// a webjar file that cannot be found is served as an empty response by the tester
		assertEquals(200, tester.getLastResponse().getStatus(), url);
		assertTrue(tester.getLastResponse().getBinaryContent().length > 0, url);
	}

	@ParameterizedTest
	@CsvSource({ "dataTables, dataTables", "none, dataTables", "none, scroller", "none, select" })
	void rendersNothing(Options.Style style, String plugin)
	{
		assertEquals(0, render(style, plugin).size());
	}

	private List<HeaderItem> render(Options.Style style, String plugin)
	{
		List<HeaderItem> items = new ArrayList<>();
		IHeaderResponse response = new DecoratingHeaderResponse(null)
		{
			@Override
			public void render(HeaderItem item)
			{
				items.add(item);
			}
		};
		style.renderHead(response, plugin);
		return items;
	}
}
