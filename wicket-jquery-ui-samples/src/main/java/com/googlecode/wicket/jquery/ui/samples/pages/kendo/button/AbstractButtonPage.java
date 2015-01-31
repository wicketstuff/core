package com.googlecode.wicket.jquery.ui.samples.pages.kendo.button;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;

abstract class AbstractButtonPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractButtonPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(KendoButtonPage.class, "Submit Button"),
				new DemoLink(AjaxButtonPage.class, "Ajax Button"),
				new DemoLink(ConfirmButtonPage.class, "Confirm Button"),
				new DemoLink(ConfirmAjaxButtonPage.class, "Confirm Ajax Button"),
				new DemoLink(SecuredButtonPage.class, "Secured Button"),
				new DemoLink(IndicatingButtonPage.class, "Indicating Button")
			);
	}
}
