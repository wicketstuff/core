/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.button;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractButtonPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultButtonPage.class, "Button"), // lf
				new DemoLink(AjaxButtonPage.class, "AjaxButton"), // lf
				new DemoLink(RadioButtonPage.class, "Radio Buttons"), // lf
				new DemoLink(CheckButtonPage.class, "Check Buttons"), // lf
				new DemoLink(ConfirmButtonPage.class, "Confirm Button"), // lf
				new DemoLink(ConfirmAjaxButtonPage.class, "Confirm AjaxButton"), // lf
				new DemoLink(SplitButtonPage.class, "SplitButton"), // lf
				new DemoLink(AjaxSplitButtonPage.class, "Ajax SplitButton"), // lf
				new DemoLink(SecuredButtonPage.class, "Secured [Ajax]Button"), // lf
				new DemoLink(IndicatingAjaxButtonPage.class, "Indicating AjaxButton") // lf
		);
	}
}
