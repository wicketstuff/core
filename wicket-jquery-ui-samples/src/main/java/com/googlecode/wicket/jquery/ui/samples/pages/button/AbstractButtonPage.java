package com.googlecode.wicket.jquery.ui.samples.pages.button;

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
				new DemoLink(DefaultButtonPage.class, "Button"),
				new DemoLink(AjaxButtonPage.class, "AjaxButton"),
				new DemoLink(RadioButtonPage.class, "Radio Buttons"),
				new DemoLink(CheckButtonPage.class, "Check Buttons"),
				new DemoLink(ConfirmButtonPage.class, "Confirm Button"),
				new DemoLink(ConfirmAjaxButtonPage.class, "Confirm AjaxButton"),
				new DemoLink(SplitButtonPage.class, "SplitButton"),
				new DemoLink(AjaxSplitButtonPage.class, "Ajax SplitButton"),
				new DemoLink(SecuredButtonPage.class, "Secured [Ajax]Button"),
				new DemoLink(IndicatingAjaxButtonPage.class, "Indicating AjaxButton")
			);
	}
}
