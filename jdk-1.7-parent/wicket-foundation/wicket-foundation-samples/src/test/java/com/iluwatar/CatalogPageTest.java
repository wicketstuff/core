package com.iluwatar;

import org.junit.Test;

public class CatalogPageTest extends AbstractPageTest
{

	@Test
	public void homepageRendersSuccessfully()
	{
		tester.startPage(CatalogPage.class);
		tester.assertRenderedPage(CatalogPage.class);
	}
	
}
