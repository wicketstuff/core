package com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractMultiSelectPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultMultiSelectPage.class, "MultiSelect"), // lf
				new DemoLink(LazyMultiSelectPage.class, "MultiSelect: lazy load"), // lf
				new DemoLink(AjaxMultiSelectPage.class, "AjaxMultiSelect: lazy load") // lf
		);
	}
}
