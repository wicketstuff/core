/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.combobox;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.KendoSamplePage;

abstract class AbstractComboBoxPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultComboBoxPage.class, "ComboBox"), // lf
				new DemoLink(AjaxComboBoxPage.class, "AjaxComboBox"), // lf
				new DemoLink(CustomComboBoxPage.class, "ComboBox: custom beans"), // lf
				new DemoLink(TemplateComboBoxPage.class, "ComboBox: custom template"), // lf
				new DemoLink(RendererComboBoxPage.class, "ComboBox: custom renderer") // lf
		);
	}
}
