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
				new DemoLink(KendoButtonPage.class, "Button"),
				new DemoLink(AjaxButtonPage.class, "AjaxButton"),
				new DemoLink(ConfirmButtonPage.class, "Confirm Button"),
				new DemoLink(ConfirmAjaxButtonPage.class, "Confirm AjaxButton"),
				new DemoLink(SecuredButtonPage.class, "Secured [Ajax]Button"),
				new DemoLink(IndicatingButtonPage.class, "Indicating [Ajax]Button"),
				new DemoLink(ButtonGroupPage.class, "ButtonGroup (radio button like)"),
				new DemoLink(AjaxButtonGroupPage.class, "AjaxButtonGroup")
			);
	}
}
