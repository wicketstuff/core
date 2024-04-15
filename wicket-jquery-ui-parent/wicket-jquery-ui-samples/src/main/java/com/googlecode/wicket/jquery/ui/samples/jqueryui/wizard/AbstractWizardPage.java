package com.googlecode.wicket.jquery.ui.samples.jqueryui.wizard;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractWizardPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultWizardPage.class, "Wizard"), // lf
				new DemoLink(DynamicWizardPage.class, "Wizard, using dynamic steps") // lf
			);
	}
}
