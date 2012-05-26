package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;



abstract class AbstractComboBoxPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractComboBoxPage()
	{
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultComboBoxPage.class, "ComboBox"),
				new DemoLink(CustomComboBoxPage.class, "ComboBox: custom beans"),
				new DemoLink(TemplateComboBoxPage.class, "ComboBox: custom template"),
				new DemoLink(RendererComboBoxPage.class, "ComboBox: custom renderer")
			);
	}
}
