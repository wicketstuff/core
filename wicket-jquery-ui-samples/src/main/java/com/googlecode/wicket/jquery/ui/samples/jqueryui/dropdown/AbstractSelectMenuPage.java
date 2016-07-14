package com.googlecode.wicket.jquery.ui.samples.jqueryui.dropdown;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSelectMenuPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSelectMenuPage.class, "DropDownChoice"), // lf
				new DemoLink(AjaxSelectMenuPage.class, "AjaxDropDownChoice"), // lf
				new DemoLink(RendererSelectMenuPage.class, "DropDownChoice: custom renderer") // lf
		);
	}
}
