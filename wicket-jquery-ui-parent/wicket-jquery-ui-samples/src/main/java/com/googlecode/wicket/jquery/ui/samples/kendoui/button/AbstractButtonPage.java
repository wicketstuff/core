package com.googlecode.wicket.jquery.ui.samples.kendoui.button;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractButtonPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(KendoButtonPage.class, "Button"), // lf
				new DemoLink(AjaxButtonPage.class, "AjaxButton"), // lf
				new DemoLink(ConfirmButtonPage.class, "Confirm Button"), // lf
				new DemoLink(ConfirmAjaxButtonPage.class, "Confirm AjaxButton"), // lf
				new DemoLink(SecuredButtonPage.class, "Secured [Ajax]Button"), // lf
				new DemoLink(IndicatingButtonPage.class, "Indicating [Ajax]Button"), // lf
				new DemoLink(ButtonGroupPage.class, "ButtonGroup (radio button like)"), // lf
				new DemoLink(AjaxButtonGroupPage.class, "AjaxButtonGroup") // lf
		);
	}
}
