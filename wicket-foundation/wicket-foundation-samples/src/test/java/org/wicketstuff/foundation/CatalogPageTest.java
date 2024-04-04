package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class CatalogPageTest extends AbstractPageTest
{

	@Test
	public void homepageRendersSuccessfully()
	{
		tester.startPage(CatalogPage.class);
		tester.assertRenderedPage(CatalogPage.class);
	}

}
