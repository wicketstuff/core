package com.googlecode.wicket.jquery.ui.samples.kendoui.dropdown;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractDropDownPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultDropDownPage.class, "DropDownList"), // lf
				new DemoLink(AjaxDropDownPage.class, "AjaxDropDownList"), // lf
				new DemoLink(TemplateDropDownPage.class, "Lazy DropDownList: custom template"), // lf
				new DemoLink(RendererDropDownPage.class, "Lazy DropDownList: custom renderer") // lf
		);
	}
}
