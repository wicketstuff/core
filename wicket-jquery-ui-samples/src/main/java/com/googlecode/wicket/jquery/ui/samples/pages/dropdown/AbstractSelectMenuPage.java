package com.googlecode.wicket.jquery.ui.samples.pages.dropdown;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSelectMenuPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSelectMenuPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSelectMenuPage.class, "DropDownChoice"),
				new DemoLink(AjaxSelectMenuPage.class, "AjaxDropDownChoice"),
				new DemoLink(RendererSelectMenuPage.class, "DropDownChoice: custom renderer")
			);
	}
}
