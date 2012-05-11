package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;



abstract class AbstractDropDownPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDropDownPage()
	{
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultDropDownPage.class, "DropDownList"),
				new DemoLink(AjaxDropDownPage.class, "AjaxDropDownList")
			);
	}
}
