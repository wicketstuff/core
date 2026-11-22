package org.wicketstuff.datatables.res;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.agilecoders.wicket.webjars.WicketWebjars;

/**
 * Checks that the files of the resource references exist in the webjars this module depends on.
 */
class ResourceReferencesTest
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

	static Stream<ResourceReference> references()
	{
		return Stream.of(new DataTablesJsReference(), new DataTablesCssReference(),
			new DataTablesBootstrapJsReference(), new DataTablesBootstrapCssReference(),
			new DataTablesSelectJsReference(), new DataTablesScrollerJsReference());
	}

	@ParameterizedTest
	@MethodSource("references")
	void fileExists(ResourceReference reference)
	{
		String url = tester.getRequestCycle()
			.urlFor(new ResourceReferenceRequestHandler(reference))
			.toString();
		tester.executeUrl(url);

		// a webjar file that cannot be found is served as an empty response by the tester
		assertEquals(200, tester.getLastResponse().getStatus(), url);
		assertTrue(tester.getLastResponse().getBinaryContent().length > 0, url);
	}
}
