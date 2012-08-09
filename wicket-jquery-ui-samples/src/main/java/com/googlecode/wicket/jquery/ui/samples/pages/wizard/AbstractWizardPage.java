package com.googlecode.wicket.jquery.ui.samples.pages.wizard;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DefaultWizardPage;

abstract class AbstractWizardPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractWizardPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultWizardPage.class, "Wizard"),
				new DemoLink(DynamicWizardPage.class, "Wizard, using dynamic steps")
			);
	}
}
