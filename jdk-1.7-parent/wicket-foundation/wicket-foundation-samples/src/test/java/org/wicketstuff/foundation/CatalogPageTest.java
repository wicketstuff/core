package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.CatalogPage;

public class CatalogPageTest extends AbstractPageTest
{

	@Test
	public void homepageRendersSuccessfully()
	{
		tester.startPage(CatalogPage.class);
		tester.assertRenderedPage(CatalogPage.class);
	}
	
}
